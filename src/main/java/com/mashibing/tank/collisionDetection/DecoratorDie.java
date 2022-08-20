package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.Decorator.Decorator;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/15 - 08 - 15 - 19:44
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class DecoratorDie implements Task {
    @Override
    public boolean doTask() {
        for (int i = 0; i < GameModel.gameObjectList.size(); i++) {
            GameObject object=GameModel.gameObjectList.get(i);
            if (object instanceof Decorator && object.isDie()){
                GameModel.gameObjectList.remove(object);
            }
        }
        return true;
    }
}
