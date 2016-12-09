package chapter3.example;

import java.util.Scanner;
import java.util.Stack;

/**
 * 利用栈计算后缀表达式
 * @author wenl
 */
public class EvalPostFixDemo {
	
	public static void main(String[] args) {
		System.out.println(evalPostFix());
	}
	
	public static double evalPostFix(){
		Stack<Double> stack=new Stack<Double>();
		
		Scanner sc = new Scanner(System.in);
		String token = sc.next();
		boolean isNumber=false;
		Double number=null;
		while (token.charAt(0) != '=')
		{
			try{
				number=Double.parseDouble(token);
				isNumber=true;
				
			}catch(NumberFormatException e){
				isNumber=false;
			}
			if(isNumber){
				stack.push(number);
			}else{
				if("+".equals(token)){
					Double b=stack.pop();
					Double a=stack.pop();
					stack.push(a+b);
				}else if("-".equals(token)){
					Double b=stack.pop();
					Double a=stack.pop();
					stack.push(a-b);
				}else if("*".equals(token)){
					Double b=stack.pop();
					Double a=stack.pop();
					stack.push(a*b);
				}else if("/".equals(token)){
					Double b=stack.pop();
					Double a=stack.pop();
					stack.push(a/b);
				}else if("^".equals(token)){
					Double b=stack.pop();
					Double a=stack.pop();
					stack.push(Math.pow(a, b));
				}
			}
			token=sc.next();
		}
		sc.close();
		return stack.pop();
	}
	
}
