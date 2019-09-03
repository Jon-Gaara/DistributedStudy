package com.yumaolin.deepunderstand.bytecode;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

/** 
 * Javasist 动态生成java类
 * @author yuml
 * @since 2019年8月28日
 */
public class JavasistDemo {
	public static void main(String[] args) throws NotFoundException, IOException {
		ClassPool classPool = ClassPool.getDefault();
		CtClass ctClass = classPool.makeClass("com.yumaolin.deepunderstand.bytecode.JavasistTestClass");
		try {
			//生成构造器
			CtConstructor constructor = CtNewConstructor.make("public JavasistTestClass(){};", ctClass);
			ctClass.addConstructor(constructor);
			//生成方法
			CtMethod ctMethod = new CtMethod(CtClass.voidType, "helloWorld",null, ctClass);
			ctMethod.setBody("{System.out.println(\"Hello world!\");}");
			ctClass.addMethod(ctMethod);	
			//生成属性
			CtField id = new CtField(CtClass.intType,"id", ctClass);
			id.setModifiers(AccessFlag.PUBLIC);
			ctClass.addField(id);
			//生成常量
			CtField name = new CtField(classPool.getCtClass("java.lang.String"),"name",ctClass);
			name.setModifiers(AccessFlag.PUBLIC|AccessFlag.STATIC|AccessFlag.FINAL);
			ctClass.addField(name,"\"0\"");
			//生成class
			ctClass.writeFile();
			//通过反射调用方法，查看结果
            Class<?> pc = ctClass.toClass();
			Field[] fields = pc.getFields();
            Method move= pc.getMethod("helloWorld",new Class<?>[]{});
            Constructor<?> con = pc.getConstructor(new Class<?>[]{});
            move.invoke(con.newInstance());
			System.out.println("属性名称：" + fields[0].getName() + "  属性类型：" + fields[0].getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
