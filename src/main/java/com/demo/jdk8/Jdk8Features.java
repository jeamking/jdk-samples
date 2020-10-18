package com.demo.jdk8;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * jdk8������
 * @author Administrator
 *
 */
public class Jdk8Features {
	/**
	 * 1.Lambda���ʽ
	 */
	@Test
	public void testLambda() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		list.forEach(System.out::println);
		list.forEach(e -> System.out.println("��ʽ����" + e));
	}
	
	/**
	 * 2.Stream����ʽ������Ԫ�ؼ���
	 */
	@Test
	public void testStream() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		list.add(2, null);
		list.add(6, null);
		System.out.println("��ͣ�" + list.stream()// ת��Stream
				.filter(team -> team != null)// ����
				.distinct()// ȥ��
				.mapToInt(num -> num * 2)// map����
				.skip(2)// ����ǰ2��Ԫ��
				.limit(4)// ����ȡǰ4��Ԫ��
				.peek(System.out::println)// ��ʽ���������
				.sum());//
	}
}
