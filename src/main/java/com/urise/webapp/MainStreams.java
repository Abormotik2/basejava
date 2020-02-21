package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = new int[]{7, 7, 3, 8, 9, 4, 5, 8, 9};
        minValue(values);
        System.out.println(" ");

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(43);
        list.add(24);
        list.add(99);
        list.add(55);
        list.add(122);

        System.out.println(oddOrEven(list));

    }

    private static void minValue(int[] values) {
        IntStream.of(values).sorted().limit(3).distinct().forEach(System.out::println);

    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        integers
                .stream()
                .peek(integer -> {
                    if (integer % 2 == 0)
                        evens.add(integer);
                    else odds.add(integer);
                })
                .reduce(Integer::sum)
                .ifPresent(integer -> {
                    if (integer % 2 == 0) {
                        evens.clear();
                    } else odds.clear();
                });
        odds.addAll(evens);
        return odds;
    }
}
