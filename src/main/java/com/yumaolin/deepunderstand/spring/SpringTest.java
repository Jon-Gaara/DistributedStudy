package com.yumaolin.deepunderstand.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {
	
	private final static String springFilePath = "classpath*:spring/applicationContext.xml";
    //private final static ApplicationContext context =new FileSystemXmlApplicationContext(new String[]{springFilePath});
	//private final static GenericXmlApplicationContext applicationContext =getGenericXmlApplicationContext();
    //private final static DefaultListableBeanFactory dbr = getDefaultListableBeanFactory();
    //private final static BeanFactory beanFactory = new ClassPathXmlApplicationContext(springFilePath);
	private final static ApplicationContext context = new ClassPathXmlApplicationContext(springFilePath);
    
    public static void main(String[] args) {
    	TestBean testBean = (TestBean) context.getBean("testBean");
    	testBean.test();
	}
}
