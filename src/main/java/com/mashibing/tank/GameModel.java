package com.mashibing.tank;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Explode.Explode;
import com.mashibing.tank.collisionDetection.*;
import com.mashibing.tank.resourceMgr.Audio;
import com.mashibing.tank.resourceMgr.PropertyMgr;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 11:19
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class GameModel {
    public static List<GameObject> gameObjectList=new LinkedList<>();
    CollisionDetectionChain collision=new CollisionDetectionChain();
    //敌方坦克初始数量
    //int initTankEnemyCount=Integer.parseInt((String)PropertyMgr.get("initTankEnemyCount"));
    //我方坦克
    public Tank myTank;
    //坦克总数
    public int tankCount;
    //子弹总数
    public int bulletCount;
    //爆炸总数
    public int explodeCount;
    Random random=new Random();
    //构造方法
    public GameModel() {
        tankCount=bulletCount=explodeCount=0;
        //刷新主战坦克
        pushMy();
        //从配置文件读取敌方坦克数量初始值
        tankCount=Integer.parseInt(PropertyMgr.get("initTankEnemyCount"));
        //初始化敌方坦克
        gameObjectList.addAll(TankFactory.getTanks(tankCount));
        tankCount++;
        //刷新墙
        gameObjectList.add(new Wall(100, 200, 40, 300));
        gameObjectList.add(new Wall(400, 200, 400, 40));
        gameObjectList.add(new Wall(600, 400, 40, 300));
        //播放背景音乐
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
    }
    //刷新主战坦克
    public void pushMy(){
        gameObjectList.remove(myTank);
        myTank=new Tank(400,400,Camp.MY);
        gameObjectList.add(myTank);
    }
    //刷新敌方坦克
    public void pushEnemy() {
        int count=random.nextInt(10)+1;
        gameObjectList.addAll(TankFactory.getTanks(count));
        tankCount=count;
    }

    //在窗口中绘图的方法
    public void paint(Graphics graphics){
        //进行碰撞检测
        collision.doTask();
        //画出所有游戏单位
        int t,b,e;
        t=b=e=0;
        for (int i=0;i<gameObjectList.size();i++) {
            GameObject object=gameObjectList.get(i);
            if (object instanceof Tank){
                t++;
            }else if (object instanceof Bullet){
                b++;
            }else if (object instanceof Explode){
                e++;
            }
            object.paint(graphics);
        }
        bulletCount=b;tankCount=t;explodeCount=e;
        //显示子弹数
        graphics.drawString("子弹数量:"+bulletCount, 10, 40);
        //显敌人数
        graphics.drawString("敌人数量:"+(tankCount-1), 10, 60);
        //显示爆炸数
        graphics.drawString("爆炸数量:"+explodeCount, 10, 80);
    }
    //按键处理
    public void handleKeyPressed(KeyEvent e) {
        myTank.handleKeyPressed(e);
    }
    public void handleKeyReleased(KeyEvent e) {
        myTank.handleKeyReleased(e);
    }
    //坦克列表
    public Tank getMyTank() {
        return myTank;
    }
    public void setMyTank(Tank myTank) {
        this.myTank = myTank;
    }
}
