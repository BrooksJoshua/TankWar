package com.lee.tank.designPatterns.Strategy;


public class DogComparator2 implements Comparator<Dog> {
    @Override
    public int compare(Dog dogA, Dog dogB) {
        if (dogA.height < dogB.height) {
            return -1;
        } else if (dogA.height > dogB.height) {
            return 1;
        } else {
            return 0;
        }
    }
}
