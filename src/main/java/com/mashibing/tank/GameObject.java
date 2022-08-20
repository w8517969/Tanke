package com.mashibing.tank;

import com.mashibing.coordinate.Coordinate;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 13:24
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public abstract class GameObject {
    public Coordinate coordinate=new Coordinate();
    public abstract void paint(Graphics graphics);
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract boolean isDie();
    public abstract void die();
    public GameObject(Coordinate coordinate) {
        this.coordinate.x = coordinate.x;
        this.coordinate.y = coordinate.y;
    }
    public GameObject(int x,int y) {
        this.coordinate.x = x;
        this.coordinate.y = y;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
