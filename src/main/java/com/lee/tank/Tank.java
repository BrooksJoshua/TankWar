package com.lee.tank;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Tank {
    private int x;
    private int y;
    private boolean isAlive = true;
    private static final int HEIGHT = ResourceMgr.goodTankD.getHeight();
    private boolean moving = true;
    private static final int SPEED = (int) PropertyManager.get("tankSpeed_int");
    private Random random = new Random();
    private TankFrame tankFrame;  //为了明确在哪个窗口new Tnak(...)
    Rectangle tankRectForCollisionCheck=new Rectangle();

    FireStrategy fs;
    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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

    private Dir dir;
    private Group group;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private static final int WIDTH = ResourceMgr.goodTankD.getWidth();

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;
        //在构造方法里面维护一个矩形用来做碰撞检测.(同样的方法在Bullet也有)
        tankRectForCollisionCheck.x=this.x;
        tankRectForCollisionCheck.y=this.y;
        tankRectForCollisionCheck.width=Tank.getWIDTH();
        tankRectForCollisionCheck.height=Tank.getHEIGHT();
        if(this.group==Group.BAD){  //根据敌我双方的group属性, 分别赋值不同的射击策略
            String badFSName=(String) PropertyManager.get("badFS_String");

            try {
                fs = (FireStrategy)Class.forName(badFSName).getDeclaredConstructor().newInstance(null);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }else {
            String goodFSName=(String) PropertyManager.get("goodFS_String");

            try {
                fs = (FireStrategy)Class.forName(goodFSName).getDeclaredConstructor().newInstance(null);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public void paint(Graphics g) {
        /*
        Color color = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 50, 50);
        g.setColor(color);
       */
        if (!isAlive) {
            tankFrame.invaders.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankL : ResourceMgr.goodTankL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankR : ResourceMgr.goodTankR, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankU : ResourceMgr.goodTankU, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.BAD ? ResourceMgr.badTankD : ResourceMgr.goodTankD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (!moving) {
            return;
        }
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
        tankRectForCollisionCheck.x=x;
        tankRectForCollisionCheck.y=y;
        //每次move之后random一下, 如果 是BAD并且random>8 就发射子弹. 这样做是为了模拟敌机发射子弹.
        if (this.group == Group.BAD && random.nextInt(100) > 85) {
            this.fire();
        }
        //之所以不和上面写在一个if里是为了敌机让改变方向的频率不和发子弹的频率一样
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
            //边界检测
            boundCheck();

        }
    }

    private void boundCheck() {
        if (this.x < 10) {
            x = 10;
        }
        if (this.y < 20) {
            y = 20;
        }
        if (this.x > TankFrame.GAME_WIDTH - Tank.getWIDTH() / 2 - 10) {
            x = TankFrame.GAME_WIDTH - Tank.getWIDTH() / 2 - 10;
        }
        if (this.y > TankFrame.GAME_HEIGHT - Tank.getHEIGHT() / 2 - 20) {
            y = TankFrame.GAME_HEIGHT - Tank.getHEIGHT() / 2 - 20;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        fs.fire(this);
    }

    public void suicide() {
        setAlive(false);
    }
}
