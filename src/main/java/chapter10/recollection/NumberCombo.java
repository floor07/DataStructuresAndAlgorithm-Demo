package chapter10.recollection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NumberCombo {



		private int n; // 被求组合数的自然数
		private int r; // 要求组合数的自然数个数
		private int[] a; // 解

		public NumberCombo(int _n, int _r) {
			n = _n;
			r = _r;
			a = new int[_r + 1]; //解是从1开始对应的。
		}


		public List<int[]> combOrderIndex() {

			List<int[]> selectIndexList = new LinkedList<int[]>();

			combOrder(selectIndexList);

			return selectIndexList;
		}

		/**
		 * 核心处理方法
		 * @param selectIndexList 存储结果
		 */
		private void combOrder(List<int[]> selectIndexList) {

			if(r==0){
				return ;
			}
			if(r==1){
			  for (int i = 0; i<n; i++) {
					selectIndexList.add(new int[] { i });
			   }
				return;
			}
			int resultIndex=1;
			 a[1]=1;
			while(a[1]<=(n-r+1)){//根据第一位进行裁剪，第一位取值为1<a[1]<n-r+1 列入5个数选3个 第一位最大为5-3+1=3
			   if(resultIndex<r){
				if(a[resultIndex]<=n-r+resultIndex){
					a[resultIndex+1] = a[resultIndex]+1;
					resultIndex++;
				}else{// 已经超过每一位的最大值 n-r+resultIndex,进行回溯
					resultIndex--;
					a[resultIndex]=a[resultIndex]+1;
				}
			  }else if(resultIndex==r){
				  int[] selectIndex = new int[r];
					selectIndexList.add(selectIndex);
					for (int j = 1; j <= r; j++) {//存储结果
						selectIndex[j - 1] = a[j]-1;
					}
					if (a[resultIndex]==n) { // 若当前搜索结果为1，表示本次搜素完成，进行回溯 此时条件相当为 ri+a[ri]==r+1 因为ri=r所以简化啦
						resultIndex--;
						a[resultIndex] = a[resultIndex]+1; // 回溯
					} else {
						a[resultIndex] = a[resultIndex]+1; // 搜索到下一个数
					}
			  }
			}
		}
		
		
		/**
		 * @param args
		 */
		public static void main(String[] args) {
			// 测试例子
			NumberCombo nc = new NumberCombo(5, 3);
			List<int[]> indexList2 = nc.combOrderIndex();
			for(int i=0;i<indexList2.size();i++){
				System.out.println(Arrays.toString(indexList2.get(i)));
			}
			System.out.println(indexList2.size()+"====================================="+indexList2.size());
		}
	}
