package chapter3.adt;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author wenl
 * 1.保存基本数据，与容量，当前项数和
 * 2.已一种机制改变基础数组的容量ensureCapacity方法
 * 2.提供get,set,size,isEmpty,clear
 * remove,add(两种类型)如果数组的容量与当前大小相同将进行扩容
 * 实现一个Iterator接口的类，迭代数组中的下标，并提供next，hasNext，remove方法
 *
 * @param <AnyType>
 */
public class MyArrayList<AnyType>implements Iterable<AnyType> {
	private static final int DEFAULT_CAPACITY=10;
	private int thesize;
	private AnyType[] item;
	
	public MyArrayList() {
		clear();
	}
	public int size(){
		return this.thesize;
	}
	public boolean isEmpty(){
		return this.thesize==0;
	}
	
	public void clear() {
		this.thesize=0;
		ensureCapacity(DEFAULT_CAPACITY);
	}

	public AnyType get(int index){
		if(index<0||index>=size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		return this.item[index];
	}
	public AnyType set(int index,AnyType value){
		if(index<0||index>=size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		AnyType oldValue=	this.item[index];
		this.item[index]=value;
		return oldValue;
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {
		if(capacity==size()){
			return;
		}
		AnyType[]old=this.item;
		this.item=(AnyType[])new Object[capacity];
		for(int i=0;i<this.thesize;i++){
			this.item[i]=old[i];
		}
	}
	public boolean add(AnyType x){
		add(size(),x);
		return true;
	}
	public void add(int idx, AnyType x) {
		if(size()==this.item.length){
			ensureCapacity(size()*2+1);
		}
		for(int i=size();i>idx;i++){
			this.item[i]=this.item[i-1];
		}
		this.item[idx]=x;
		this.thesize++;
	}
	public AnyType remove(int idx){
		AnyType removedItem=this.item[idx];
		for(int i=idx;i<size()-1;i++){
			this.item[i]=this.item[i+1];
		}
		this.thesize--;
		return removedItem;
	}
	
	public void addAll(Iterable<? extends AnyType>items){
		Iterator<? extends AnyType>iter=items.iterator();
		while(iter.hasNext()){
			add(iter.next());
		}
	}
	@Override
	public Iterator<AnyType> iterator() {
		return new ArrayListIterator();
	}
	public ListIterator<AnyType> listIterator(int current) {
		return new ArrayListIterator(current);
	}
	public Iterator<AnyType> reverseIterator(){
		return new ArrayReverseIterator();
	}
	private class ArrayReverseIterator implements Iterator<AnyType>{
		private int current=size();
		private int lastIndex=size()-1;
		@Override
		public boolean hasNext() {
			return current>0;
		}

		@Override
		public AnyType next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			lastIndex=current;
			return item[--current];
		}

		@Override
		public void remove() {
			MyArrayList.this.remove(lastIndex);
		}

	
		
	}
	private class ArrayListIterator implements ListIterator<AnyType>{
		private int current;
		private int lastIndex;
		public ArrayListIterator(){
			this.current=0;
			lastIndex=0;
		}
		public ArrayListIterator(int current){
			this.current=current;
			lastIndex=current-1;
		}
		@Override
		public boolean hasNext() {
			return this.current<size();
		}

		@Override
		public AnyType next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			lastIndex=current;
			return item[current++];
		}

		@Override
		public void remove() {
			current=lastIndex;
			MyArrayList.this.remove(lastIndex);
		}

		@Override
		public boolean hasPrevious() {
			return this.current>=0;
		}

		@Override
		public AnyType previous() {
			if(!hasPrevious()){
				throw new NoSuchElementException();
			}
			lastIndex=current;
			return item[--current];
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(AnyType data) {
			MyArrayList.this.set(current,data);
		}

		@Override
		public void add(AnyType data) {
			MyArrayList.this.add(current++,data);
		}
		
	}
	public static void main(String[] args) {
		MyArrayList<Integer>list=new MyArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		ListIterator<Integer>iter=list.listIterator(list.size());
		while(!iter.hasPrevious()){
			System.out.println(iter.previous());
			iter.remove();
		}
		System.out.println("in");
		Iterator<Integer>reserverIter=list.reverseIterator();
		while(reserverIter.hasNext()){
			System.out.println(reserverIter.next());
//			reserverIter.remove();
		}
//		Iterator<Integer>iter=list.iterator();
//		
//		while(iter.hasNext()){
//			System.out.println(iter.next());
//		}
	}
}
