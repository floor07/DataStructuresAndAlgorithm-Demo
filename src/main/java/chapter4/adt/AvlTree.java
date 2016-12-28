package chapter4.adt;

/**
 * @author Administrator
 *AVL树自实现
 * @param <AnyType>
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

	private AvlNode<AnyType> root;

	private static class AvlNode<AnyType extends Comparable<? super AnyType>> {
		AnyType element;
		AvlNode<AnyType> left;
		AvlNode<AnyType> right;
		int height;

		AvlNode(AnyType element) {
			this(element, null, null);
		}

		AvlNode(AnyType element, AvlNode<AnyType> left, AvlNode<AnyType> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			this.height = 0;
		}
	}

	private int height(AvlNode<AnyType> t) {
		return t == null ? -1 : t.height;
	}

	public void insert(AnyType x) {
		root = insert(x, root);
	}

	private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
		if (t == null) {
			return new AvlNode<AnyType>(x);
		}
		int compareValue = x.compareTo(t.element);
		if (compareValue < 0) {
			t.left = insert(x, t.left);
			if (height(t.left) - height(t.right) == 2) {
				if (t.left.element.compareTo(x) < 0) {
					// 左左
					t = rotateWithLeftChild(t);
				} else {
					t = doubleWithLeftChild(t);
				}
			}
		} else if (compareValue > 0) {
			t.right = insert(x, t.right);
			if (height(t.right) - height(t.left) == 2) {
				if (t.right.element.compareTo(x) > 0) {
					t = rotateWithRightChild(t);
				} else {
					t = doubleWithRightChild(t);
				}
			}
		} else {
			// do nothing;
		}
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> t) {
		t.right = rotateWithLeftChild(t.right);
		return rotateWithRightChild(t);
	}

	private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> oldroot) {
		AvlNode<AnyType> newRight = oldroot;
		AvlNode<AnyType> newLeft = oldroot.left;
		AvlNode<AnyType> newRoot = oldroot.left.right;
		newRight.left = newRoot.right;
		newLeft.right = newRoot.left;
		newRoot.left = newLeft;
		newRoot.right = newRight;
		newLeft.height = Math.max(height(newLeft.left), height(newLeft.right)) + 1;
		newRight.height = Math.max(height(newRight.left), height(newRight.right)) + 1;
		newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
		return newRoot;
	}

	private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> oldroot) {
		// 右右
		AvlNode<AnyType> newRoot = oldroot.right;
		oldroot.right = newRoot.left;
		newRoot.left = oldroot;
		oldroot.height = Math.max(height(oldroot.left), height(oldroot.right)) + 1;
		newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
		return newRoot;
	}

	private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> oldroot) {
		//
		AvlNode<AnyType> newRoot = oldroot.left;
		oldroot.left = newRoot.right;
		newRoot.right = oldroot;
		oldroot.height = Math.max(height(oldroot.left), height(oldroot.right)) + 1;
		newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;
		return newRoot;
	}

}
