package com.lee.tank.designPatterns.Strategy;


public class DogComparator1 implements Comparator<Dog> {
    @Override
    public int compare(Dog dogA, Dog dogB) {
        if (dogA.weight < dogB.weight) {
            return -1;
        } else if (dogA.weight > dogB.weight) {
            return 1;
        } else {
            return 0;
        }
    }
}
