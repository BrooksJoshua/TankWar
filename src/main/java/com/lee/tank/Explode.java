package com.lee.tank;

import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.*;

public class Explode {
    private int x,y;
    private static final int WIDTH=ResourceMgr.explodes[0].getWidth();
    private static final int HEIGHT=ResourceMgr.explodes[0].getHeight();
    private TankFrame tankFrame=null;

    //private boolean living = true;

    private int step = 0;

    public Explode(int x, int y,TankFrame tankFrame) {
        super();
        this.x = x;
        this.y = y;
        this.tankFrame=tankFrame;
        new Thread(()->new Audio("audio/explode.wav").play()).start();



    }


    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step++], x, y, null);

        if(step >= ResourceMgr.explodes.length) {
           tankFrame.explodes.remove(this); //每次paint完都要把遍历的List<Explode>的当前的这个元素explode去除掉.
        }


    }
}
