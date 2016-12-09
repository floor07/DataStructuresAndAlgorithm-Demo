package chapter3.adt;

/**
 * 使用链完成的简单队列
 * @author wenl
 *
 * @param <AnyType>
 */
public class SingleQueue<AnyType> {
	
	private Node<AnyType> head;
	private Node<AnyType> tail;
	private int size;
	public SingleQueue() {
		head = new Node<AnyType>();
		tail = null;
	}

	void enqueue(AnyType x) {
		Node<AnyType> p = new Node<AnyType>(x);
		if(size==0){
			head.next=p;
			tail=p;
		}else{
			tail.next=p;
			tail=p;
		}
		size++;
	}

	AnyType dequeue() {
		if(head.next!=null){
			AnyType temp = head.next.data;
			head.next=head.next.next;
			size--;
			return temp;
		}else{
			return null;
		}
	}

	private static class Node<AnyType> {
		AnyType data;
		Node<AnyType> next;

		public Node() {
			this(null, null);
		}

		public Node(AnyType x) {
			this(x, null);
		}

		public Node(AnyType data, Node<AnyType> next) {
			super();
			this.data = data;
			this.next = next;
		}
	}
	public static void main(String[] args) {
		SingleQueue<Integer>a=new SingleQueue<Integer>();
		a.enqueue(1);
		a.enqueue(2);
		a.enqueue(3);
		a.enqueue(4);
		System.out.println(a.dequeue());
		System.out.println(a.dequeue());
		System.out.println(a.dequeue());
		System.out.println(a.dequeue());
	}
}
