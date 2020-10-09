package com.lee.tank.designPatterns.Singleton;

public class Mgr07 {
    private Mgr07() {
    }
    private static final class Mgr07Holder {
        private static final Mgr07 INSTANCE = new Mgr07();
    }


    public Mgr07 getInstance(){
        return Mgr07Holder.INSTANCE;
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->
                    System.out.println(Mgr03.getInstance().hashCode())
            ).start();
        }
    }


}






