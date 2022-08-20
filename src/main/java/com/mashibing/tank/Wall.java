package com.mashibing.tank;

import com.mashibing.coordinate.Coordinate;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 15:43
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Wall extends GameObject {
    public int width ;
    public int height ;
    public boolean exist=true;
    //墙所占的矩形
    Rectangle rectangle;
    //返回一个矩形
    public Rectangle getRectangle() {
        return this.rectangle;
    }
    public Wall(Coordinate coordinate,int width,int height) {
        super(coordinate);
        this.width=width;
        this.height=height;
        rectangle=new Rectangle();
        this.rectangle.x=this.coordinate.x;
        this.rectangle.y=this.coordinate.y;
        this.rectangle.width=this.width;
        this.rectangle.height=this.height;
    }
    public Wall(int x, int y,int width,int height) {
        super(x, y);
        this.width=width;
        this.height=height;
        rectangle=new Rectangle();
        this.rectangle.x=this.coordinate.x;
        this.rectangle.y=this.coordinate.y;
        this.rectangle.width=this.width;
        this.rectangle.height=this.height;
    }
    public void paint(Graphics graphics){
        //绘制子弹
        if (exist){
            Color color = graphics.getColor();
            graphics.setColor(Color.GRAY);
            graphics.fillRect(coordinate.x,coordinate.y,width, height);
            graphics.setColor(color);
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isDie() {
        return false;
    }

    @Override
    public void die() {

    }

    @Override
    public String toString() {
        return "Wall{" +coordinate.toString()+
                "width=" + width +
                ", height=" + height +
                ", exist=" + exist +
                ", rectangle=" + rectangle +
                '}';
    }
}
