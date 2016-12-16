package chapter6.adt;

import java.util.NoSuchElementException;

/**
 * 左式堆
 * @author wenl
 *
 * @param <AnyType>
 */
public class LeftistHeap<AnyType extends Comparable<? super AnyType>> {
 
	private static class Node<AnyType>{
	 AnyType element;
	 Node<AnyType>left;
	 Node<AnyType>right;
	 int npl;
	public Node(AnyType element, Node<AnyType> left, Node<AnyType> right, int npl) {
		super();
		this.element = element;
		this.left = left;
		this.right = right;
		this.npl = npl;
	}
	public Node(AnyType element){
		this(element,null,null,0);
	}
 }
 private Node<AnyType>root;
 public LeftistHeap() {
	 root=null;
 }
 public void merge(LeftistHeap<AnyType>rhs){
	 if(this==rhs){
		 return ;
	 }
	 root=merge(root, rhs.root);
	 rhs.root=null;
 }
 public void insert(AnyType x){
	 merge(new Node<AnyType>(x), root);
 }
 public AnyType findMin(){
	 return root.element;
 }
 public boolean isEmpty(){
	 return root==null;
 }
 public AnyType deleteMin(){
	 if(isEmpty()){
		 throw new NoSuchElementException();
	 }
	 AnyType item=root.element;
	 root=merge(root.left, root.right);
	 return item;
 }
 private Node<AnyType>merge( Node<AnyType> h1, Node<AnyType>h2){
	 //TBD
	 if(h1==null){
		 return h2;
	 }
	 if(h2==null){
		 return h1;
	 }
	 if(h1.element.compareTo(h2.element)<0){
		 h1=merge1(h1,h2);
	 }else{
		 h1=merge1(h2,h1); 
	 }
	 return h1;
 }
 private Node<AnyType>merge1( Node<AnyType> h1, Node<AnyType>h2){
	 //TBD
	 if(h1.left==null){
		 h1.left=h2;
	 }else{
		 h1.right=merge(h1.right,h2);
		 if(h1.right.npl>h1.left.npl){
			 swapChildren(h1);
		 }
		 h1.npl=h1.right.npl+1;
	 }
	 
	 return h1;
 }
 private void swapChildren( Node<AnyType> h1){
	 //TBD
	 Node<AnyType>newLeft=h1.right;
	 Node<AnyType>newRight=h1.left;
	 h1.left=newLeft;
	 h1.right=newRight;
 }
}
