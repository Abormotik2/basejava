package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = new int[]{7, 7, 3, 8, 9, 4, 5, 8, 9};
        minValue(values);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(43);
        list.add(24);
        list.add(99);
        list.add(55);

    }

    private static void minValue(int[] values) {
        IntStream.of(values).sorted().limit(3).distinct().forEach(System.out::print);

    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        Stream.of().
    }
}
