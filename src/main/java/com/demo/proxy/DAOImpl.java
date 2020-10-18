package com.demo.proxy;

/**
 * DAO的实现类
 */
public class DAOImpl implements DAO {
  @Override
  public void add() {
    System.out.println("添加的方法");
  }
  @Override
  public void update() {
    System.out.println("更新的方法");
  }
  @Override
  public void delete() {
    System.out.println("删除的方法");
  }
  @Override
  public void query() {
    System.out.println("查询的方法");
  }
}
