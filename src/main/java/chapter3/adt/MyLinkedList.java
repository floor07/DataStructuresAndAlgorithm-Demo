package chapter3.adt;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 包含两端的链，和一些方法
 * Node内部类 包含当前数据，和上一个节点和下一个节点的链
 * LinkedListIterator类 提供next,hasNext,remove方法
 * @author Administrator
 *
 */
public class MyLinkedList <AnyType>implements Iterable<AnyType>{
	private int theSize;
	private int modCount=0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;
	
	public void addFirst( AnyType x ){
		addBefore(beginMarker.next, x);
	}
	public void addLast( AnyType x ){
		addBefore(endMarker, x);
	}
	public void removeFirst( ){
		remove(beginMarker.next);
	}
	public void removeLast( ){
		remove(endMarker.prev);
	}
	public AnyType getFirst(){
		return get(0);
	}
	public AnyType getLast(){
		return get(size()-1);
	}
	private static class Node<AnyType>{
		public Node<AnyType>prev;
		public Node<AnyType>next;
		public AnyType data;
		public Node(Node<AnyType> prev, Node<AnyType> next, AnyType data) {
			super();
			this.prev = prev;
			this.next = next;
			this.data = data;
		}
	}
	
	 public MyLinkedList() {
		clear();
	}
	
	
	private void clear() {
		this.beginMarker=new Node<AnyType>(null, null, null);
		this.endMarker=new Node<AnyType>( this.beginMarker,null, null);
		this.theSize=0;
		modCount++;
	}

	public int size(){
		return this.theSize;
	}
	public boolean isEmpty(){
		return this.theSize==0;
	}
	public boolean add(AnyType x){
		add(size(),x);
		return true;
	}
	public void add(int idx, AnyType x) {
		addBefore(getNode(idx),x);
	}
	public AnyType get(int idx){
		return getNode(idx).data;
	}
	public AnyType set(int idx,AnyType x){
		Node<AnyType>p=getNode(idx);
		AnyType old=p.data;
		p.data=x;
		return old;
	}
	public AnyType remove(int idx){
		return remove(getNode(idx));
		
	}
	public AnyType remove(Node<AnyType> node) {
		AnyType oldValue=node.data;
		Node<AnyType>prev=node.prev;
		Node<AnyType>next=node.next;
		prev.next=next;
		next.prev=prev;
		this.theSize--;
		this.modCount++;
		return oldValue;
	}


	/**
	 * 注意 beginMarker没有实际数据
	 * 而endMarker有实际数据
	 * @param idx
	 * @return
	 */
	public Node<AnyType> getNode(int idx) {
		if(idx<0||idx>size()){
			throw new NoSuchElementException();
		}
		Node<AnyType> p=null;
		if(idx<size()/2){
			p=beginMarker.next;
			for(int i=0;i<idx;i++){
				p=p.next;
			}
			
		}else{
			p=endMarker;
			for(int i=size();i>idx;i--){
				p=p.prev;
			}
		}
		return p;
	}

	public void addBefore(Node<AnyType>p, AnyType x) {
		Node<AnyType>newNode=new Node<AnyType>(p.prev,p,x);
		newNode.prev.next=newNode;
		p.prev=newNode;
		this.theSize++;
		this.modCount++;
	}

	public boolean contains(AnyType x){
		Node<AnyType>p=beginMarker.next;
		while(p.next!=endMarker&&!p.data.equals(x)){
			p=p.next;
		}
		return p!=endMarker;
	}
	public void removeAll(Iterable<? extends AnyType>items){
		Iterator<? extends AnyType>iter=items.iterator();
		
		while(iter.hasNext()){
			Iterator<AnyType>thisIter=iterator();
			AnyType item=iter.next();
			while(thisIter.hasNext()){
				if(item.equals(thisIter.next())){
					thisIter.remove();
				}
			}
		}
	}


	@Override
	public Iterator<AnyType> iterator() {
		return new LinkedListIterator();
	}
	public ListIterator<AnyType>listIterator(){
		return new LinkedListIterator();
	}
	
	public void reverseList()
	{
		Node<AnyType>current,prev,next;
		prev=endMarker;
		current=beginMarker.next;
		next=current.next;
		while(current!=endMarker){
			current.next=prev;
			current.prev=next;
			prev=current;
			current=next;
			next=next.next;
		}
		Node<AnyType> tmp=endMarker.prev;
		endMarker.prev=	beginMarker.next;
		beginMarker.next=tmp;
	}
	
	private class LinkedListIterator implements  ListIterator<AnyType>{
		private Node<AnyType>current=beginMarker.next;
		public int expectedModCount=modCount;
		private boolean okToRemove=false;
		private boolean isback=false;
		@Override
		public boolean hasNext() {
			return current!=endMarker;
		}

		@Override
		public AnyType next() {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			AnyType data=this.current.data;
			this.current=this.current.next;
			okToRemove=true;
			isback=false;
			return data ;
		}


		@Override
		public boolean hasPrevious() {
			
			return current.prev!=beginMarker;
		}

		@Override
		public AnyType previous() {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			if(!hasPrevious()){
				throw new NoSuchElementException();
			}
		
			this.current=this.current.prev;
			AnyType data=this.current.data;
			okToRemove=true;
			isback=true;
			return data;
		}

		@Override
		public void add(AnyType e) {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			MyLinkedList.this.addBefore(current.next,e);
		}

		@Override
		public void set(AnyType e) {
			current.data=e;
		}


		@Override
		public void remove() {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			if(!okToRemove){
				throw new IllegalStateException();
			}
			// 需要修改
			if(!isback){
				MyLinkedList.this.remove(current.prev);
			}else{
				MyLinkedList.this.remove(current);
			}
			okToRemove=false;
			expectedModCount++;
		}
		
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}
	}
	public static void main(String[] args) {
		MyLinkedList<Integer>a=new MyLinkedList<Integer>();
		a.addLast(1);
		a.addLast(2);
		a.addLast(3);
		a.addLast(4);
		a.addLast(5);
		a.addLast(6);
//		for(Integer tmp:a){
//			System.out.println(tmp);
//		}
		
		a.reverseList();
		for(Integer tmp:a){
			System.out.println(tmp);
		}
		System.out.println(a.getLast());
		System.out.println(a.getFirst());
	}
}
