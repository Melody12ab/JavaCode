package com.day01.basic;

import java.util.Arrays;

public class Demo01 {
    public static void main(String[] args) {
        int [] a=new int[10];
        for(int i=0;i<10;i++){
            a[i]=i+1;
        }
        System.out.println(Arrays.binarySearch(a, 8));
    }
}
