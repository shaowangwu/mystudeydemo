package com.atguigu.jvmdemo;

public class GetCPU {

    public static void main(String[] args) {
        int cpu=Runtime.getRuntime().availableProcessors();
        System.out.println(cpu);
    }
}
