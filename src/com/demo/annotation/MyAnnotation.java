package com.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	/**
	 * �������ԣ������в���
	 * @return
	 */
	String[] value0() default "unknown";
}
