package adt;
/**
 * 伸展树
 * */
public class SplayTree<AnyType extends Comparable<? super AnyType>> {
	private BinaryNode<AnyType>root;
	private BinaryNode<AnyType>nullNode;
	//for splay
	private BinaryNode<AnyType>header=new BinaryNode<AnyType>(null);
	private BinaryNode<AnyType>newNode=null;
	
	
	private static class BinaryNode<AnyType>{
	       // Constructors
      public  BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

      public  BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
	}
	
	public SplayTree(){
		nullNode=new BinaryNode<AnyType>(null);
		nullNode.left=nullNode.right=nullNode;
		root=nullNode;
	}
	/**
	 *  伸展历程
	 * @param item
	 * @param t
	 * @return
	 */
	private BinaryNode<AnyType>splay(AnyType item,BinaryNode<AnyType>t){
		BinaryNode<AnyType>leftTreeMax,rightTreeMin;
		//header.leftt用于存储右子树的根,header.right用于存储左子树的根
		header.left=header.right=nullNode;
		leftTreeMax=rightTreeMin=header;
		//保证一定会查找到
		nullNode.element=item;
		while(true){
			if(item.compareTo(t.element)<0){
				if(item.compareTo(t.left.element)<0){
					t=rotateWithLeftChild(t);
				}
				if(t.left==nullNode){
					break;
				}
				rightTreeMin.left=t;
				rightTreeMin=t;
				t=t.left;
			}else if(item.compareTo(t.element)>0){
				if(item.compareTo(t.right.element)>0){
					t=rotateWithRightChild(t);
				}
				if(t.right==nullNode){
					break;
				}
				leftTreeMax.right=t;
				leftTreeMax=t;
				t=t.right;
			}else{
				break;
			}
		}
		leftTreeMax.right=t.left;
		rightTreeMin.left=t.right;
		t.left=header.right;
		t.right=header.left;
		return t;
	}
	
	public void insert(AnyType x){
		if(newNode==null){
			newNode=new BinaryNode<AnyType>(null);
		}
		newNode.element=x;
		if(root==nullNode){
			newNode.left=newNode.right=null;
			root=newNode;
		}else{
			root=splay(x,root);
			if(x.compareTo(root.element)<0){
				newNode.left=root.left;
				newNode.right=root;
				root.left=nullNode;
				root=newNode;
			}else if(x.compareTo(root.element)>0){
				newNode.right=root.right;
				newNode.left=root;
				root.right=null;
				root=newNode;
			}
		}
		newNode=nullNode;
	}
	public void remove(AnyType x){
		BinaryNode<AnyType>newTree;
		root=splay(x, root);
		if(x.compareTo(root.element)!=0){
			return;
		}
		if(root.left==nullNode){
			newTree=root.right;
		}else{
			newTree=root.left;
			newTree=splay(x, newTree);
			newTree.right=root.right;
		}
		
		root=newTree;
	}
	
	private BinaryNode<AnyType> rotateWithRightChild(BinaryNode<AnyType> t) {
		BinaryNode<AnyType>newRoot=t.right;
		t.right=newRoot.left;
		newRoot.left=t;
		return newRoot;
	}
	
	private BinaryNode<AnyType> rotateWithLeftChild(BinaryNode<AnyType> t) {
		BinaryNode<AnyType>newRoot=t.left;
		t.left=newRoot.right;
		newRoot.right=t;
		return newRoot;
	}
}
