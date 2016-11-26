package adt;

import java.util.NoSuchElementException;




/**
 * 红黑树，线程不安全
 * 红黑树4个性质
 * 1.每个节点要么是红色，要么是黑色
 * 2.根是黑色的，（可选：null节点默认为黑色）
 * 3.一个节点是红色的，它的子节点必须是黑色的
 * 4.从一个节点到一个null引用必须包含相同个数的黑色节点
 * @author wenl
 * @param <AnyType>
 */
public class RedBlackTree<AnyType extends Comparable<? super AnyType>> {

	private static class RedBlackNode<AnyType>{
		AnyType element;
		RedBlackNode<AnyType> left;
		RedBlackNode<AnyType> right;
		int color;
		public RedBlackNode(AnyType element){
			this(element,null,null);
		}
		
		public RedBlackNode(AnyType element, RedBlackNode<AnyType> left, RedBlackNode<AnyType> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			this.color = BLACK;//根式黑色，默认为黑色
		}
	}
	private static final int BLACK=1;
	private static final int RED=0;
	//header.right是根节点，header.left是上次插入元素到
	private RedBlackNode<AnyType>header;
	//用于辅助 ，
	private RedBlackNode<AnyType>nullNode;
	//用于插入找到当前节点
	private RedBlackNode<AnyType>current;
	//用于插入维护 插入节点与父链的关系
    private RedBlackNode<AnyType>parent;
    //用于判断是否是 之字形，用于维护祖父关系。
    private RedBlackNode<AnyType>grand;
    //用于一字形和之字形旋转
    private RedBlackNode<AnyType>great;
	
    /**
     * 构造方法
     */
    public RedBlackTree( )
    {
        nullNode = new RedBlackNode<>( null );
        nullNode.left = nullNode.right = nullNode;
        header      = new RedBlackNode<>( null );
        header.left = header.right =  nullNode;
    }

	/**
	 * 自顶向下插入
	 */
	public void insert( AnyType item ){
		  nullNode.element=item;
		  current=parent=grand=header;
		  //自顶向下调整,避免插入的父节点和父节点的兄弟节点为黑色的情况，该情况复杂不利于恢复平衡信心.
		  while(compare(item,current)!=0){
			  great=grand;
			  grand=parent;
			  parent=current;
			  current=compare(item,current)<0?current.left:current.right;
			  if(current.left.color==RED&&current.right.color==RED){
				  handleReorientAfterInsert(item);
			  }
		  }
		 
		  if(current!=nullNode){//重复元素跳过
			  return;
		  }
		  //找到位置
		  //构建新的节点
		  current=new RedBlackNode<AnyType>(item,nullNode,nullNode);
		  //维护与父节点的关系
		  if(compare(item,parent)<0){
			  parent.left=current;
		  }else{
 			  parent.right=current;
		  }
		  //插入完成后,维护平衡信息
		  handleReorientAfterInsert(item);
		  nullNode.element=null;
	}
	

	/**
	 * 删除一个节点，
	 * 依据可以删除一个叶子,
	 * 自顶向下删除,
	 * 1如果要删除项有右儿子，先删除右儿子最小项，之后使用原右儿子的最小项内容替换要删除项的内容.
	 * 2.如果只有左儿子，先删除左儿子最大，之后使用左儿子的最大项替换要删除项的内容.
	 * 3.如果没有儿子
	 * 	若父节点为header,将树变为空树
	 *  否则如果当前节点为黑色，进行调整，保证删除项为红色，之后将要删除项的父节点的引用设置为nullNode.
	 * @param x
	 */
	public AnyType remove( AnyType x ){
		//需要自己尝试书写
		//先查找是否存在，存在后删除
		 RedBlackNode<AnyType>p=find(x);
		 RedBlackNode<AnyType>pParent=parent;
		 if (p == null){
	        return null;
		 }
		 AnyType item=p.element;
		 //自顶向下删除
		 //找到后,如果存在左儿子和右儿子（或 只有右儿子），
		 //使用右儿子的最小，替换当前 ，之后删除右儿子最小
		 //只有左儿子使用左儿子最大替换，
		 RedBlackNode<AnyType>replacement=findReplaceMent(p);
		 if(replacement!=null){
			 //进行替换
			 p.element=remove(replacement.element);

		 }else{
			 //没有替换者，
			 if(pParent==header){
				makeEmpty();
			 }else{
				 if(p.color==BLACK){
					 //将p地调整为红色
					 fixbeforedelete(p.element) ;
					 pParent=parent;
				 }
				//调整为删除
				 if(pParent.left==p){
					pParent.left=nullNode;
				  }else if(pParent.right==p){
					pParent.right=nullNode;
			      }

			 }
		 }
		 current=p;
		 parent=pParent;
		 return item;
	}
	
