package com.mashibing.tank.Decorator;

import com.mashibing.tank.GameObject;
import com.mashibing.tank.coordinate.Coordinate;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/15 - 08 - 15 - 15:23
 * @Description: com.mashibing.tank.Decorator
 * @version: 1.0
 */
public class BoxDecorator extends Decorator {
    public int width,height;

    public BoxDecorator(Coordinate coordinate, GameObject gameObject, int width, int height) {
        super(coordinate.x-4, coordinate.y-4, gameObject);
        this.width = width+8;
        this.height = height+8;
    }

    public BoxDecorator(int x, int y, GameObject gameObject, int width, int height) {
        super(x-4, y-4, gameObject);
        this.width = width+8;
        this.height = height+8;
    }
    public BoxDecorator( GameObject gameObject) {
        super(gameObject.coordinate.x-4, gameObject.coordinate.y-4, gameObject);
        this.width = gameObject.getWidth()+8;
        this.height = gameObject.getHeight()+8;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void paint(Graphics graphics) {
        gameObject.paint(graphics);
        //同步坐标
        coordinate.x=gameObject.coordinate.x-4;
        coordinate.y=gameObject.coordinate.y-4;
        Color color = graphics.getColor();
        graphics.setColor(Color.WHITE);
        graphics.drawRect(coordinate.x, coordinate.y, width,height);
        graphics.setColor(color);
    }

    @Override
    public boolean isDie() {
        return gameObject.isDie();
    }

    @Override
    public void die() {
        gameObject.die();
    }

    @Override
    public String toString() {
        return "BoxDecorator{" +
                "width=" + width +
                ", height=" + height +
                ", gameObject=" + gameObject +
                ", coordinate=" + coordinate +
                '}';
    }
}
