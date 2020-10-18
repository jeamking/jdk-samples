package com.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * �����������
 */
public class ProxyManager implements InvocationHandler {// ��̬����ĺ��Ĵ������ӿ�
	private Object object;

	public ProxyManager(Object object) {
		this.object = object;
	}

	public Object createNewInstance() {
		Object o = Proxy.newProxyInstance(object.getClass().getClassLoader(),// ��ʵ������������
				object.getClass().getInterfaces(),// ��ʵ��������нӿ�
				this);// �������
		return o;
	}

	@Override
	// ������� ִ��ҵ��ķ��� ����
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before......Ȩ�޼��");// ǰ��֪ͨ
		Object invoke = method.invoke(object, args);// ��̬����ִ�з���
		System.out.println("after......��־���");// ����֪ͨ
		return invoke;
	}
}