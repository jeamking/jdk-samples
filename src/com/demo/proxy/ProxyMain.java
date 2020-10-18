package com.demo.proxy;

public class ProxyMain {

	public static void main(String[] args) {
		//创建真实对象		
		DAO dao = new DAOImpl();
		ProxyManager manager = new ProxyManager(dao);
		DAO newDao =(DAO) manager.createNewInstance();
		//JDK动态代理是代理的接口，因此强制转换应该转换为接口，而不是实现类，若强制转换实现类就会抛出ClassCastException，好比ArrayList与LinkedList实现统一接口List，两者也不能相互转换，但都可以向上转型。
		newDao.add();
		newDao.query();
	}

}
