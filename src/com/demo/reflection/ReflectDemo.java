package com.demo.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectDemo {

	/*
	 * ͨ��Class������Ի�ȡĳ�����еģ����췽������Ա��������Ա�����������ʳ�Ա��
	 * 
	 * 1.��ȡ���췽����
	 * 		1).�����ķ�����
	 * 			public Constructor[] getConstructors()������"���е�"���췽��
	            public Constructor[] getDeclaredConstructors()����ȡ���еĹ��췽��(����˽�С��ܱ�����Ĭ�ϡ�����)
	 * 		2).��ȡ�����ķ����������ã�
	 * 			public Constructor getConstructor(Class... parameterTypes):��ȡ������"���е�"���췽����
	 * 			public Constructor getDeclaredConstructor(Class... parameterTypes):��ȡ"ĳ�����췽��"������˽�еģ����ܱ�����Ĭ�ϡ����У� 		
	 * 		3).���ù��췽����
	 * 			Constructor-->newInstance(Object... initargs)
	 */
	public static void main(String[] args) throws Exception {
		//1.����Class����
		Class clazz = Class.forName("com.demo.reflection.User");
		
		//2.��ȡ���й��й��췽��
		System.out.println("**********************���й��й��췽��*********************************");
		Constructor[] conArray = clazz.getConstructors();
		for(Constructor c : conArray){
			System.out.println(c);
		}
		
		System.out.println("************���еĹ��췽��(������˽�С��ܱ�����Ĭ�ϡ�����)***************");
		conArray = clazz.getDeclaredConstructors();
		for(Constructor c : conArray){
			System.out.println(c);
		}
		
		System.out.println("*****************��ȡ���С��޲εĹ��췽��*******************************");
		Constructor con = clazz.getConstructor(null);
		//1>����Ϊ���޲εĹ��췽������������һ��null,��дҲ���ԣ�������Ҫ����һ�����������ͣ��м�������
		//2>�����ص�����������޲ι��캯���������
		System.out.println("con = " + con);
		
		System.out.println("*****************��ȡ���С��в����Ĺ��췽��*******************************");
		Constructor con2 = clazz.getConstructor(String.class, int.class);
		//1>����Ϊ���޲εĹ��췽������������һ��null,��дҲ���ԣ�������Ҫ����һ�����������ͣ��м�������
		//2>�����ص�����������޲ι��캯���������
		System.out.println("con2 = " + con2);	
		Object user2Object = con2.newInstance("����", 1);
		User user2 = (User) user2Object;
		System.out.println("ͨ��ʵ������ķ�����ȡ��" + user2.getName());
		Method method = clazz.getMethod("setName", String.class);
		method.invoke(user2Object, "���»�");
		System.out.println("ͨ��ʵ������ķ�����ȡ��" + user2.getName());
		
 
		//���ù��췽��
		Object obj = con.newInstance();
	//	System.out.println("obj = " + obj);
	//	Student stu = (Student)obj;
		
		System.out.println("******************��ȡ˽�й��췽����������*******************************");
		con = clazz.getDeclaredConstructor(char.class);
		System.out.println(con);
		//���ù��췽��
		con.setAccessible(true);//��������(���Ե��������η�)
		obj = con.newInstance('��');
	}

}
