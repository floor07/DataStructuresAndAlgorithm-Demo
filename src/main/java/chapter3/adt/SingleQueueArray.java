package chapter3.adt;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ArrayList简单实现Queue
 * 使用固定大小的数组，循环引用
 * @author wenl
 * @param <AnyType>
 */
public class SingleQueueArray<AnyType> {
	
	private Integer headIndex;
	private Integer nextItemIndex;
	private List<AnyType>elements;
	private Integer maxSize;
	private Integer currentSize;
	
	public SingleQueueArray() {
		this(10);
	}
	public SingleQueueArray(int size) {
		maxSize=size;
		this.elements=new ArrayList<AnyType>(maxSize);
		headIndex=0;
		nextItemIndex=0;
		currentSize=0;
	}
	/**
	 * 入队
	 * @param x 需要入队的元素
	 */
	void enqueue(AnyType x){
		if(!full()){
			if(this.elements.size()<maxSize){
				this.elements.add(x);
			}else{
				this.elements.set(nextItemIndex,x);
			}
			nextItemIndex=(nextItemIndex+1)%maxSize;
			currentSize++;
		}else{
			System.out.println("in");
		}
	}
	/**
	 * 出队 ，返回头部元素
	 * @return
	 */
	AnyType dequeue(){
		if(!empty()){
			AnyType tmp=this.elements.get(headIndex);
			headIndex=(headIndex+1)%maxSize;
			currentSize--;
			return tmp;
		}else{
			return null;
		}
	}
	/**
	 * 判断队列是否为空
	 * @return
	 */
	boolean empty()
	{
		return currentSize==0;
	}
	/**
	 * 判断队列是否已满
	 * @return
	 */
	boolean full(){
		return currentSize==maxSize;
	}
	
	public static void main(String[] args) {
		SingleQueueArray<Integer>a=new SingleQueueArray<Integer>(8);
		for(int i=0;i<10;i++){
			a.enqueue(i);
		}
		System.out.println(a.full());
		System.out.println(a.dequeue());
		System.out.println(a.full());
		a.enqueue(0);
		for(int i=0;i<10;i++){
			System.out.println(a.dequeue());
		}
		System.out.println(a.empty());
	}
}
