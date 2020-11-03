package com.demo.serviceloader;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderDemo {

	public static void main(String[] args) {
		ServiceLoader<IMan> serviceLoader = ServiceLoader.load(IMan.class);
        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
        	IMan item = (IMan)iterator.next();
            System.out.println(item.getClass().getName() +  ": " +  item.hashCode());
        }
        ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
        System.out.println("Thread.currentThread().getContextClassLoader=" + cl1.toString());
        System.out.println(ServiceLoaderDemo.class.getClassLoader().toString());
        System.out.println(ServiceLoaderDemo.class.getClassLoader().getParent().toString());
        System.out.println(ServiceLoaderDemo.class.getClassLoader().getParent().getParent().toString());
	}

}
