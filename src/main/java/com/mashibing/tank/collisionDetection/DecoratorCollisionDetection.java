package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.Bullet.Bullet;
import com.mashibing.tank.Decorator.Decorator;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;
import com.mashibing.tank.Wall;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/15 - 08 - 15 - 17:19
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class DecoratorCollisionDetection implements CollisionDetection {
    @Override
    public  boolean collision(GameObject o1, GameObject o2){
        return staticCollision(o1, o2);
    }
    public static boolean staticCollision(GameObject o1, GameObject o2) {
        if (o1 instanceof Decorator){
            return myCollision(o1, o2);
        }else if (o2 instanceof Decorator){
            return myCollision(o2, o1);
        }
        return true;
    }
    private static boolean myCollision(GameObject o1, GameObject o2){
        boolean b=false;
        GameObject gameObject=((Decorator) o1).getGameObject();
        if (gameObject instanceof Tank){
            if (o2 instanceof Tank){
                b=TankTankCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Bullet){
                b=TankBulletCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Wall){
                b=WallTankCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Decorator){
                b=DecoratorCollisionDetection.staticCollision(gameObject, o2);
            }
        }else if (gameObject instanceof Bullet){
            if (o2 instanceof Tank){
                b=TankBulletCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Wall){
                b=WallBulletCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Decorator){
                b=DecoratorCollisionDetection.staticCollision(gameObject, o2);
            }
        }else if (gameObject instanceof Wall){
            if (o2 instanceof Tank){
                b=WallTankCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Bullet){
                b=WallBulletCollisionDetection.staticCollision(gameObject, o2);
            }else if (o2 instanceof Decorator){
                b=DecoratorCollisionDetection.staticCollision(gameObject, o2);
            }
        }else if (gameObject instanceof Decorator){
            b=DecoratorCollisionDetection.staticCollision(gameObject, o2);
        }
        return b;
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
}
