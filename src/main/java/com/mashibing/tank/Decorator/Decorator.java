package com.mashibing.tank.Decorator;

import com.mashibing.tank.GameObject;
import com.mashibing.tank.coordinate.Coordinate;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/15 - 08 - 15 - 14:55
 * @Description: com.mashibing.tank.Decorator
 * @version: 1.0
 */
public abstract class Decorator extends GameObject {
    GameObject gameObject;
    public Decorator(Coordinate coordinate, GameObject gameObject) {
        super(coordinate);
        this.gameObject = gameObject;
    }
    public Decorator(int x, int y, GameObject gameObject) {
        super(x, y);
        this.gameObject = gameObject;
    }
    public  GameObject getRootObject(){
        return (gameObject instanceof Decorator)
                ?  ( (Decorator) gameObject )  .getRootObject()
                : gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
