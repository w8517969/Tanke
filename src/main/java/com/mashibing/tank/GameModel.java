package com.mashibing.tank;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Decorator.Decorator;
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
    private static  GameModel GAME_MODEL=new GameModel();
    public static List<GameObject> gameObjectList=new LinkedList<>();
    public static HashMap<UUID,Tank> tankHashMap=new HashMap<>();
    private CollisionDetectionChain collision=new CollisionDetectionChain();
    //播放背景音乐
    private Thread thread= new Thread(()-> {new Audio("audio/war1.wav").loop();} , "播放背景音乐");
    //我方坦克
    public Tank myTank;
    //坦克总数
    public int tankCount=0;
    //子弹总数
    public int bulletCount=0;
    //爆炸总数
    public int explodeCount=0;
    Random random=random=new Random();;

    //构造方法
    private GameModel() {
        thread.start();
    }
    public static GameModel getGameModel(){
        if (GAME_MODEL==null){
            GAME_MODEL=new GameModel();
        }
        return GAME_MODEL;
    }
    public void initialization(){
        tankCount=bulletCount=explodeCount=0;
        //刷新墙
        gameObjectList.add(new Wall(100, 200, 40, 300));
        gameObjectList.add(new Wall(400, 200, 400, 40));
        gameObjectList.add(new Wall(600, 400, 40, 300));
        pushMy();
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
        //计数并画出所有游戏单位
        int t,b,e;
        t=b=e=0;
        for (int i=0;i<gameObjectList.size();i++) {
            GameObject object=gameObjectList.get(i);
            GameObject temp=object;
            if (object instanceof Decorator){
                object=((Decorator) object).getRootObject();

            }
            if (object instanceof Tank){
                t++;
            }else if (object instanceof Bullet){
                b++;
            }else if (object instanceof Explode){
                e++;
            }
            temp.paint(graphics);
        }
        bulletCount=b;tankCount=t;explodeCount=e;
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        //显示子弹数
        graphics.drawString("子弹数量:"+bulletCount, 10, 40);
        //显敌人数
        graphics.drawString("玩家数量:"+tankCount, 10, 60);
        //显示爆炸数
        graphics.drawString("爆炸数量:"+explodeCount, 10, 80);
        graphics.setColor(color);
        //进行碰撞检测
        collision.doTask();
    }
    //删除游戏元素
    public void remove(GameObject o){
        if (o==null)return;
        gameObjectList.remove(o);
        if (o instanceof Tank){
            //显示爆炸效果，播放爆炸声音
            gameObjectList.add(new Explode(o.coordinate));
            new Thread(new Audio("audio/explode.wav")::play,"播放爆炸声").start();
            remove(((Tank) o).uuid);
        }
    }
    public void remove(UUID uuid){
        gameObjectList.remove(tankHashMap.get(uuid));
        tankHashMap.remove(uuid);
        if (uuid.equals(myTank.uuid)){
            myTank=null;
        }
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
        myTank.handleKeyPressed(e);
    }
    //某个键被弹起的时候调用
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
