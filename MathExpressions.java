package application;

import java.util.Stack;

public class MathExpressions {
	
	
	private static final String divideByZero="can't divide by zero...";
	
	public static String evaluate(String exp) {
		Stack<Double> operands = new Stack();
		Stack<Character> operators = new Stack();
		
		for(int i=0; i<exp.length();i++) {
			char ch=exp.charAt(i);
			
			if(Character.isDigit(ch)) {
				String value="";
				while(i<exp.length()) {
					if(Character.isDigit(exp.charAt(i)) || exp.charAt(i)=='.') {
						value+=exp.charAt(i++);
					}
					else {
						break;
					}
				}
				i--;
				operands.push(Double.parseDouble(value));
			}
			else if(ch=='(') {
				operators.push(ch);
			}
			else if(ch=='+' || ch=='-' || ch=='*' || ch=='/' || ch=='^') {
				
				//if operators comes then check
				
				if(operators.isEmpty()) {
					operators.push(ch);
				}
				else if(operators.peek()=='(') {
					operators.push(ch);
				}
				else if(getPrecedence(operators.peek())>=getPrecedence(ch)) {
					char op=operators.pop();
					double num2=operands.pop();
					double num1=operands.pop();
					Object value=calculate(num1, num2, op);
					if(value instanceof String)
						return divideByZero;
					operands.push((double)value);
					operators.push(ch);
				}
				else {
					operators.push(ch);
				}
				//end of operators checking
			}
			else if(ch==')') {
				while(operators.peek()!='(') {
					char op=operators.pop();
					double num2=operands.pop();
					double num1=operands.pop();
					Object value=calculate(num1, num2, op);
					if(value instanceof String)
						return divideByZero;
					operands.push((double)value);
				}
				operators.pop();
			}
		}
		// end of loop
		
		while(operators.size()!=0) {
			char op=operators.pop();
			double num2=operands.pop();
			double num1=operands.pop();
			Object value=calculate(num1, num2, op);
			if(value instanceof String)
				return divideByZero;
			operands.push((double)value);
		}
		
		
		return operands.pop().toString();
	}
	
	
	private static int getPrecedence(char ch) {
		
		if(ch=='+' || ch=='-')
			return 1;
		else if(ch=='*' || ch=='/')
			return 2;
		else if(ch=='^')
			return 3;
		else if(ch=='(' || ch==')')
			return 4;
		else
			return 0;
	}
	
	
	
	private static Object calculate(double num1, double num2, char operator) {
		switch(operator) {
		
		
		case '+': return num1+num2;
		case '-': return num1-num2;
		case '*': return num1*num2;
		case '/': 
			if(num2==0)
				return divideByZero;  //Object return object so type will be Object also.....
			else
				return num1/num2;
		case '^': return Math.pow(num1, num2);
		
		default:return 0;
		}
	}
	
	
	

}
