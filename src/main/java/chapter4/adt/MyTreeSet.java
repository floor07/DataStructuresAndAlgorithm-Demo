package chapter4.adt;

import java.util.ConcurrentModificationException;

/**
 * 低层为二叉查找树，每个节点保存一个父链
 * @author Administrator
 */
public class MyTreeSet <AnyType extends Comparable<? super AnyType>>{

public MyTreeSet() {
		root=null;
	}
public static	class UnderflowException extends Exception {
		private static final long serialVersionUID = 5565070743126879217L; };
		private BinaryNode<AnyType> root;
		int modCount = 0;
		private static class BinaryNode<AnyType>{
			AnyType element;
			BinaryNode<AnyType>left;
			BinaryNode<AnyType>right;
			BinaryNode<AnyType>parent;
			public BinaryNode(AnyType element,BinaryNode<AnyType> parent) {
				this(element,null,null,parent);
			}
			public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right,
					BinaryNode<AnyType> parent) {
				super();
				this.element = element;
				this.left = left;
				this.right = right;
				this.parent = parent;
			}
		}
		public void insert(AnyType x){
			root=insert(x,root,null);
		}
		public void remove(AnyType x){
			root=remove(x,root);
		}
		public boolean contains( AnyType x )
		{ 
			return contains( x, root ); 
		}
		public void makeEmpty(){
			modCount++;
			root=null;
		}
		public AnyType findMin(){
			return findMin(root).element;
		}
		public AnyType findMax(){
			return findMax(root).element;
		}
		private boolean contains( AnyType x, BinaryNode<AnyType> t ){
			if(t==null){
				return false;
			}
			int compareValue=x.compareTo(t.element);
			if(compareValue<0){
				return contains(x, t.left);
			}else if(compareValue<0){
				return contains(x, t.right);
			}else{
				return true;
			}
		}
		
		public boolean isEmpty(){
			return root==null;
		}
		public void printTree()
		{
			if ( isEmpty() )
				System.out.println( "Empty tree" );
			else
				printTree(root);
		}
		private void printTree(BinaryNode<AnyType> t) {
			if(t!=null){
				printTree(t.left);
				System.out.println(t.element);
				printTree(t.right);
			}
		}
		private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t,
				BinaryNode<AnyType> pt ){
			if(t==null){
				modCount++;
				return new BinaryNode<AnyType>(x,pt);
			}
			int compareValue=x.compareTo(t.element);
			if(compareValue<0){
				t=insert(x,t.left,t);
			}else if(compareValue>0){
				t=insert(x,t.right,t);
			}
			return t;
		}
		private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t ){
			if(t==null){
				return t;
			}
			int compareValue=x.compareTo(t.element);
			if(compareValue<0){
				remove(x,t.left);
			}else if(compareValue>0){
				remove(x,t.right);
			}else if(t.left!=null&&t.right!=null){
				modCount++;
				t=findMin(t.right);
				t.right=remove(x,t.right);
			}else {
				modCount++;
				BinaryNode<AnyType>chlid=t.left!=null?t.left:t.right;
				chlid.parent=t.parent;
				t=chlid;
			}
			return t;
		}
		
		private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
			while(t.left!=null){
				t=t.left;
			}
			return t;
		}
		private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t ){
			while(t.right!=null){
				t=t.right;
			}
			return t;
		}
		public java.util.Iterator<AnyType> iterator(){
			return new MyTreeSetIterator();
		}
		private class MyTreeSetIterator implements java.util.Iterator<AnyType>{
			private BinaryNode<AnyType> current = findMin(root);
			private BinaryNode<AnyType> previous=null;
			private int expectedModCount = modCount;
			private boolean okToRemove = false;
			private boolean atEnd = false;
			
			@Override
			public boolean hasNext() {
				return !atEnd;
			}

			@Override
			public AnyType next() {
				AnyType item=current.element;
				previous=current;
				if(current.right!=null){
					current=findMax(current);
				}else{
					BinaryNode<AnyType> child=current;
					current=current.parent;
					while(current!=null&&current.left!=child){
						child=current;
						current=current.parent;
					}
					if(current==null){
						atEnd = true;
					}
				}
				okToRemove=true;
				return item;
			}

			@Override
			public void remove() {
				if(!okToRemove){
					throw new IllegalStateException();
				}
				if( modCount != expectedModCount){
					throw new ConcurrentModificationException();
				}
				MyTreeSet.this.remove(previous.element);
				okToRemove=false;
			}
			
		}
}
