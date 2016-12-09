package chapter3.adt;

/**
 * 自己实现的栈
 * @author wenl
 * @param <AnyType>
 */
public class SingleStack<AnyType> {
	
	private Node<AnyType> head;

	public SingleStack() {
		head = new Node<AnyType>();
	}

	void push(AnyType x) {
		Node<AnyType> p = new Node<AnyType>(x);
		p.next = head.next;
		head.next = p;
	}

	AnyType top() {
		if (head.next == null) {
			return null;
		} else {
			return head.next.data;
		}
	}

	AnyType pop() {
		if (head.next != null) {
			AnyType data = head.next.data;
			head.next = head.next.next;
			return data;
		} else {
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
		SingleStack<Integer> a = new SingleStack<Integer>();
		a.push(1);
		a.push(2);
		a.push(3);
		System.out.println(a.top() + "：" + a.pop());
		System.out.println(a.top() + "：" + a.pop());
		System.out.println(a.top() + "：" + a.pop());
		System.out.println(a.top() + "：" + a.pop());
	}
}
