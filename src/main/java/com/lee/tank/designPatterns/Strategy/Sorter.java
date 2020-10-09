package com.lee.tank.designPatterns.Strategy;

public class Sorter<T> {

    public void sort(T[] arr, Comparator<T> ct) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minPos = i;

            for (int j = i + 1; j < arr.length; j++) {
                //minPos = arr[j].compareTo(arr[minPos])==-1 ? j : minPos;
                minPos = ct.compare(arr[j],arr[minPos])==-1 ? j : minPos;
            }
            swap(arr, i, minPos);
        }
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

