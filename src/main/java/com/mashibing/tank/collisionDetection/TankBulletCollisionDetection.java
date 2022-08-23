package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 15:10
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class TankBulletCollisionDetection implements CollisionDetection {

    @Override
    public boolean doTask() {
        for (int i = 0; i < GameModel.gameObjectList.size()-1; i++) {
            if (GameModel.gameObjectList.get(i).isDie()) break;
            for (int j = i+1; j < GameModel.gameObjectList.size(); j++) {
                if (GameModel.gameObjectList.get(i).isDie()) break;
                if (!(collision(GameModel.gameObjectList.get(i), GameModel.gameObjectList.get(j))))break;
            }
        }
        return true;
    }

    @Override
    public boolean collision(GameObject o1,GameObject o2) {
        return staticCollision(o1,o2);
    }
    public static boolean staticCollision(GameObject o1, GameObject o2){
        if ((o1 instanceof Tank)&&(!(o1.isDie())&&(o2 instanceof Bullet)&&(((Bullet) o2).isExist()))) {
            Tank tank = (Tank) o1;
            Bullet bullet=(Bullet) o2;
            //如果相撞了
            if (tank.camp!=bullet.camp&&CollisionDetection.test(tank.getRectangle(), bullet.getRectangle())) {
                tank.life--;
                if (tank.life<=0){
                    tank.die();
                }
                bullet.setExist(false);
                return false;
            }
        }else if ((o2 instanceof Tank)&&(!(o2.isDie()))&&(o1 instanceof Bullet)&&(((Bullet) o1).isExist())) {
            Tank tank = (Tank) o2;
            Bullet bullet=(Bullet) o1;
            //如果相撞了
            if (tank.camp!=bullet.camp&&CollisionDetection.test(tank.getRectangle(), bullet.getRectangle())) {
                tank.life--;
                if (tank.life<=0){
                    tank.die();
                }
                bullet.setExist(false);
                return false;
            }
        }
        return true;
    }
}
