package com.mashibing.tank.Bullet;

import com.mashibing.coordinate.Coordinate;
import com.mashibing.tank.*;
import com.mashibing.tank.resourceMgr.ResourceMgr;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/6 - 08 - 06 - 18:13
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public class DefaultBullet extends Bullet  {
    //子弹的大小
    public static int width=ResourceMgr.bulletU.getWidth();
    public  static int height=ResourceMgr.bulletU.getHeight();
    //子弹所占的矩形
    public Rectangle rectangle=new Rectangle();
    //移动速度
    private int speed=20;
    //存在状态
    private boolean exist=true;
    //方向
    private Dir dir;

    public DefaultBullet(Coordinate coordinate, Camp camp, Dir dir) {
        super(coordinate, camp);
        this.dir = dir;
    }
    public DefaultBullet(Tank tank){
        super(tank.coordinate, tank.camp);
        setCoordinate(tank);
        this.dir = tank.dir;
}
    public DefaultBullet(int x, int y, Camp camp, Dir dir) {
        super(x, y, camp);
        this.dir = dir;
    }

    //更新子弹坐标，检查存在状态，绘制子弹
    @Override
    public void paint(Graphics graphics){
        //更新坐标
        changeCoordinate();
        //检查子弹的存在状态
        setExist();
        //绘制子弹
        if (exist){
            switch (dir){
                case UP:
                    graphics.drawImage(ResourceMgr.bulletU, coordinate.x, coordinate.y, null);
                    break;
                case DOWN:
                    graphics.drawImage(ResourceMgr.bulletD, coordinate.x, coordinate.y, null);
                    break;
                case LEFT:
                    graphics.drawImage(ResourceMgr.bulletL, coordinate.x, coordinate.y, null);
                    break;
                case RIGHT:
                    graphics.drawImage(ResourceMgr.bulletR, coordinate.x, coordinate.y, null);
                    break;
            }
        }
    }
    //坐标
    public Coordinate getCoordinate() {
        return coordinate;
    }
    private void changeCoordinate() {
        switch (dir){
            case UP:coordinate.y-=speed;break;
            case DOWN:coordinate.y+=speed;break;
            case LEFT:coordinate.x-=speed;break;
            case RIGHT:coordinate.x+=speed;break;
        }
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = new Coordinate(coordinate);
    }
    public void setCoordinate(Tank tank) {
        switch (tank.getDir()){
            case UP:
                this.coordinate.x=(tank.getWidth()-width)/2+tank.getCoordinate().x;
                this.coordinate.y=tank.getCoordinate().y-height/2;
                break;
            case DOWN:
                this.coordinate.x=(tank.getWidth()-width)/2+tank.getCoordinate().x;
                this.coordinate.y=tank.getCoordinate().y+tank.getHeight()-height/2;
                break;
            case LEFT:
                this.coordinate.x=tank.getCoordinate().x-width/2;
                this.coordinate.y=(tank.getHeight()-height)/2+tank.getCoordinate().y;
                break;
            case RIGHT:
                this.coordinate.x=tank.getWidth()-width/2+tank.getCoordinate().x;
                this.coordinate.y=(tank.getHeight()-height)/2+tank.getCoordinate().y;
                break;

        }

    }
    //方向
    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
    }
    //速度
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    //检查存活状态
    public void setExist() {
        exist=!(coordinate.x>TankFrame.GAME_WIDTH||coordinate.x<0||coordinate.y<0||coordinate.y>TankFrame.GAME_HEIGHT);
    }
    //存在状态
    @Override
    public boolean isExist() {
        return exist;
    }
    @Override
    public void setExist(boolean exist) {
        this.exist = exist;
    }
    @Override
    public boolean isDie() {
        return !exist;
    }
    @Override
    public void die() {
        this.exist=false;
    }
    //大小
    public  int getWidth() {
        return width;
    }
    public  void setWidth(int width) {
        this.width = width;
    }
    public  int getHeight() {
        return height;
    }
    public  void setHeight(int height) {
        this.height = height;
    }
    @Override
    //返回一个矩形
    public Rectangle getRectangle() {
        this.rectangle.x=this.coordinate.x;
        this.rectangle.y=this.coordinate.y;
        this.rectangle.width=this.width;
        this.rectangle.height=this.height;
        return this.rectangle;
    }
}
