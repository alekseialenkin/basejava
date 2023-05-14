package com.javaops.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {
    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));
        return map.get(map.get(false).size() % 2 == 0);
    }

    public static void main(String[] args) {
        System.out.println(oddOrEven(List.of(1, 1, 1, 2, 2)));
        System.out.println(oddOrEven(List.of(1, 1, 1, 2, 2, 1)));
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
    }
}