	/**
	 * 删除前调整数的平衡信息，保证要删除的项是红色
	 * @param item
	 */
	private void fixbeforedelete(AnyType item) {
		grand=header;
		RedBlackNode<AnyType>p=header.right;
		RedBlackNode<AnyType>x=nullNode;
		RedBlackNode<AnyType>t=nullNode;
		RedBlackNode<AnyType>i=find(item);
		//先把p涂成红色，最后恢复
		p.color=RED;
		x=item.compareTo(p.element)<=0?p.left:p.right;
		t=item.compareTo(p.element)<=0?p.right:p.left;
		//保证要删除的项是红色
		while(i.color!=RED){
			if(x.color==RED||
				(x.color==BLACK&&(x.left.color==RED&&x.right.color==RED)||
				t.color==BLACK&&(x.left.color==RED||x.right.color==RED))	
			){
				//x为红色或x儿子为红色,x为黑色&&t为黑色,x有一个儿子为红色,向下探索
				grand=p;
				p=x;
				x=item.compareTo(p.element)<0?p.left:p.right;
				t=item.compareTo(p.element)<0?p.right:p.left;
			}else if(x.color==BLACK&&t.color==BLACK
					&&x.right.color==BLACK&&x.left.color==BLACK){
				//3中情况需要，调整的情况
				if(t.left.color==BLACK&&t.right.color==BLACK){
					//t的两个儿子，直接变换p和t,x的颜色，重新再该位置下探
					p.color=BLACK;
					t.color=RED;
					x.color=RED;
				}else if(t.left.color==RED&&t.right.color==RED){
					//t有两个红色的儿子，调整后下探
					if(p.right==t){
						RedBlackNode<AnyType>red=t.left;
						p.right=red.left;
						t.left=red.right;
						red.right=t;
						red.left=p;	
						//更新祖父节点
						if(grand.left==p){
							grand.left=red;
						}else{
							grand.right=red;
						}
						grand=red;
						p.color=BLACK;
						x.color=RED;
						t=p.right;	
					}else{
						RedBlackNode<AnyType>red=t.right;
						p.left=red.right;
						t.right=red.left;
						red.right=p;
						red.left=t;
						if(grand.left==p){
							grand.left=red;
						}else{
							grand.right=red;
						}
						grand=red;
						p.color=BLACK;
						x.color=RED;
						t=p.left;
					}
				}else if(p.right==t&&t.left.color==RED){
					//右左，之字调整后继续下探
					RedBlackNode<AnyType>red=t.left;
					p.right=red.left;
					t.left=red.right;
					red.right=t;
					red.left=p;	
					if(grand.left==p){
						grand.left=red;
					}else{
						grand.right=red;
					}
					grand=red;
					p.color=BLACK;
					x.color=RED;
					t=p.right;
				}else if(p.left==t&&(t.right.color==RED)){
					//左右，之字调整后继续下探
					RedBlackNode<AnyType>red=t.right;
					p.left=red.right;
					t.right=red.left;
					red.right=p;
					red.left=t;
					if(grand.left==p){
						grand.left=red;
					}else{
						grand.right=red;
					}
					grand=red;
					p.color=BLACK;
					x.color=RED;
					t=p.left;
				}else if(p.right==t&&t.right.color==RED){
					//右右 一字，交换t和p
					p.right=t.left;
					t.left=p;
					if(grand.left==p){
						grand.left=t;
					}else{
						grand.right=t;
					}
					grand=t;
					t.color=RED;
					p.color=BLACK;
					t=p.right;
				}else if(p.left==t&&t.left.color==RED){
					//左左 一字 交换t和p
					p.left=t.right;
					t.right=p;
					if(grand.left==p){
						grand.left=t;
					}else{
						grand.right=t;
					}
					grand=t;
					t.color=RED;
					p.color=BLACK;
					t=p.left;
				}
		}else if(x.color==BLACK&&p.color==BLACK&&t.color==RED){
			//x的兄弟为黑色，x和x的父节点都是红色，调整t和p,保证p为红色后，继续下探
			if(p.left==x){
				p.right=t.left;
				t.left=p;
				if(grand.left==p){
					grand.left=t;
				}else{
					grand.right=t;
				}
				grand=t;
				t.color=BLACK;
				p.color=RED;
				t=p.right;
			}else{
				p.left=t.right;
				t.right=p;
				if(grand.left==p){
					grand.left=t;
				}else{
					grand.right=t;
				}
				grand=t;
				t.color=BLACK;
				p.color=RED;
				t=p.left;
			}
		}else if(header.right==p&&x.color==BLACK
				&&p.color==RED&&t.color==RED){
			p.color=BLACK;
		}
	}
		header.right.color=BLACK;
		parent=p;
}


