package com.javaops.webapp;

import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(integers.stream()
                        .filter(n -> n % 2 != 0)
                        .count() % 2 == 0 ? a -> a % 2 == 0 : a -> a % 2 != 0)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(oddOrEven(List.of(1, 1, 1, 2, 2)));
        System.out.println(oddOrEven(List.of(1, 1, 1, 2, 2, 1)));
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
    }
}
