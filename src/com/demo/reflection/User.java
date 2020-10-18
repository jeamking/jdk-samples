package com.demo.reflection;

public class User {
	//---------------���췽��-------------------
	//��Ĭ�ϵĹ��췽����
	User(String str){
		System.out.println("(Ĭ��)�Ĺ��췽�� s = " + str);
	}
	//�޲ι��췽��
	public User(){
		System.out.println("�����˹��С��޲ι��췽��ִ���ˡ�����");
	}
	//��һ�������Ĺ��췽��
	public User(char name){
		System.out.println("������1���������췽����������" + name);
	}
	//�ж�������Ĺ��췽��
	public User(String name ,int id){
		this.name = name;
		this.id = id;
		System.out.println("������2���������췽����������"+name+"id��"+ id);//���ִ��Ч�������⣬�Ժ�����
	}
	//�ܱ����Ĺ��췽��
	protected User(boolean n){
		System.out.println("�ܱ����Ĺ��췽�� n = " + n);
	}
	//˽�й��췽��
	private User(int age){
		System.out.println("˽�еĹ��췽�� ���䣺"+ age);
	}
	
	
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
