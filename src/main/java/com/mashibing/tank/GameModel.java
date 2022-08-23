package com.mashibing.tank;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Explode.Explode;
import com.mashibing.tank.Netty.Client.Client;
import com.mashibing.tank.Netty.Msg.TankMsg;
import com.mashibing.tank.collisionDetection.CollisionDetectionChain;
import com.mashibing.tank.resourceMgr.Audio;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 11:19
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class GameModel {
    private static final GameModel GAME_MODEL=new GameModel();
    public static List<GameObject> gameObjectList=new LinkedList<>();
    public static HashMap<UUID,Tank> tankHashMap=new HashMap<>();
    CollisionDetectionChain collision=new CollisionDetectionChain();
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
    private GameModel() {
        tankCount=bulletCount=explodeCount=0;
        //刷新墙
//        gameObjectList.add(new Wall(100, 200, 40, 300));
//        gameObjectList.add(new Wall(400, 200, 400, 40));
//        gameObjectList.add(new Wall(600, 400, 40, 300));
        //播放背景音乐
        new Thread(()-> {new Audio("audio/war1.wav").loop();} , "播放背景音乐").start();
    }
    public static GameModel getGameModel(){
        return GAME_MODEL;
    }
    //创建主战坦克
    public void pushMy(){
        while (true){
            if (Client.uuid!=null)break;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        remove(myTank);
        myTank=new Tank(random.nextInt(TankFrame.GAME_WIDTH-90)+5,
                random.nextInt(TankFrame.GAME_HEIGHT-90)+5,
                Client.uuid,
                Camp.MY,1,false,Dir.values()[random.nextInt(Dir.values().length)]);
        add(myTank);
    }
    //刷新坦克信息
    public void pushTank(TankMsg msg){
        if (tankHashMap.get(msg.uuid)==null){
            add(new Tank(msg.x, msg.y, msg.uuid, Camp.ENEMY,1,msg.moving,msg.dir));
        }else {
            pushHasTank(msg);
        }
    }
    private void pushHasTank(TankMsg msg){
        Tank tank=tankHashMap.get(msg.uuid);
        tank.coordinate.x=msg.x;
        tank.coordinate.y=msg.y;
        tank.life=1;
        tank.setMoving(msg.moving);
        tank.dir=msg.dir;
    }
    //在窗口中绘图的方法
    public void paint(Graphics graphics){
        //进行碰撞检测
        collision.doTask();
        //计数并画出所有游戏单位
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
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        //显示子弹数
        graphics.drawString("子弹数量:"+bulletCount, 10, 40);
        //显敌人数
        graphics.drawString("敌人数量:"+(tankCount-1), 10, 60);
        //显示爆炸数
        graphics.drawString("爆炸数量:"+explodeCount, 10, 80);
        graphics.setColor(color);
    }
    //删除游戏元素
    public void remove(GameObject o){
        gameObjectList.remove(o);
        if (o instanceof Tank){
            tankHashMap.remove(((Tank) o).uuid);
        }
    }
    public void remove(UUID uuid){
        gameObjectList.remove(tankHashMap.get(uuid));
        tankHashMap.remove(uuid);
    }
    //添加
    public void add(GameObject o){
        gameObjectList.add(o);
        if (o instanceof Tank){
            tankHashMap.put(((Tank)o).uuid, (Tank) o);
        }
    }
    //某个键被摁下去的时候调用
    public void handleKeyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar()+"键被摁下，GM分发到主战坦克处理");
        myTank.handleKeyPressed(e);
    }
    //某个键被弹起的时候调用
    public void handleKeyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar()+"键被弹起，GM分发到主战坦克处理");
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
