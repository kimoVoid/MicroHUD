package me.kimovoid.microhud.util;

public class MathUtils {

    public static double averageInt(int[] arr) {
        long sum = 0L;
        for (int value : arr) {
            sum += value;
        }
        return (double) sum / (double) arr.length;
    }
}
