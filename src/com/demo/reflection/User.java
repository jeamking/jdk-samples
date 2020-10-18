package com.demo.reflection;

public class User {
	//---------------构造方法-------------------
	//（默认的构造方法）
	User(String str){
		System.out.println("(默认)的构造方法 s = " + str);
	}
	//无参构造方法
	public User(){
		System.out.println("调用了公有、无参构造方法执行了。。。");
	}
	//有一个参数的构造方法
	public User(char name){
		System.out.println("调用了1个参数构造方法，姓名：" + name);
	}
	//有多个参数的构造方法
	public User(String name ,int id){
		this.name = name;
		this.id = id;
		System.out.println("调用了2个参数构造方法，姓名："+name+"id："+ id);//这的执行效率有问题，以后解决。
	}
	//受保护的构造方法
	protected User(boolean n){
		System.out.println("受保护的构造方法 n = " + n);
	}
	//私有构造方法
	private User(int age){
		System.out.println("私有的构造方法 年龄："+ age);
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
