package chapter3.example;

import java.util.Scanner;
import java.util.Stack;

/**
 * 中缀转后缀的Demo
 * @author Administrator
 */
public class InFixToPostFixDemo{
	
	public static void main(String[] args) {
		InFixToPostFix();
	}

	public static void InFixToPostFix() {
		// 中缀转后缀
		// 非符号直接输出，
		// 符号翻入栈，栈中如果优先级比当前的高弹出，否则压入
		// 最后将栈弹出。
		// 括号除外，遇到（压入，）弹出并输出直到（但是（不输出
		// 有优先级 )( ^ */ +-
		Stack<Character> stack = new Stack<Character>();
		Scanner sc = new Scanner(System.in);
		String token = sc.next();
		int i = 0;
		Character c = null;
		while ((c = token.charAt(i++)) != '=') {
			if (c >= 'a' && c <= 'z') {
				System.out.print(c);

			} else {
				switch (c) {
				case ')':
					while (!stack.isEmpty() && stack.peek() != '(') {
						System.out.print(stack.pop());
					}
					stack.pop();
					break;
				case '(':
					stack.push(c);
					break;
				case '^':
					while (!stack.isEmpty() && stack.peek() != '(' && stack.peek() != '^') {
						System.out.print(stack.pop());
					}
					stack.push(c);
					break;
				case '*':
				case '/':
					while (!stack.isEmpty() && stack.peek() != '-' && stack.peek() != '+' && stack.peek() != '(') {
						System.out.print(stack.pop());
					}
					stack.push(c);
					break;
				case '+':
				case '-':
					while (!stack.isEmpty() && stack.peek() != '(') {
						System.out.print(stack.pop());
					}
					stack.push(c);
					break;
				default:
					throw new RuntimeException("符号错误");
				}

			}
		}
		sc.close();
		if (!stack.isEmpty()) {
			while (!stack.isEmpty()) {
				System.out.print(stack.pop());
			}
		}
	}
}
