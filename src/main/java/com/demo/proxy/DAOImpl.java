package com.demo.proxy;

/**
 * DAO��ʵ����
 */
public class DAOImpl implements DAO {
  @Override
  public void add() {
    System.out.println("��ӵķ���");
  }
  @Override
  public void update() {
    System.out.println("���µķ���");
  }
  @Override
  public void delete() {
    System.out.println("ɾ���ķ���");
  }
  @Override
  public void query() {
    System.out.println("��ѯ�ķ���");
  }
}
