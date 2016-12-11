package chapter4.adt;

/**
 * 二叉查找树
 * 安装书中介绍实现
 * @author wenl
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	private BinaryNode<AnyType>root;//根节点
	/**
	 * 自定义异常
	 */
	private static class UnderflowException extends Exception{
		private static final long serialVersionUID = 1L;
	}
	private static class BinaryNode<AnyType extends Comparable<? super AnyType>>{
		AnyType element;// 节点内容
		BinaryNode<AnyType>left;//左儿子
		BinaryNode<AnyType>right;//右儿子
		public BinaryNode(AnyType theElement){
			this(theElement,null,null);
		}
		public BinaryNode(
				AnyType theElement,
				BinaryNode<AnyType>left,
				BinaryNode<AnyType>right){
			this.element=theElement;
			this.left=left;
			this.right=right;
		}
	}
    public void makeEmpty(){
    	this.root=null;
    }
    public boolean isEmpty(){
    	return this.root==null;
    }
	public boolean contain(AnyType x){
		return contains(x,root);
	}
	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if(t==null){
			return false;
		}else {
			int compareValue=x.compareTo(t.element);
			if(compareValue<0){
				return contains(x,t.left);
			}else if(compareValue>0){
				return contains(x,t.right);
			}else{
				return true;
			}
		}
	}
	public AnyType findMin() throws UnderflowException{
	    if(isEmpty()){
	    	throw new UnderflowException();
	    }
	    return findMin(root).element;
	}
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		if(t==null){
			return null;
		}else if(t.left!=null){
			return findMin(t.left);
		}else {
			return t;
		}
	}
	public AnyType findMax() throws UnderflowException{
		  if(isEmpty()){
		    	throw new UnderflowException();
		    }
		    return findMax(root).element;
	}
	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		if(t!=null){
			while(t.right!=null){
				t=t.right;
			}
		}
		return t;
	}
	public void insert(AnyType x){
		root=insert(x,root);
	}
	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
		if(t==null){
			return new BinaryNode<AnyType>(x);
		}
		int compareValue=x.compareTo(t.element);
		if(compareValue<0){
		 t=insert(x,t.left); 	
		}else if(compareValue>0){
		 t=insert(x,t.right); 
		}else{
		 //什么都不做
		}
		return t;
	}
	public void remove(AnyType x) throws UnderflowException{
		root=remove(x,root);
	}
	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) throws UnderflowException {
		if(t==null){
			throw new UnderflowException();
		}
		int compareValue=x.compareTo(t.element);
		if(compareValue<0){
			t=remove(x,t.left);
		}else if(compareValue>0){
			t=remove(x,t.right);
		}else if(t.left!=null&&t.right!=null){
			t.element=findMin(t.right).element;
			t.right=remove(t.element,t.right);
		}else {
			t=(t.left!=null)?t.left:t.right;
		}
		return t;
	}
	public void printTree(){
		if(isEmpty()){
			System.out.println("Empty tree");
		}
		printTree(root);
	}
	public void printTree(BinaryNode<AnyType>t){
		if(t!=null){
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
}
