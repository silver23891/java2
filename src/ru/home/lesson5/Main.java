package ru.home.lesson5;

import java.util.Arrays;

public class Main {
    private static final int SIZE = 10000000;
    private static final int H = SIZE / 2;

    public static void main(String[] args) {
        float[] arrForFirst = new float[SIZE];
        float[] arrForSecond = new float[SIZE];
        Arrays.fill(arrForFirst, 1);
        Arrays.fill(arrForSecond, 1);
        System.out.printf("Время работы с массивом в одном потоке: %d мс\n", oneThread(arrForFirst));
        try {
            System.out.printf("Время работы с массивом в двух потоках: %d мс\n", twoThreads(arrForSecond));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < SIZE; i++) {
            if (arrForFirst[i] != arrForSecond[i]) {
                System.out.println("Массивы не равны");
                break;
            }
        }

    }

    private static long oneThread(float[] arr1) {
        long beginTime = System.currentTimeMillis();
        changeArrayValues(arr1);
        return System.currentTimeMillis() - beginTime;
    }

    private static long twoThreads(float[] arr) throws Exception {
        float[] arr1 = new float[H];
        float[] arr2 = new float[H];
        long beginTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, H);
        System.arraycopy(arr, H, arr2, 0, H);
        MeasurementThread t1 = new MeasurementThread(arr1, 0);
        MeasurementThread t2 = new MeasurementThread(arr2, H);
        t1.join();
        t2.join();
        System.arraycopy(t1.arr, 0, arr, 0, H);
        System.arraycopy(t2.arr, 0, arr, H, H);
        return System.currentTimeMillis() - beginTime;
    }

    private static void changeArrayValues(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
