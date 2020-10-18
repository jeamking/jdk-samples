package com.demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        Class<Person> personClass = Person.class;
        Method somebodyMethod = personClass.getMethod("somebody", new Class[]{String.class, int.class});
        somebodyMethod.invoke(person, new Object[]{"lily", 18});
        iteratorAnnotations(somebodyMethod);

        Method emptyMethod = personClass.getMethod("empty", new Class[]{});
        emptyMethod.invoke(person, new Object[]{});
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
