package chapter10.dynamicprogramming;

public class Fibonacci {
	/**
	 * 递归实现违反合成效益法则
	 * */
	public static int fib(int n){
		if(n<=1){
			return 1;
		}else{
			return fib(n-1)+fib(n-2);
		}
	}
	/**
	 * 动态规划版本，保证没有多余的计算，
	 * 以last 保存f(i-1)的值，nextToLast保存f(i-2)
	 * answer 保存f(i)的值
	 * */
	public static int fibonacci(int n){
		if(n<=1){
			return 1;
		}
		int last=1;
		int nextToLast=1;
		int answer=1;
		for(int i=2;i<=n;i++){
			answer=last+nextToLast;
			nextToLast=last;
			last=answer;
		}
		return answer;
	}
	public static void main(String[] args) {
		System.out.println(fib(6));//13
		System.out.println(fibonacci(6));//13
	}
}
