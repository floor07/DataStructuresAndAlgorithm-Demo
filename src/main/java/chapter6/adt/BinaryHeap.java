package chapter6.adt;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * @author Administrator
 * 以数组实现，不包含合并操作，二叉堆
 * @param <AnyType>
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>>{
	
	private int currentSize;
	private AnyType[]array;
	public BinaryHeap() {
	} 
	@SuppressWarnings("unchecked")
	public BinaryHeap(int capacity){
		currentSize=0;
		array=(AnyType[]) new Comparable[(capacity*2)+1];
	}
	@SuppressWarnings("unchecked")
	public BinaryHeap(AnyType[]items){
		currentSize=items.length;
		array=(AnyType[]) new Comparable[(currentSize*2)+1];
		int i=1;
		for(AnyType item:items){
			array[i++]=item;
		}
		buildHeap();
	}
	public void insert(AnyType item){
		if(currentSize==array.length-1){
			enlargeArray(array.length*2+1);
		}
		int hole=++currentSize;
		for(;hole>1&&item.compareTo(array[hole/2])<0;hole/=2){
				array[hole]=array[hole/2];
		}
		array[hole]=item;
		array[0]=item;
	}
	public AnyType findMin(){
		
		return array[1];
	}
	public AnyType deleteMin(){
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		AnyType item=findMin();
		array[1]=array[currentSize--];
		precolateDown(1);
				return item;
	}
	public boolean isEmpty(){
		return currentSize==0;
	}
	public void makeEmpty(){
		array=null;
		currentSize=0;
	}
	/**
	 * 下滤,用于删除
	 * @param hole
	 */
	private void precolateDown(int hole){
		int childIndex=0;
		AnyType tmp=array[hole];
		while(hole*2<=currentSize){
			childIndex=hole*2;
			if(childIndex!=currentSize&&array[childIndex].compareTo(array[childIndex+1])>0){
				childIndex++;
			}
			if(array[childIndex].compareTo(tmp)<0){
				array[hole]=array[childIndex];
			}
			
			hole=childIndex;
		}
		array[hole]=tmp;
	}
	/**
	 * 从倒数第二层开始处理
	 */
	private void buildHeap(){
		for(int i=currentSize/2;i>0;i--){
			precolateDown(i);
		}
	}
	/**
	 * 数组扩容的方式 扩大堆
	 * @param newSize
	 */
	private void enlargeArray(int newSize){
		array=Arrays.copyOf(array, newSize);
	}
}
