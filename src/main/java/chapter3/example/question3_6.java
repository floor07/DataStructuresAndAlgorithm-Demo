package chapter3.example;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class question3_6 {
	/**
	 * Josephus problem
	 * @param m 第m个要移除的数字
	 * @param n 初始个数
	 */
	public static void pass(int m, int n){
		//初始化
		List<Integer> nums=new ArrayList<Integer>();
		for(int i=0;i<n;i++){
			nums.add(i);
		}
		//
		int numPrime,numLeft=n;
		Integer removeItem=0;
		ListIterator<Integer>iter=nums.listIterator();
		while(numLeft!=1){
			numPrime=m%numLeft;	
			if(numPrime<numLeft/2){
				//避免余数为0的情况
				if (iter.hasNext()){
					removeItem=iter.next();	
				}
				
				for(int i=0;i<numPrime;i++){
					if(!iter.hasNext()){
						iter=nums.listIterator();
					}
					removeItem=iter.next();
				}
				
			}else{
				//避免余数为0的情况
				for(int i=0;i<numLeft-numPrime;i++){
					if(!iter.hasPrevious()){
						iter=nums.listIterator(nums.size());
					}
					removeItem=iter.previous();
				}
			}
			System.out.println("remove:"+removeItem);
			iter.remove();
			//如果为最后节点，需要移动到最前，放在next没有，避免余数为0的情况
			if(!iter.hasNext()){
				iter=nums.listIterator();
			}
			numLeft--;
			System.out.println("left:"+nums);
		}
		
	}
	public static void main(String[] args) {
//		pass(0,5);
//		pass(1,5);
		pass(4,5);
	}
}
