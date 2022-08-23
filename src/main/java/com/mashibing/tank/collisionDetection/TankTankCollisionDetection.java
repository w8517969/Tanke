package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 14:10
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class TankTankCollisionDetection implements CollisionDetection {
    @Override
    public boolean collision(GameObject o1,GameObject o2) {
        return staticCollision(o1,o2);
    }

    @Override
    public boolean doTask() {
        for (int i = 0; i < GameModel.gameObjectList.size()-1; i++) {
            for (int j = i+1; j < GameModel.gameObjectList.size()&&collision(GameModel.gameObjectList.get(i), GameModel.gameObjectList.get(j)); j++) {
                ;
            }
        }
        return true;
    }
    public static boolean staticCollision(GameObject o1, GameObject o2){
        if (o1 instanceof Tank&&o2 instanceof Tank) {
            Tank tank1 = (Tank) o1;
            Tank tank2 = (Tank) o2;
            //如果相撞了
            if (CollisionDetection.test(tank1.getRectangle(), tank2.getRectangle())) {
//                //判断相对方向
//                Dir itDir = tank1.coordinate.dirOfMe(tank2.getCoordinate());
//                if (tank1.dir == itDir && tank1.isMoving()) {
//                    tank1.Back();
//                }
//                itDir = tank2.coordinate.dirOfMe(tank1.getCoordinate());
//                if (tank2.dir == itDir && tank2.isMoving()) {
//                    tank2.Back();
//                }

                tank1.Back();
                tank2.Back();
            }
        }
        return true;
    }
}
