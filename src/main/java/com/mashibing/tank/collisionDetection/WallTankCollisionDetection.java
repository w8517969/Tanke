package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;
import com.mashibing.tank.Wall;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/15 - 08 - 15 - 12:55
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class WallTankCollisionDetection implements CollisionDetection {
    @Override
    public boolean doTask() {
        for (int i = 0; i < GameModel.gameObjectList.size()-1; i++) {
            for (int j = i+1; j < GameModel.gameObjectList.size()&&collision(GameModel.gameObjectList.get(i), GameModel.gameObjectList.get(j)); j++) {
                ;
            }
        }
        return true;
    }

    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        return staticCollision(o1,o2);
    }
    public static boolean staticCollision(GameObject o1, GameObject o2){
        if (o1 instanceof Wall && o2 instanceof Tank && !o2.isDie()) {
            Wall wall = (Wall) o1;
            Tank tank=(Tank) o2;
            //如果相撞了
            if (CollisionDetection.test(wall.getRectangle(), tank.getRectangle())) {
                tank.Back();
            }
        }else if (o2 instanceof Wall && o1 instanceof Tank && !o1.isDie()) {
            Wall wall = (Wall) o2;
            Tank tank=(Tank) o1;
            //如果相撞了
            if (CollisionDetection.test(wall.getRectangle(), tank.getRectangle())) {
                tank.Back();
            }
        }
        return true;
    }
}
