package com.yumaolin.deepunderstand.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {
	
	//public final static String springFilePath = "classpath*:spring/applicationContext.xml";
    //private final static ApplicationContext context =new FileSystemXmlApplicationContext(new String[]{springFilePath});
	//private final static GenericXmlApplicationContext applicationContext =getGenericXmlApplicationContext();
    //private final static DefaultListableBeanFactory dbr = getDefaultListableBeanFactory();
    //private final static BeanFactory beanFactory = new ClassPathXmlApplicationContext(springFilePath);
	public static ApplicationContext context = null;
    
    public static void main(String[] args) {
		String springFilePath = "classpath*:spring/applicationContext.xml";
		context = new ClassPathXmlApplicationContext(springFilePath);
    	TestBean testBean = (TestBean) context.getBean("testBean");
    	testBean.test();
	}
}
