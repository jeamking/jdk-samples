package com.demo.annotation;

public class Person {
    /**
     * empty()����ͬʱ�� "@Deprecated" �� ��@MyAnnotation(value={"a","b"})������ע 
     * (01) @Deprecated����ζ��empty()���������ٱ�����ʹ��
     * (02) @MyAnnotation, ��ζ��empty() ������Ӧ��MyAnnotation��valueֵ��Ĭ��ֵ"unknown"
     */
    @MyAnnotation
    @Deprecated
    public void empty(){
        System.out.println("invoke empty method");
    }
    
    /**
     * sombody() �� @MyAnnotation(value={"girl","boy"}) ����ע��
     * @MyAnnotation(value={"girl","boy"}), ��ζ��MyAnnotation��valueֵ��{"girl","boy"}
     */
    @MyAnnotation(value0={"girl","boy"})
    public void somebody(String name, int age){
        System.out.println("invoke somebody method: "+name+", "+age);
    }
}
