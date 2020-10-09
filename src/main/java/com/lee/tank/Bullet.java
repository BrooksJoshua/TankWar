package com.lee.tank;

import java.awt.*;
import java.util.Random;

public class Bullet {
    private int x, y;
    private static final int WIDTH = ResourceMgr.bulletD.getWidth();
    private static final int HEIGHT = ResourceMgr.bulletD.getHeight();
    private static final int SPEED = (int) PropertyManager.get("bulletSpeed_int");
    private Dir dir;
    private boolean isAlive = true;
    private TankFrame tankFrame = null;
    private Rectangle bulletRectForCollisionCheck = new Rectangle();


    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void paint(Graphics g) {
        if (!isAlive) {
            tankFrame.bullets.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        march();
    }


    public void march() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
        //每次移动完, 更新矩形位置
        bulletRectForCollisionCheck.x=x;
        bulletRectForCollisionCheck.y=y;
        if (x < 0 || x > TankFrame.GAME_WIDTH || y < 0 || y > TankFrame.GAME_HEIGHT) {  // 当子弹越界后将存活标记设为false, 注意在TankFrame中,遍历子弹list集合时要留意concurrentModificationExeption
            setAlive(false);
        }
    }


    public Bullet(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        //在构造方法里面维护一个矩形用来做碰撞检测.(同样的方法在Tank里也有)
        bulletRectForCollisionCheck.x = this.x;
        bulletRectForCollisionCheck.y = this.y;
        bulletRectForCollisionCheck.width = Tank.getWIDTH();
        bulletRectForCollisionCheck.height = Tank.getHEIGHT();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void collideWithInvadersOrNot(Tank tank) {
        if (this.group.equals(tank.getGroup())) {  //碰撞检测的前提是子弹和坦克分属敌我双方. 否则不检测.
            return;    // 如果子弹和坦克是同一方, 就不走碰撞检测
        }
//        Rectangle bulletSpot = new Rectangle(this.x, this.y, getWIDTH(), getHEIGHT());
//        Rectangle invaderPatch = new Rectangle(tank.getX(), tank.getY(), Tank.getWIDTH(), Tank.getHEIGHT());
        //原来是每重画一次就要做一轮碰撞检测, 假设有N个坦克, M颗子弹,(对应检测的两层for循环), 那么就要进行N*M次检测,每次都要new两个rectangle,
        // 所以最终会产生(2N*M)个rectangle实例. 这样很影响效率,进而影响用户体验
        // 如何解决呢?
        //在Bullet和Tank内部都维护一个Rectangel, 每次实例化就初始化这个rectangle的位置和尺寸, 每次移动后就更新位置.
        //那么再做碰撞检测时就只用取出bullet和tank各自内部的rectangle做检测就好, 虽然检测次数还是N*M, 但是rectangle的
        //实例个数就变为了 (N+M), 比原来的(2N*M) 会少很多.
        if(bulletRectForCollisionCheck.intersects(tank.tankRectForCollisionCheck))  {
            suicide();
            tank.suicide();
            int ex = tank.getX() + Tank.getWIDTH() / 2 - Bullet.getWIDTH() / 2;
            int ey = tank.getY() + Tank.getWIDTH() / 2 - Bullet.getHEIGHT() / 2;
            tankFrame.explodes.add(new Explode(ex, ey, tankFrame));   // 每次碰撞都要往list<Expode>里加一个爆炸实例
        }


    }

    private void suicide() {
        setAlive(false);
    }
}
