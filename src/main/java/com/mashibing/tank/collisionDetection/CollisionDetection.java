package com.mashibing.tank.collisionDetection;

import com.mashibing.tank.GameObject;

import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/7 - 08 - 07 - 14:30
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public interface CollisionDetection extends Task {
    //碰撞检测
    public  boolean collision(GameObject o1, GameObject o2);
    //是否发生碰撞
    static  boolean test(Rectangle rectangle1, Rectangle rectangle2){
        return rectangle1.intersects(rectangle2);
    }
}
