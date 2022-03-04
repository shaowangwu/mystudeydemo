package com.atguigu.jvmdemo;

public class MyObject {


    public static void main(String[] args) {
        Object object = new Object();
        System.out.println("object:"+object.getClass().getClassLoader());
        //System.out.println("object-->parent:"+object.getClass().getClassLoader().getParent());
        //System.out.println("object-->parent-->parent:"+object.getClass().getClassLoader().getParent().getParent());

        MyObject myObject = new MyObject();
        System.out.println("myObject的爷爷:"+myObject.getClass().getClassLoader().getParent().getParent());
        System.out.println("myObject的parent:"+myObject.getClass().getClassLoader().getParent());
        System.out.println("myObject:"+myObject.getClass().getClassLoader());
    }



}
