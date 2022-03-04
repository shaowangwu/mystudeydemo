package com.atguigu.jvmdemo;

public class JVVMNote {

    public static void m1() {

        m1();
    }

    public static void main(String[] args) {

        System.out.println("11111111");
        m1();
        System.out.println("33333");
    }
}
