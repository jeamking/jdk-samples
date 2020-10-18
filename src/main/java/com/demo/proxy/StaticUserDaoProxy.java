package com.demo.proxy;

/**
 * ������
 *
 */
public class StaticUserDaoProxy implements StaticDao{
  StaticUserDao userDao = null;
  public StaticUserDaoProxy(StaticUserDao userDao){
    this.userDao = userDao;
  }
  @Override
  public void add() {
    userDao.add();
    System.out.println("��¼��־add");
  }
  @Override
  public void delete() {
    userDao.delete();
    System.out.println("��¼��־delete");
  }
  @Override
  public void update() {
    userDao.update();
    System.out.println("��¼��־update");
  }
  @Override
  public void query() {
    userDao.query();
    System.out.println("��¼��־query");
  }
}
