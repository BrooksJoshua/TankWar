package com.lee.tank;

public class DefaultFireStrategy implements FireStrategy{


    @Override
    public void fire(Tank t) {
        int bx = t.getX() + Tank.getWIDTH() / 2 - Bullet.getWIDTH() / 2;
        int by = t.getY() + Tank.getHEIGHT() / 2 - Bullet.getHEIGHT() / 2;
        t.getTankFrame().bullets.add(new Bullet(bx, by, t.getDir(), t.getTankFrame(), t.getGroup()));

        if(t.getGroup()==Group.GOOD){
            new Thread(()->new Audio("audio/explode.wav").play()).start();
        }
    }
}
