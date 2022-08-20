package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Explode.Explode;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;

import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 20:25
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class ExplodeDie implements Task {
    @Override
    public boolean doTask() {
        for (int i = 0; i < GameModel.gameObjectList.size(); i++) {
            GameObject object=GameModel.gameObjectList.get(i);
            if (object instanceof Explode &&(!((Explode) object).isExist())){
                GameModel.gameObjectList.remove(object);
            }
        }
        return true;
    }
}
