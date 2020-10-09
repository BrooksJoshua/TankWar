package com.lee.tank;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    static final int GAME_WIDTH = (int) PropertyManager.get("GAME_WIDTH_int");;
    static final int GAME_HEIGHT = (int) PropertyManager.get("GAME_HEIGHT_int");

    Tank myTank = new Tank((int)PropertyManager.get("initialTankX_int"),(int) PropertyManager.get("initialTankY_int"), Dir.UP, this, Group.GOOD); //this表示在当前窗口创建坦克对象
    //Bullet bullet = new Bullet(20, 20, Dir.RIGHT);
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<Tank> invaders = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<Explode>();


    public TankFrame() {
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle((String)PropertyManager.get("GAME_NAME_String"));
        this.setVisible(true);
        /*添加窗口监听, 方便关闭窗口, 否则只能通过停止程序才能关闭窗口*/
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new MyKeyListener());

    }

    /*以下代码是用双缓冲的方式解决屏幕闪烁的问题*/
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    /*双缓冲的方式解决屏幕闪烁的问题, 代码到此结束*/

    /*该方法是在游戏窗口中画图,*/
    @Override
    public void paint(Graphics g) {
        /*暂时当前窗口的子弹数量*/
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Fired :" + bullets.size(), 10, 100);
        g.drawString("Invaders:" + invaders.size(), 10, 140);
        g.setColor(color);
        myTank.paint(g);
        // explode.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < invaders.size(); i++) {
            invaders.get(i).paint(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < invaders.size(); j++) {
                bullets.get(i).collideWithInvadersOrNot(invaders.get(j));
            }
        }
    }

    class MyKeyListener extends KeyAdapter {
        boolean BL = false;
        boolean BR = false;
        boolean BU = false;
        boolean BD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keyPress(keyCode, true);
            setMainTankDir();
            System.out.println(e.getKeyCode() + " pressed");
            repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            keyRelease(keyCode, false);
            setMainTankDir(); //release必须也设置方向, 否则之前安国的建的方向标记一直是true
            System.out.println(e.getKeyCode() + " released");
        }

        public void keyPress(int keyCode, boolean flag) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    BL = flag;
                    break;
                case KeyEvent.VK_RIGHT:
                    BR = flag;
                    break;
                case KeyEvent.VK_UP:
                    BU = flag;
                    break;
                case KeyEvent.VK_DOWN:
                    BD = flag;
                    break;
                default:
                    break;
            }
        }

        public void keyRelease(int keyCode, boolean flag) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    BL = flag;
                    break;
                case KeyEvent.VK_RIGHT:
                    BR = flag;
                    break;
                case KeyEvent.VK_UP:
                    BU = flag;
                    break;
                case KeyEvent.VK_DOWN:
                    BD = flag;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
        }


        private void setMainTankDir() {
            if (!BL && !BR && !BU && !BD) {
                myTank.setMoving(false); //如果所有键的标记都是false, 说明没有一个方向键被按下, 所以设置静止
                return;
            } else {
                myTank.setMoving(true);  //否则, 设为移动,并继续设置移动方向
                if (BL) {
                    myTank.setDir(Dir.LEFT);
                }
                if (BR) {
                    myTank.setDir(Dir.RIGHT);
                }
                if (BU) {
                    myTank.setDir(Dir.UP);
                }
                if (BD) {
                    myTank.setDir(Dir.DOWN);
                }
            }

        }

    }


}
