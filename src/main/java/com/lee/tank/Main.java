package com.lee.tank;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        TankFrame tankFrame = new TankFrame();
        int initialInvadorCount = (int) PropertyManager.get("initialInvadorCount_int");
        for(int i=0;i<initialInvadorCount;i++){
           tankFrame.invaders.add(new Tank(100+(TankFrame.GAME_WIDTH-100)/initialInvadorCount*i,150,Dir.DOWN,tankFrame,Group.BAD));
       }
        while (true) {
            Thread.sleep(500);
            tankFrame.repaint();
        }


    }
}
