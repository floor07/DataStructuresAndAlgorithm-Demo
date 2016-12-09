package chapter3.adt;

import java.util.LinkedList;

/**
 * 使用LinkedList实现的双端队列
 * @author wenl
 *
 * @param <AnyType>
 */
public class MyDeque<AnyType> {
	
	private LinkedList<AnyType> items;
	public MyDeque() {
		items=new LinkedList<AnyType>();
	}
	public void push(AnyType x){
		items.addFirst(x);
	}
	public AnyType pop(){
		return items.removeFirst();
	}
	public void inject(AnyType x){
		items.addLast(x);
	}
	public AnyType eject(){
		return items.removeLast();
	}
}
