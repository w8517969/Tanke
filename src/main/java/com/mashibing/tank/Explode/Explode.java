package com.mashibing.tank.Explode;

import com.mashibing.tank.GameObject;
import com.mashibing.tank.coordinate.Coordinate;
import com.mashibing.tank.resourceMgr.ResourceMgr;

import java.awt.*;


/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/7 - 08 - 07 - 20:25
 * @Description: com.mashibing.tank.Explode
 * @version: 1.0
 */
public class Explode extends GameObject {
    //爆炸的大小
    private static int WIDTH= ResourceMgr.explodes[0].getWidth();
    private static int HEIGHT=ResourceMgr.explodes[0].getHeight();
    //存在状态
    private boolean exist=true;
    //爆炸显示状态
    private int count=0;

    //绘制爆炸效果
    public void paint(Graphics graphics){
        //检查爆炸的存在状态
        if (exist){
            graphics.drawImage(ResourceMgr.explodes[count++], coordinate.x, coordinate.y, null);
            if (count>=ResourceMgr.explodes.length){
                exist=false;
            }
        }
    }
    //构造
    public Explode(Coordinate coordinate) {
        super(coordinate);
    }

    public Explode(int x, int y) {
        super(x, y);
    }

    //坐标
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(){
        coordinate=new Coordinate();
    }
    public void setCoordinate(int x,int y){
        this.coordinate.x=x;this.coordinate.y=y;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = new Coordinate(coordinate);
    }
    //存在状态
    @Override
    public boolean isDie() {
        return !exist;
    }
    public void die() {
        exist=false;
    }
    public boolean isExist() {
        return exist;
    }
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    //大小
    public  int getWidth() {
        return WIDTH;
    }
    public  void setWidth(int WIDTH) {
        Explode.WIDTH = WIDTH;
    }
    public  int getHeight() {
        return HEIGHT;
    }
    public  void setHeight(int HEIGHT) {
        Explode.HEIGHT = HEIGHT;
    }
}
