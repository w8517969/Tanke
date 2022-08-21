package com.mashibing.test01;


import com.mashibing.tank.*;


/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/5 - 08 - 05 - 21:01
 * @Description: com.mashibing.test01
 * @version: 1.0
 */
public class T {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        GameModel GM=tankFrame.getGM();
        while (true){
            if (GM.myTank.isDie()){//我的坦克死完了
                GM.pushMy();
            }
            if (GM.tankCount<=1){//敌方坦克死完了
                GM.pushEnemy();
            }
            while (GM.tankCount>1 && !GM.myTank.isDie()){
                Thread.sleep(40);
                //因为直接调用paint方法是没办法拿到系统画笔Graphics的，所以调用Frame类的repaint方法会自动调用paint方法
                tankFrame.repaint();
//                System.out.println(GameModel.gameObjectList.size());
              }
        }
    }
}
