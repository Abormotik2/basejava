package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = new int[]{7, 7, 5, 8, 9, 4, 5, 8, 9};
        System.out.println(minValue(values));
        System.out.println(" ");

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(43);
        list.add(24);
        list.add(99);
        list.add(55);
        list.add(122);

        System.out.println(oddOrEven(list));

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((left, right) -> left * 10 + right)
                .orElse(-1);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(integer -> (integers.stream().mapToInt(Integer::intValue).sum()) % 2 != integer % 2)
                .collect(Collectors.toList());
    }
}