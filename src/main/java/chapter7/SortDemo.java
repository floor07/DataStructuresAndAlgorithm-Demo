package chapter7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortDemo {

	/**
	 * 插入排序
	 */
	public static <AnyType extends Comparable<? super AnyType>> void insertSort(AnyType[] a) {
		int j = 0;
		for (int p = 1; p < a.length; p++) {
			AnyType tmp = a[p];
			for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
				a[j] = a[j - 1];
			}
			a[j] = tmp;
		}

	}

	/**
	 * 希尔排序
	 * 
	 * @param a
	 */
	public static <AnyType extends Comparable<? super AnyType>> void shellsort(AnyType[] a) {
		int j;
		for (int gap = a.length / 2; gap > 0; gap /= 2) {
			for (int p = gap; p < a.length; p++) {
				AnyType tmp = a[p];
				for (j = p; j >= gap && tmp.compareTo(a[j - gap]) < 0; j = j - gap) {
					a[j] = a[j - gap];
				}
				a[j] = tmp;
			}
		}
	}

	/**
	 * 堆排序
	 * 
	 * @author Administrator
	 */
	public static class HeadSort {
		private static <AnyType extends Comparable<? super AnyType>> void swapReferences(AnyType[] a, int i, int j) {
			AnyType tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}

		/**
		 * 堆排序
		 */
		public static <AnyType extends Comparable<? super AnyType>> void headsort(AnyType[] a) {
			// 1.快速构建堆
			for (int i = a.length / 2; i >= 0; i--) {
				percDown(a, i, a.length);
			}
			System.out.println(Arrays.toString(a));
			for (int i = a.length - 1; i > 0; i--) {
				// j将最大的放到最后
				swapReferences(a, i, 0);
				// 之后root元素向下过滤
				percDown(a, 0, i);
			}
		}

		/**
		 * 堆排序下滤
		 * 
		 * @param a
		 * @param i
		 * @param length
		 */
		private static <AnyType extends Comparable<? super AnyType>> void percDown(AnyType[] a, int i, int length) {

			AnyType tmp = a[i];
			int child;
			for (; leftChild(i) < length; i = child) {
				child = leftChild(i);
				if (child != length - 1 && a[child].compareTo(a[child + 1]) < 0) {
					child++;
				}
				if (tmp.compareTo(a[child]) < 0) {
					a[i] = a[child];
				} else {
					break;
				}
			}
			// 最后
			a[i] = tmp;
		}

		private static int leftChild(int i) {
			return 2 * i + 1;
		}
	}

	public static class MergeSort {
		/**
		 * 归并排序
		 * 
		 * @param a
		 */
		@SuppressWarnings("unchecked")
		public static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a) {
			AnyType[] tmpArray = (AnyType[]) new Comparable[a.length];
			mergeSort(a, tmpArray, 0, a.length - 1);
		}

		private static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a, AnyType[] tmpArray,
				int left, int right) {
			if (left < right) {
				int center = (left + right) / 2;
				// 如下方式排除了只有3个元素的情况，只有三个元素是 center=1
				mergeSort(a, tmpArray, left, center);
				mergeSort(a, tmpArray, center + 1, right);
				merge(a, tmpArray, left, center + 1, right);
			}
		}

		/**
		 * 合并函数，归并排序的基本步骤
		 * 
		 * @param a
		 *            原始数据数组
		 * @param tmpArray
		 *            归并使用的第三个临时数组
		 * @param leftPos
		 *            左边部分开始，对应图上Actr初始位置
		 * @param rightPos
		 *            右边开始 ，对应Bctr初始位置
		 * @param rightEnd
		 *            右边结束 ，对应Bctr结束位置
		 */
		private static <AnyType extends Comparable<? super AnyType>> void merge(AnyType[] a, AnyType[] tmpArray,
				int leftPos, int rightPos, int rightEnd) {
			// 一定范围内合并
			// 左边结束，对应图上Actr结束位置
			int leftEnd = rightPos - 1;
			int tmpPos = leftPos;
			// 本次合并总共包含的元素数量
			int numElements = rightEnd - leftPos + 1;
			// 进行归并
			while (leftPos <= leftEnd && rightPos <= rightEnd) {
				if (a[leftPos].compareTo(a[rightPos]) <= 0) {
					tmpArray[tmpPos++] = a[leftPos++];
				} else {
					tmpArray[tmpPos++] = a[rightPos++];
				}
			}
			while (leftPos <= leftEnd) {
				tmpArray[tmpPos++] = a[leftPos++];
			}
			while (rightPos <= rightEnd) {
				tmpArray[tmpPos++] = a[rightPos++];
			}
			// 将排序过的数据拷贝会原始数组，【只有rightEnd没有变化】。
			for (int i = 0; i < numElements; i++, rightEnd--) {
				a[rightEnd] = tmpArray[rightEnd];
			}
		}
	}
	/**
	 * 快排
	 */
	public static class QuickSort {
		
		public static int CUTOFF = 5;

		public static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a) {
			quickSort(a, 0, a.length - 1);
		}

		/**
		 * 快排核心
		 * 
		 * @param a
		 *            原始数组
		 * @param left
		 *            左边界
		 * @param right
		 *            右边界
		 */
		private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a, int left, int right) {
			if (left + CUTOFF <= right) {
				// 三数中值分割法，取枢元
				AnyType pivot = median3(a, left, right);
				int i = left;
				int j = right - 1;
				while (true) {
					// i找大于枢元的元素
					while (a[++i].compareTo(pivot) < 0)
						;
					// j找小于枢元的元素
					while (a[--j].compareTo(pivot) > 0)
						;
					if (i < j) {
						swapReferences(a, i, j);
					} else {// i>j,此轮分割结束
						break;
					}
				}
				// 交换i,与枢元
				swapReferences(a, i, right - 1);
				// 分治进行，快排
				quickSort(a, 0, i - 1);
				quickSort(a, i + 1, right);
			} else {
				insertSort(a, left, right);
			}
		}

		/**
		 * 三数中值分割法
		 * 
		 * @param a
		 *            原始数组
		 * @param left
		 *            左边界
		 * @param right
		 *            右边界
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
	}

	public static void main(String[] args) {
		List<Integer> a = new ArrayList<Integer>();
		for (int i = 0; i < 60; i++) {
			a.add(i);
		}
		Integer[] aArr = new Integer[20];
		Collections.shuffle(a);
		aArr = a.toArray(aArr);
		// System.out.println(Arrays.toString(aArr));
		// insertSort(aArr);
		// System.out.println(Arrays.toString(aArr));
		// System.out.println(Arrays.toString(aArr));
		// shellsort(aArr);
		// System.out.println(Arrays.toString(aArr));
		// System.out.println(Arrays.toString(aArr));
		// HeadSort.headsort(aArr);
		// System.out.println(Arrays.toString(aArr));
		// System.out.println(Arrays.toString(aArr));
		// MergeSort.mergeSort(aArr);
		// System.out.println(Arrays.toString(aArr));
		System.out.println(Arrays.toString(aArr));
		QuickSort.quickSort(aArr);
		System.out.println(Arrays.toString(aArr));
	}
}
