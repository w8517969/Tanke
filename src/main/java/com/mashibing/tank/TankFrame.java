package com.mashibing.tank;

import com.mashibing.tank.Netty.Client.Client;
import com.mashibing.tank.Netty.Msg.ExitMsg;
import com.mashibing.tank.resourceMgr.PropertyMgr;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/5 - 08 - 05 - 23:56
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class TankFrame extends Frame {
    private static  TankFrame TANK_FRAME=null;
    public static  int GAME_WIDTH=Integer.parseInt(PropertyMgr.get("GAME_WIDTH"));
    public static  int GAME_HEIGHT=Integer.parseInt(PropertyMgr.get("GAME_HEIGHT"));
    public static GameModel GM=GameModel.getGameModel();

    //避免显示闪烁，使用缓存空间避免闪烁
    Image offScreenImage=null;//图片
    @Override
    public void update(Graphics g){
        //为缓冲空间开辟图片缓冲区
        if (offScreenImage==null){
            offScreenImage=this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        //获取缓冲空间的画笔
        Graphics goffScreenImage=offScreenImage.getGraphics();
        //保存画笔原始色
        Color c=goffScreenImage.getColor();
        //设置画笔颜色
        goffScreenImage.setColor(Color.white);
        //画出背景
        goffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        //将画笔颜色还原
        goffScreenImage.setColor(c);
        //调用paint方法在图片缓冲区画完所有画
        paint(goffScreenImage);
        //将画好的图片一次性展示到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }
    //构造方法
    private TankFrame()  {
        setLocation(200, 100);
        setSize(GAME_WIDTH, GAME_HEIGHT);//设置窗口大小
        setResizable(false);//设置窗口不可以改变大小
        setTitle("坦克大战");//设置窗口标题
        setVisible(true);//设置窗口为显示状态
        addKeyListener(new MyKeyListener());//给窗口增加一个键盘监听事件0
        //添加Window监听器，点击右上角的小叉可以关闭窗口
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Client.channel.writeAndFlush(new ExitMsg());
                try {
                    Client.channel.close().sync();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
    public static TankFrame getTankFrame(){
        if (TANK_FRAME==null){
            TANK_FRAME=new TankFrame();
        }
        return TANK_FRAME;
    }
    @Override
    //在窗口中绘图的方法
    public void paint(Graphics graphics){
        GM.paint(graphics);
    }
    //键盘监听处理类，负责处理键盘事件
    class MyKeyListener extends KeyAdapter{
        @Override
        //某个键被摁下去的时候调用
        public void keyPressed(KeyEvent e) {
            GM.handleKeyPressed(e);
        }
        @Override
        //某个键被弹起来的时候调用
        public void keyReleased(KeyEvent e) {
            GM.handleKeyReleased(e);
        }
    }

    public GameModel getGM() {
        return GM;
    }
    public void setGM(GameModel GM) {
        this.GM = GM;
    }
}
