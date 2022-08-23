package com.mashibing.tank;


import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Bullet.BulletFactory;
import com.mashibing.tank.Bullet.DefaultBulletFactory;
import com.mashibing.tank.Decorator.BoxDecorator;
import com.mashibing.tank.Netty.Client.Client;
import com.mashibing.tank.Netty.Msg.FireMsg;
import com.mashibing.tank.Netty.Msg.TankDirMsg;
import com.mashibing.tank.Netty.Msg.TankMoveMsg;
import com.mashibing.tank.coordinate.Coordinate;
import com.mashibing.tank.resourceMgr.Audio;
import com.mashibing.tank.resourceMgr.ResourceMgr;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/6 - 08 - 06 - 16:17
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Tank extends GameObject {
    public UUID uuid;
    //阵营
    public Camp camp;
    //子弹工厂
    private BulletFactory bulletFactory=DefaultBulletFactory.getBulletFactory();

    //坦克所占的矩形
    Rectangle rectangle=new Rectangle();
    //随机类
    Random random=new Random();
    ///////////////////////////////////////
    //坦克的大小
    public static int WIDTH=60;//ResourceMgr.tankU.getWidth();
    public static int HEIGHT=60;//ResourceMgr.tankU.getHeight();
    //生命
    public int life=1;
    //记录坦克移动状态
    private boolean moving=false;
    //记录坦克的方向
    public Dir dir=Dir.UP;
    //设置移动速度
    private int speed=8;
    //记录按键的状态，true表示被按下
    boolean BU=false;
    boolean BD=false;
    boolean BL=false;
    boolean BR=false;
    //键按下的处理
    public void handleKeyPressed(KeyEvent e){
        if (!isDie()){
            int key=e.getKeyCode();
            switch (key){
                case KeyEvent.VK_UP:
                    setBU(true);
                    setMoving();
                    break;
                case KeyEvent.VK_DOWN:
                    setBD(true);
                    setMoving();
                    break;
                case KeyEvent.VK_LEFT:
                    setBL(true);
                    setMoving();
                    break;
                case KeyEvent.VK_RIGHT:
                    setBR(true);
                    setMoving();
                    break;
            }
        }
    }
    //键弹起的处理
    public void handleKeyReleased(KeyEvent e){
        if (!isDie()){
            int key=e.getKeyCode();
            switch (key){
                case KeyEvent.VK_UP:
                    setBU(false);
                    setMoving();
                    break;
                case KeyEvent.VK_DOWN:
                    setBD(false);
                    setMoving();
                    break;
                case KeyEvent.VK_LEFT:
                    setBL(false);
                    setMoving();
                    break;
                case KeyEvent.VK_RIGHT:
                    setBR(false);
                    setMoving();
                    break;
                case KeyEvent.VK_SPACE:
                    System.out.println("按下了开火键,向服务器发送开火消息");
                    Client.channel.writeAndFlush(new FireMsg(this));
                    break;
                case KeyEvent.VK_NUMPAD0:
                    fireAll();
                    break;
            }
        }
    }
    //开火
    public void fire(){
        fireOne();
    }
    private void fireOne() {
        Bullet bullet=bulletFactory.getBullet(this);
        GameModel.gameObjectList.add(
                new BoxDecorator(
                new BoxDecorator(
                new BoxDecorator(bullet))));
        //GameModel.gameObjectList.add(bulletFactory.getBullet(this));
        //播放开炮声音
        new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }
    //打出四个方向的子弹
    private void fireFour(){
        GameModel.gameObjectList.addAll(bulletFactory.getBulletsFourDir(this));
        //播放开炮声音
        new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }
    //打出大炮弹
    private void fireAll(){
        GameModel.gameObjectList.addAll(bulletFactory.getBulletsAll(this));
    }
    //根据方向调整坐标值
    private void changeCoordinate(){
        switch (dir){
            case UP:
               coordinate.y-=speed;
               if (coordinate.y<0){
                   coordinate.y=0;
               }
               break;
            case DOWN:
                coordinate.y+=speed;
                if (coordinate.y>TankFrame.GAME_HEIGHT-this.HEIGHT){
                    coordinate.y=TankFrame.GAME_HEIGHT-this.HEIGHT;
                }
                break;
            case LEFT:
                coordinate.x-=speed;
                if (coordinate.x<0){
                    coordinate.x=0;
                }
                break;
            case RIGHT:
                coordinate.x+=speed;
                if (coordinate.x>TankFrame.GAME_WIDTH-this.WIDTH){
                    coordinate.x=TankFrame.GAME_WIDTH-this.WIDTH;
                }
                break;
        }
    }
    //反向移动
    public void changeCoordinateBack() {
        switch (dir){
            case UP:
                coordinate.y=coordinate.y+speed+4;
                if (coordinate.y>TankFrame.GAME_HEIGHT-speed){
                    coordinate.y=TankFrame.GAME_HEIGHT-speed;
                }
                break;
            case DOWN:
                coordinate.y=coordinate.y-speed-4;
                if (coordinate.y<0){
                    coordinate.y=0;
                }
                break;
            case LEFT:
                coordinate.x=coordinate.x+speed+4;
                if (coordinate.x>TankFrame.GAME_WIDTH-speed){
                    coordinate.x=TankFrame.GAME_WIDTH-speed;
                }
                break;
            case RIGHT:
                coordinate.x=coordinate.x-speed-4;
                if (coordinate.x<0){
                    coordinate.x=0;
                }
                break;
        }
    }

    //更新坦克，其中包括更改坐标，方向，在窗口中显示
    public void paint(Graphics graphics){
        //如果坦克存活
        if (!isDie()) {
            if (moving) {
                changeCoordinate();//修改坐标
                changeDir();//修改方向
            }
            //画出UUID
            Color color = graphics.getColor();
            graphics.setColor(Color.orange);
            graphics.drawString(uuid.toString(), this.coordinate.x, this.coordinate.y-10);
            graphics.setColor(color);
            //画出坦克
            switch (dir) {
                case UP:
                    if (camp==Camp.MY) {
                        graphics.drawImage(ResourceMgr.tankU, coordinate.x, coordinate.y, null);
                    }else {
                        graphics.drawImage(ResourceMgr.bandTankU, coordinate.x, coordinate.y, null);
                    }
                    break;
                case DOWN:
                    if (camp==Camp.MY) {
                        graphics.drawImage(ResourceMgr.tankD, coordinate.x, coordinate.y, null);
                    }else {
                        graphics.drawImage(ResourceMgr.bandTankD, coordinate.x, coordinate.y, null);
                    }
                    break;
                case LEFT:
                    if (camp==Camp.MY) {
                        graphics.drawImage(ResourceMgr.tankL, coordinate.x, coordinate.y, null);
                    }else {
                        graphics.drawImage(ResourceMgr.bandTankL, coordinate.x, coordinate.y, null);
                    }
                    break;
                case RIGHT:
                    if (camp==Camp.MY) {
                        graphics.drawImage(ResourceMgr.tankR, coordinate.x, coordinate.y, null);
                    }else {
                        graphics.drawImage(ResourceMgr.bandTankR, coordinate.x, coordinate.y, null);
                    }
                    break;
            }
        }
    }
    //随机移动
    private void randomMoving() {
        setDir(randomDir());
        setMoving(true);
    }
    //构造
    public Tank(Coordinate coordinate, UUID uuid, Camp camp, int life, boolean moving, Dir dir) {
        super(coordinate);
        this.uuid = uuid;
        this.camp = camp;
        this.life = life;
        this.moving = moving;
        this.dir = dir;
    }
    public Tank(int x, int y, UUID uuid, Camp camp, int life, boolean moving, Dir dir) {
        super(x, y);
        this.uuid = uuid;
        this.camp = camp;
        this.life = life;
        this.moving = moving;
        this.dir = dir;
    }
    @Override
    public String toString() {
        return "Tank{" +
                "coordinate=" + coordinate +
                '}';
    }
    //坐标
    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(int x,int y){
        this.coordinate.x=x;
        this.coordinate.y=y;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate.x = coordinate.x;
        this.coordinate.y = coordinate.y;
    }
    //行走状态
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public boolean isMoving() {
        return moving;
    }
    public void setMoving() {
        boolean nowMoving=(BU||BD||BL||BR);
        if (moving!=nowMoving) {
            moving=nowMoving;
            Client.channel.writeAndFlush(new TankMoveMsg(this));
        }
    }
    //阵营
    public Camp getCamp() {
        return camp;
    }
    public void setCamp(Camp camp) {
        this.camp = camp;
    }
    //方向
    //根据按键状态调整坦克方向的方法
    private void changeDir(){
        if (BU||BD||BL||BR) {
            Dir nowDir=Dir.DOWN;
            if (BU) {
                nowDir = Dir.UP;
            }
            if (BD) {
                nowDir = Dir.DOWN;
            }
            if (BL) {
                nowDir = Dir.LEFT;
            }
            if (BR) {
                nowDir = Dir.RIGHT;
            }
            if (dir!=nowDir){
                setDir(nowDir);
                Client.channel.writeAndFlush(new TankDirMsg(this));
            }
        }
    }
    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public Dir randomDir(){
        Dir[] dirs = Dir.values();
        return dirs[random.nextInt(dirs.length)];
    }
    //速度
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    //方向键状态
    public boolean isBU() {
        return BU;
    }
    public void setBU(boolean BU) {
            this.BU=BU;
    }
    public boolean isBD() {
        return BD;
    }
    public void setBD(boolean BD) {
        this.BD = BD;
    }
    public boolean isBL() {
        return BL;
    }
    public void setBL(boolean BL) {
        this.BL = BL;
    }
    public boolean isBR() {
        return BR;
    }
    public void setBR(boolean BR) {
        this.BR = BR;
    }
    //大小
    public int getWidth() {
        return WIDTH;
    }
    public void setWidth(int WIDTH) {
        this.WIDTH = WIDTH;
    }
    public int getHeight() {
        return HEIGHT;
    }
    public void setHeight(int HEIGHT){this.HEIGHT = HEIGHT;}
    //死亡
    public void die() {
        life=0;
    }
    public boolean isDie(){
        return life<=0;
    }
    //返回一个矩形
    public Rectangle getRectangle() {
        this.rectangle.x=this.coordinate.x;
        this.rectangle.y=this.coordinate.y;
        this.rectangle.width=this.WIDTH;
        this.rectangle.height=this.HEIGHT;
        return this.rectangle;
    }
}
