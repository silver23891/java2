package ru.home.lesson5;

public class MeasurementThread extends Thread {
    public float[] arr;
    public int deviation;

    public MeasurementThread(float[] arr, int deviation) {
        this.arr = arr;
        this.deviation = deviation;
        start();
    }

    @Override
    public void run() {
        changeArrayValues();
    }

    private void changeArrayValues() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (i+deviation) / 5) * Math.cos(0.2f + (i+deviation) / 5) * Math.cos(0.4f + (i+deviation) / 2));
        }
    }
}
