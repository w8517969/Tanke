package com.mashibing.tank.Bullet;

import com.mashibing.coordinate.Coordinate;
import com.mashibing.tank.Camp;
import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.collisionDetection.CollisionDetection;
import com.mashibing.tank.resourceMgr.ResourceMgr;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/13 - 08 - 13 - 22:48
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public class RedBullet extends Bullet  { //子弹的大小
    public static int WIDTH=30;
    public static int HEIGHT=30;
    //子弹所占的矩形
    public Rectangle rectangle=new Rectangle();
    //移动速度
    private int speed=20;
    //存在状态
    private boolean exist=true;
    //方向
    private Dir dir;

    public RedBullet(Coordinate coordinate, Camp camp, Dir dir) {
        super(coordinate, camp);
        this.dir = dir;
    }
    public RedBullet(Tank tank){
        super(tank.coordinate, tank.camp);
        setCoordinate(tank);
        this.dir = tank.dir;
    }
    public RedBullet(int x, int y, Camp camp, Dir dir) {
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
            Color color = graphics.getColor();
            graphics.setColor(Color.RED);
            graphics.fillRect(coordinate.x, coordinate.y, WIDTH, HEIGHT);
            graphics.setColor(color);
        }else {
            die();
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
                this.coordinate.x=(tank.getWidth()-WIDTH)/2+tank.getCoordinate().x;
                this.coordinate.y=tank.getCoordinate().y-HEIGHT/2;
                break;
            case DOWN:
                this.coordinate.x=(tank.getWidth()-WIDTH)/2+tank.getCoordinate().x;
                this.coordinate.y=tank.getCoordinate().y+tank.getHeight()-HEIGHT/2;
                break;
            case LEFT:
                this.coordinate.x=tank.getCoordinate().x-WIDTH/2;
                this.coordinate.y=(tank.getHeight()-HEIGHT)/2+tank.getCoordinate().y;
                break;
            case RIGHT:
                this.coordinate.x=tank.getWidth()-WIDTH/2+tank.getCoordinate().x;
                this.coordinate.y=(tank.getHeight()-HEIGHT)/2+tank.getCoordinate().y;
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
    //大小
    public  int getWidth() {
        return WIDTH;
    }
    public  void setWidth(int WIDTH) {
        this.WIDTH = WIDTH;
    }
    public  int getHeight() {
        return HEIGHT;
    }
    public  void setHeight(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    @Override
    public boolean isDie() {
        return !exist;
    }
    @Override
    public void die() {
        exist=false;
    }

    @Override
    //返回一个矩形
    public Rectangle getRectangle() {
        this.rectangle.x=this.coordinate.x;
        this.rectangle.y=this.coordinate.y;
        this.rectangle.width=this.WIDTH;
        this.rectangle.height=this.HEIGHT;
        return this.rectangle;
    }
}
