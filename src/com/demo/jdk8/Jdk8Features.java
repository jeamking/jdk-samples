package com.demo.jdk8;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * jdk8新特性
 * @author Administrator
 *
 */
public class Jdk8Features {
	/**
	 * 1.Lambda表达式
	 */
	@Test
	public void testLambda() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		list.forEach(System.out::println);
		list.forEach(e -> System.out.println("方式二：" + e));
	}
	
	/**
	 * 2.Stream函数式操作流元素集合
	 */
	@Test
	public void testStream() {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		list.add(2, null);
		list.add(6, null);
		System.out.println("求和：" + list.stream()// 转成Stream
				.filter(team -> team != null)// 过滤
				.distinct()// 去重
				.mapToInt(num -> num * 2)// map操作
				.skip(2)// 跳过前2个元素
				.limit(4)// 限制取前4个元素
				.peek(System.out::println)// 流式处理对象函数
				.sum());//
	}
}
