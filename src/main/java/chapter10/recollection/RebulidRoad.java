package chapter10.recollection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 回溯算法解 高速公路问题 设给定N个点P1，P2，.......，PN，它们位于X轴上。Xi是Pi点的X坐标。<br/>
 * 进一步假设X1=0以及这些点从左到右给出。着N个点确定，在每一对，点间的N（N-1）/2个，<br/>
 * 形如|Xi - Xj|（i !=j）的距离。收费公路重建问题就是由这些距离重构一个点集。<br/>
 * 
 * @author wenl
 */
public class RebulidRoad {

	/**
	 * @param x
	 *            结果点集合
	 * @param distSet
	 *            边的优先队列，注意队列从大到小
	 * @param n
	 *            根据边集大小计算出来的 预计点个数， n(n-1)/2=distSet.size();
	 * @return
	 */
	public static boolean turnpike(int[] x, PriorityQueue<Integer> distSet, int n) {
		// 倒序排列边集合
		x[1] = 0;
		x[n] = distSet.poll();
		x[n - 1] = distSet.poll();
		if (distSet.contains(x[n] - x[n - 1])) {
			distSet.remove(new Integer(x[n] - x[n - 1]));
			return place(x, distSet, n, 2, n - 2);
		} else {
			return false;
		}
	}

	/**
	 * 重建高速公路-回溯算法核心
	 * @param x  结果集合
	 * @param distSet 剩余边集合 从大到小构建的堆
	 * @param n       预计包含的点集合
	 * @param left    本次尝试的左边界
	 * @param right   本次尝试的右边开始点
	 */
	private static boolean place(int[] x, PriorityQueue<Integer> distSet, int n, int left, int right) {
		int dmax = 0;
		boolean found = false;
		if (distSet.isEmpty()) {
			return true;
		}
		dmax = distSet.peek();
		// 尝试x[right]为 dmax
		if (CheckIfRight(x, distSet, n, left, right, dmax)) {
			x[right] = dmax;
			for (int j = 1; j < left; j++) {
				distSet.remove(new Integer(Math.abs(x[j] - x[right])));
			}
			for (int j = right + 1; j <= n; j++) {
				distSet.remove(new Integer(Math.abs(x[j] - x[right])));
			}
			found = place(x, distSet, n, left, right - 1);
			if (!found) {
				for (int j = 1; j < left; j++) {
					distSet.add(new Integer(Math.abs(x[j] - x[right])));
				}
				for (int j = right + 1; j <= n; j++) {
					distSet.add(new Integer(Math.abs(x[j] - x[right])));
				}
			}
		}
		// x[left]=x[n]-dmax
		if (!found && CheckIfleft(x, distSet, n, left, right, dmax)) {
			x[left] = x[n] - dmax;
			for (int j = 1; j < left; j++) {
				distSet.remove(new Integer(Math.abs(x[n] - dmax - x[j])));
			}
			for (int j = right + 1; j <= n; j++) {
				distSet.remove(new Integer(Math.abs(x[n] - dmax - x[j])));
			}
			found = place(x, distSet, n, left + 1, right);
			if (!found) {
				for (int j = 1; j < left; j++) {
					distSet.add(new Integer(Math.abs(x[n] - dmax - x[j])));
				}
				for (int j = right + 1; j <= n; j++) {
					distSet.add(new Integer(Math.abs(x[n] - dmax - x[j])));
				}
			}
		}
		return found;
	}

	private static boolean CheckIfleft(int[] x, PriorityQueue<Integer> distSet, int n, int left, int right, int dmax) {
		boolean flagL = true;
		for (int j = 1; j < left; j++) {
			flagL=flagL&&distSet.contains(Math.abs(x[n] - x[j] - dmax));
			if(!flagL){
				break;
			}
		}
		if (flagL) {
			for (int j = 1; j < left; j++) {
				distSet.remove(new Integer(Math.abs(x[n] - dmax - x[j])));
			}
			for (int j = right + 1; j <= n; j++) {
				flagL=flagL&&distSet.contains(Math.abs(x[n] - x[j] - dmax));
				if(!flagL){
					break;
				}
			}
			for (int j = 1; j < left; j++) {
				distSet.add(new Integer(Math.abs(x[n] - dmax - x[j])));
			}
		}
		return flagL;
	}

	private static boolean CheckIfRight(int[] x, PriorityQueue<Integer> distSet, int n, int left, int right, int dmax) {
		boolean flagR = true;
		for (int j = 1; j < left; j++) {
			flagR=flagR&&distSet.contains(Math.abs(x[j] - dmax));
			if(!flagR){
				break;
			}
		}
		if (flagR) {
			for (int j = 1; j < left; j++) {
				distSet.remove(new Integer(Math.abs(x[j] - dmax)));
			}
			for (int j = right + 1; j <= n; j++) {
				flagR=flagR&&distSet.contains(Math.abs(x[j] - dmax));
				if(!flagR){
					break;
				}
			}
			for (int j = 1; j < left; j++) {
				distSet.add(new Integer(Math.abs(x[j] - dmax)));
			}
		}
		
		return flagR;
	}

	public static void main(String[] args) {
		int[] x = new int[7];
		PriorityQueue<Integer> distSet = new PriorityQueue<Integer>(15, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		distSet.add(1);
		distSet.add(2);
		distSet.add(2);
		distSet.add(2);
		distSet.add(3);
		distSet.add(3);
		distSet.add(3);
		distSet.add(4);
		distSet.add(5);
		distSet.add(5);
		distSet.add(5);
		distSet.add(6);
		distSet.add(7);
		distSet.add(8);
		distSet.add(10);
		System.out.println(distSet);
		boolean isTrue = turnpike(x, distSet, 6);

		System.out.println(isTrue);
		System.out.println(Arrays.toString(x));
		System.out.println(distSet);

	}
}
