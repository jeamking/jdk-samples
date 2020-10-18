package com.demo.proxy;

/**
 * 被代理类
 */
public class User {
  public void saveUser(){
    System.out.println("保存对象。");
  }
  public void updateUser(){
    System.out.println("修改对象。");
  }
}
