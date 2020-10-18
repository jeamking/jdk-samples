package com.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类管理器
 */
public class ProxyManager implements InvocationHandler {// 动态代理的核心处理器接口
	private Object object;

	public ProxyManager(Object object) {
		this.object = object;
	}

	public Object createNewInstance() {
		Object o = Proxy.newProxyInstance(object.getClass().getClassLoader(),// 真实对象的类加载器
				object.getClass().getInterfaces(),// 真实对象的所有接口
				this);// 代理对象
		return o;
	}

	@Override
	// 代理对象 执行业务的方法 参数
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("before......权限检测");// 前置通知
		Object invoke = method.invoke(object, args);// 动态调用执行方法
		System.out.println("after......日志监控");// 后置通知
		return invoke;
	}
}