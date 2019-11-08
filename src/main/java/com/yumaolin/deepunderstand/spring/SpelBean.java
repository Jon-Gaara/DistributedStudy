package com.yumaolin.deepunderstand.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/** 
 * SpEL运算符使用
 * @author yuml
 * @since 2019年9月23日
 */
public class SpelBean {

	@Value("#{10+2*3/4-2}")
	public int algDemoValue;
	@Value("#{'Hello'+'World!'}")
	public String stringConcatValue;
	@Value("#{T(java.lang.Math).random()}")
	public int randomInt;
	
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("foo","bar");
		String foo = parser.parseExpression("#foo").getValue(context,String.class);
		System.out.println(foo);
		context = new StandardEvaluationContext("I am root");
		String root = parser.parseExpression("#root").getValue(context,String.class);
		System.out.println(root);
		String result3 = parser.parseExpression("#this").getValue(context,String.class);
		System.out.println(result3);
		//获取spring bean
		ApplicationContext appContext = SpringTest.context;
		SpelBean spelBean = (SpelBean) appContext.getBean("spelBean");
		System.out.println(spelBean.algDemoValue);
		System.out.println(spelBean.stringConcatValue);
		System.out.println(spelBean.randomInt);
	}

}
