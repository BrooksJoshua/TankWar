package com.lee.tank.designPatterns.Singleton;

public class Mgr04 {

    private static Mgr04 INSTANCE =null;
    private Mgr04(){}
    public static synchronized Mgr04 getInstance(){
        if(INSTANCE==null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE= new Mgr04();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Mgr04.getInstance().hashCode());
            }).start();
        }
    }

}

