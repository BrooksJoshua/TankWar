package com.lee.tank.designPatterns.Singleton;

public class Mgr06 {
    private Mgr06() {
    }
    private static Mgr06 INSTANCE;

    public static Mgr06 getInstance() {
        if (INSTANCE == null) {
            synchronized (Mgr06.class) {
                if (INSTANCE == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    INSTANCE = new Mgr06();
                }
            }
        }
        return INSTANCE;

    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Mgr06.getInstance().hashCode());
            }).start();
        }
    }
}