	/**
	 * 用于删除，查找替换的节点
	 * @param p
	 * @return
	 */
	private RedBlackNode<AnyType> findReplaceMent(RedBlackNode<AnyType> p) {
			RedBlackNode<AnyType>replacement=null; 
			parent=null;
			if (p == null){
	            return null;
			}else if (p.right != nullNode) {
					parent=p;
	        		replacement = p.right;
	            while (replacement.left != nullNode){
	            	parent=replacement;
	            	replacement = replacement.left;
	            }
	            return replacement;
	        } else if(p.left!=nullNode){
	        		parent=p;
	        		replacement = p.left;
	            while (replacement != nullNode) {
	            	parent=replacement;
	            	replacement = replacement.right;
	            }
	            return replacement;
	        }else{
	        	return null;
	        }
	}

	/**
	 * 查找项对应的节点
	 * @param x
	 * @return
	 */
	private RedBlackNode<AnyType> find(AnyType x) {
	        nullNode.element = x;
	        current = header.right;
	        parent=current;
	        while(true){
	        	int compare=x.compareTo(current.element);
	        	if(compare<0){
	        		parent=current;
	        		current=current.left;
	        	}else if(compare>0){
	        		parent=current;
	        		current=current.right;
	        	}else if(current!=nullNode){
	        		nullNode.element = null;
	        		return current;
	        	}else {
	        		nullNode.element = null;
	        		return null;
	        	}
	        }
	  }

	/**
	 * 找最小项
	 * @return
	 */
	public AnyType findMin( ){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		RedBlackNode<AnyType>iter=header.right;
		while(iter.left!=nullNode){
			iter=iter.left;
		}
		return iter.element;
    }
	
	/**
	 * 找最大项
	 * @return
	 */
	public AnyType findMax(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		RedBlackNode<AnyType>t=header.right;
		while(t.right!=nullNode){
			t=t.right;
		}
		return t.element;
	}
	
	/**
	 * 判断树是否包含x项
	 * @param x
	 * @return
	 */
	public boolean contains( AnyType x ){
		nullNode.element=x;
		RedBlackNode<AnyType>iter=header.right;
		boolean flag=false;
		for(;;){
			if(x.compareTo(iter.element)<0){
				iter=iter.left;
			}else if(x.compareTo(iter.element)>0){
				iter=iter.right;
			}else if(iter!=nullNode){
				flag= true;
				break;
			}else {
				flag= false;
				break;
			}
		}
		nullNode.element=null;
		return flag;	 
	}
	
	 /**
	 * 清空这棵树
	 */
	public void makeEmpty( ){
		 header.right=nullNode;
	 }
	 
