package com.urise.webapp.main;

public class MainUtil {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(-1) == Integer.valueOf(-1));
        System.out.println(Integer.valueOf(-1) == new Integer(-1));
        int result = getInt();
        System.out.println(result);
        Integer a1 = 50;
        Integer a2 = 50;
        Integer b1 = 500;
        Integer b2 = 500;
        System.out.println(a1==a2);
        System.out.println(b1==b2);
    }
    private static Integer getInt() {
        return -1;
    }
}
