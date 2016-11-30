package chapter7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 快速选择算法，解决topN，使用分治思想
 * @author Administrator
 *
 */
public class QuickSelect {
	
	public static int CUTOFF = 5;// 5个元素内使用插入排序

	public static <AnyType extends Comparable<? super AnyType>> void quickSelect(AnyType[] a, int k) {
		quickSelect(a, 0, a.length - 1, k);
	}

	/**
	 * 快速选择核心
	 * 
	 * @param a
	 *            原始数组
	 * @param left
	 *            左边界
	 * @param right
	 *            右边界
	 * @param k
	 *            需要选择的位
	 */
	private static <AnyType extends Comparable<? super AnyType>> void quickSelect(AnyType[] a, int left, int right,
			int k) {
		if (left + CUTOFF <= right) {
			AnyType pivot = median3(a, left, right);
			int i = left;
			int j = right - 1;
			while (true) {
				while (a[++i].compareTo(pivot) < 0)
					;
				while (a[--j].compareTo(pivot) > 0)
					;
				if (i < j) {
					swapReferences(a, i, j);
				} else {
					break;
				}
			}
			swapReferences(a, i, right - 1);
			if (k <= i) {
				quickSelect(a, left, i - 1, k);
			} else if (k > i + 1) {
				quickSelect(a, i + 1, right, k);
			}
		} else {
			insertSort(a, left, right);
		}
	}
	/**
	* 三数中值分割法
	* @param a 原始数组
	* @param left 左边界
	* @param right 右边界
	* @return 返回枢元
	*/
	private static <AnyType extends Comparable<? super AnyType>> AnyType median3(AnyType[] a, int left, int right) {
		int center = (left + right) / 2;
		if (a[center].compareTo(a[left]) < 0) {
			swapReferences(a, left, center);
		}
		if (a[right].compareTo(a[left]) < 0) {
			swapReferences(a, left, right);
		}
		if (a[right].compareTo(a[center]) < 0) {
			swapReferences(a, center, right);
		}
		swapReferences(a, center, right - 1);
		return a[right - 1];
	}

	private static <AnyType extends Comparable<? super AnyType>> void swapReferences(AnyType[] a, int i, int j) {
		AnyType tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	/**
	 * 插入排序历程
	 * @param a 需要排序的数组
	 * @param left   排序开始坐标
	 * @param right  排序终止坐标
	 */
	public static <AnyType extends Comparable<? super AnyType>> void insertSort(AnyType[] a, int left, int right) {
		int j = 0;
		for (int p = left; p <= right; p++) {
			AnyType tmp = a[p];
			for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
				a[j] = a[j - 1];
			}
			a[j] = tmp;
		}
	}

	public static void main(String[] args) {
		List<Integer> a = new ArrayList<Integer>();
		for (int i = 1; i <= 150; i++) {
			a.add(i);
		}
		Integer[] aArr = new Integer[150];
		Collections.shuffle(a);
		aArr = a.toArray(aArr);
		System.out.println(Arrays.toString(aArr));
		System.out.println(aArr[9]);
		QuickSelect.quickSelect(aArr, 10);
		System.out.println(aArr[9]);
	}
}