	 /**
	 * 打印这棵树
	 */
	public void printTree(){
		 if(isEmpty()){
			 throw new NoSuchElementException();
	     }
		 printTree(header.right);
	 }
	 
	 /**
	  * 打印这棵树
	 * @param t
	 */
	private void printTree(RedBlackNode<AnyType> t) {
		   if( t != nullNode )
	        {
	            printTree( t.left );
	            System.out.println( t.element +"color is"+( t.color==RED?"RED":"BLACK")+" left:"+t.left.element+" right:"+t.right.element);
	         
	            printTree( t.right );
	        }
	}
	 
	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		 return header.right==nullNode;
	}
	
    /**
     * 使用的比较方法
     * @param item
     * @param t 如果t是header,任何item都大于header
     * @return
     */
    private int compare( AnyType item, RedBlackNode<AnyType> t ){
        if( t == header )
            return 1;
        else
            return item.compareTo( t.element );    
    }
	/**
	 * 插入后维护平衡信息
	 * @param item
	 */
	private void handleReorientAfterInsert(AnyType item) {
		//初步调整的变换颜色 自己变为红色，两个儿子变为红色
		current.color=RED;
		current.left.color=BLACK;
		current.right.color=BLACK;
		if(parent.color==RED){//调整后破坏了红黑树性质，需要旋转
			//分两种类型 一字形和之字形，之字形比一字形调整了多一步
			grand.color = RED;
			if((compare(item,grand)<0)!=(compare(item,parent)<0)){//之字形
				parent=rotate(item,grand);//调整parent和他的儿子，并将调整后的节点W设置成parent
			}
			//调整完成，重新设置当前节点
			current=rotate(item,great);
			//并将当前节点设置为黑色
			current.color=BLACK;
		}
		//保证根节点是黑色
		header.right.color=BLACK;
	}
	
	/**
	 * 根据item和根，旋转对应的儿子
	 * @param item 插入的项
	 * @param parent 当前根
	 * @return
	 */
	private RedBlackNode<AnyType> rotate(AnyType item, RedBlackNode<AnyType> parent) {
		//
		if(compare(item,parent)<0){//L
			return parent.left=compare(item,parent.left)<0?
				rotateWithLeftChild(parent.left):
				rotateWithRightChild(parent.left);
		}else {//R
			return parent.right=compare(item,parent.right)<0?
				rotateWithLeftChild(parent.right):
				rotateWithRightChild(parent.right);
			}

	}
	/**
	 * 旋转一颗树的右儿子，返回新的根
	 * @param t 需要旋转的树的根
	 * @return
	 */
	private RedBlackNode<AnyType> rotateWithRightChild(RedBlackNode<AnyType> t) {
		//
		RedBlackNode<AnyType>newT=t.right;
		t.right=newT.left;
		newT.left=t;
		return newT;

	}
	
	/**
	 * 旋转一颗树的左儿子，返回新的根
	 * @param t
	 * @return
	 */
	private RedBlackNode<AnyType> rotateWithLeftChild(RedBlackNode<AnyType> t) {
		//
		RedBlackNode<AnyType>newT=t.left;
		t.left=newT.right;
		newT.right=t;
		return newT;
	}
	
    public static void main( String [ ] args )
    {
        RedBlackTree<Integer> t = new RedBlackTree<>( );
        final int NUMS = 10;
//        final int NUMS = 400000;
//        final int GAP  =  35461;
//
//        System.out.println( "Checking... (no more output means success)" );
//        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ){
//            t.insert( i );
//        }
//        t.printTree();
        for(int i=0;i<NUMS;i++){
        	t.insert(i);
        }
        t.printTree();
//        t.remove(0);
//        System.out.println("=================================");
//        t.printTree();
        t.remove(0);
        System.out.println("=================================");
        t.printTree();
//        if( t.findMin( ) != 1 || t.findMax( ) != NUMS - 1 )
//            System.out.println( "FindMin or FindMax error!" +t.findMax( )+":"+t.findMin( ));
//
//        for( int i = 1; i < NUMS; i++ )
//             if( !t.contains( i ) )
//                 System.out.println( "Find error1!" );
      
    }
}
