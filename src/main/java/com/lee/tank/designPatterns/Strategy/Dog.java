package com.lee.tank.designPatterns.Strategy;

public class Dog implements Comparable<Dog>{
    int height ;
    int weight ;

    public Dog(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }



    @Override
    public String toString() {
        return "Dog{" +
                "height=" + height +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Dog d) {
        if(this.weight<d.weight){
            return -1;
        }else if(this.weight>d.weight){
            return 1;
        }else{
            return 0;
        }
    }


}
