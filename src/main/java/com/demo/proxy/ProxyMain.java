package com.demo.proxy;

public class ProxyMain {

	public static void main(String[] args) {
		//������ʵ����		
		DAO dao = new DAOImpl();
		ProxyManager manager = new ProxyManager(dao);
		DAO newDao =(DAO) manager.createNewInstance();
		//JDK��̬�����Ǵ���Ľӿڣ����ǿ��ת��Ӧ��ת��Ϊ�ӿڣ�������ʵ���࣬��ǿ��ת��ʵ����ͻ��׳�ClassCastException���ñ�ArrayList��LinkedListʵ��ͳһ�ӿ�List������Ҳ�����໥ת����������������ת�͡�
		newDao.add();
		newDao.query();
	}

}
