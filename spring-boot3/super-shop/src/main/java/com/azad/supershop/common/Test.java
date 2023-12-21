package com.azad.supershop.common;

public class Test {

    public enum TestEnum {
        CASH, DUE;
    }

    public static void main(String[] args) {

        System.out.println(TestEnum.CASH.name());
    }
}
