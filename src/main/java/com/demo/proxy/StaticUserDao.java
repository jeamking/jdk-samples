package com.demo.proxy;

/**
*��������
*/
public class StaticUserDao implements StaticDao{
  @Override
  public void add() {
    System.out.println("��Ŀ�������ִ��add");
  }
  @Override
  public void delete() {
    System.out.println("��Ŀ�������ִ��delete");
  }
  @Override
  public void update() {
    System.out.println("��Ŀ�������ִ��update");
  }
  @Override
  public void query() {
    System.out.println("��Ŀ�������ִ��query");
  }
}
