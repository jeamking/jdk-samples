package com.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	/**
	 * 定义属性，不能有参数
	 * @return
	 */
	String[] value0() default "unknown";
}
