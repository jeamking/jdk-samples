package com.demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ��̬�����࣬���һ�ȡannotation������ͨ�����
 * @author Administrator
 *
 */
public class AnnotationTest2 {
    public static void main(String[] args) throws Exception {
    	Class<?> personClass = Class.forName("com.demo.annotation.Person");
    	Object object = personClass.newInstance();
        Method somebodyMethod = personClass.getMethod("somebody", new Class[]{String.class, int.class});
        somebodyMethod.invoke(object, new Object[]{"lily", 18});
        iteratorAnnotations(somebodyMethod);

        Method emptyMethod = personClass.getMethod("empty", new Class[]{});
        emptyMethod.invoke(object, new Object[]{});
        iteratorAnnotations(emptyMethod);
    }
    
    public static void iteratorAnnotations(Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // �ж� somebody() �����Ƿ����MyAnnotationע��
        if(method.isAnnotationPresent(MyAnnotation.class)){
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            String[] values = myAnnotation.value0();
            for (String value : values) {
            	System.out.println(MyAnnotation.class.getName() + " method values:" + value);     	
            }
        }
        
        // ��ȡ�����ϵ�����ע�⣬����ӡ����
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(method.getName() + " annotation:" + annotation);
        }
    }
}
