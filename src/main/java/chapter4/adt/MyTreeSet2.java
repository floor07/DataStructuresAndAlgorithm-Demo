package chapter4.adt;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Administrator 这个二叉查找树，保存2个链，一个是next ，一个是prev
 */
public class MyTreeSet2<AnyType extends Comparable<? super AnyType>> {
	private BinaryNode<AnyType> root;
	private int modCount;

	private static class UnderflowException extends Exception {
		private static final long serialVersionUID = -2328260157445764070L;
	}

	private static class BinaryNode<AnyType> {
		AnyType element;
		BinaryNode<AnyType> left;
		BinaryNode<AnyType> right;
		BinaryNode<AnyType> next;
		BinaryNode<AnyType> prev;

		public BinaryNode(AnyType element, BinaryNode<AnyType> next, BinaryNode<AnyType> prev) {
			this(element, null, null, next, prev);
		}

		public BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right,
				BinaryNode<AnyType> next, BinaryNode<AnyType> prev) {
			super();
			this.element = element;
			this.left = left;
			this.right = right;
			this.next = next;
			this.prev = prev;
		}
	}

	public void insert(AnyType x) {

		root = insert(x, root, null, null);
	}

	private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t, BinaryNode<AnyType> next,
			BinaryNode<AnyType> prev) {
		if (t == null) {
			BinaryNode<AnyType> newroot = new BinaryNode<AnyType>(x, next, prev);
			if (next != null) {
				next.prev = newroot;
			}
			if (prev != null) {
				prev.next = newroot;
			}
			return newroot;
		}
		int compareValue = x.compareTo(t.element);
		if (compareValue < 0) {
			t = insert(x, t.left, t, prev);
		} else if (compareValue > 0) {
			t = insert(x, t.left, next, t);
		} else {

		}
		return t;
	}

	public void remove(AnyType x) {
		root = remove(x, root);
	}

	private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {

		int compareValue = x.compareTo(t.element);
		if (compareValue < 0) {
			t = remove(x, t.left);
		} else if (compareValue > 0) {
			t = remove(x, t.right);
		} else if (t.left != null && t.right != null) {
			t = findMin(t.right);
			t.right = remove(x, t.right);
		} else {
			modCount++;
			t.prev.next = t.next;
			t.next.prev = t.prev;
			t = t.left != null ? t.left : t.right;
		}
		return t;
	}

	public AnyType findMin() throws UnderflowException {
		if (isEmpty()) {
			throw new UnderflowException();
		} else {
			return findMin(root).element;
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
		while (t.left != null) {
			t = t.left;
		}
		return t;
	}

	public AnyType findMax() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		else
			return findMax(root).element;
	}

	private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
		while (t.right != null) {
			t = t.right;
		}
		return t;
	}

	public void printTree() {
		if (isEmpty()) {
			System.out.println("This tree is empty");
		} else {
			printTree(root);
		}

	}

	private void printTree(BinaryNode<AnyType> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	public boolean contains(AnyType x) {
		return contains(x, root);
	}

	private boolean contains(AnyType x, BinaryNode<AnyType> t) {
		if (t == null) {
			return false;
		}
		int compareValue = x.compareTo(t.element);
		if (compareValue < 0) {
			return contains(x, t.left);
		} else if (compareValue > 0) {
			return contains(x, t.right);
		} else {
			return true;
		}
	}

	public MyTreeSet2() {
		root = null;
	}

	public void makeEmpty() {
		modCount++;
		root = null;
	}
	public java.util.Iterator<AnyType> iterator()
	{
	return new MyTreeSet2Iterator( );
	}
	private class MyTreeSet2Iterator implements java.util.Iterator<AnyType>{
		private BinaryNode<AnyType>current;
		private BinaryNode<AnyType>prev;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
		private boolean atEnd = false;
		
		
		@Override
		public boolean hasNext() {
			return !atEnd;
		}

		@Override
		public AnyType next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			if(expectedModCount!=modCount){
				throw new ConcurrentModificationException();
			}
			AnyType item=current.element;
			prev=current;
			current=current.next;
			okToRemove=true;
			return item;
		}

		@Override
		public void remove() {
			if(expectedModCount!=modCount){
				throw new ConcurrentModificationException();
			}
			if(!okToRemove){
				throw new IllegalStateException( );
			}
			MyTreeSet2.this.remove(prev.element);
			okToRemove=false;
		}
		
	}
}
