package com.lee.tank;

public interface FireStrategy { //定义发射子弹的策略
    void fire(Tank t); //必须添加Tank参数, 因为要确定子弹从哪里射出来
}
