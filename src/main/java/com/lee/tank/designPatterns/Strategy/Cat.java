package com.lee.tank.designPatterns.Strategy;

public class Cat implements Comparable<Cat>{
    String name;
    int age;


    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Cat c){
        if(this.age < c.age) {
            return -1;
        }else if(this.age > c.age) {
            return 1;
        }else {
            return 0;
        }

    }


}
