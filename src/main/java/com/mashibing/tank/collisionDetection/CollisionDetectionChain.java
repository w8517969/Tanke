package com.mashibing.tank.collisionDetection;


import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/14 - 08 - 14 - 13:52
 * @Description: com.mashibing.tank.collisionDetection
 * @version: 1.0
 */
public class CollisionDetectionChain implements Task {
    List<Task> tasks = new LinkedList<>();
    //发生碰撞
    @Override
    public boolean doTask() {
//        for (int i = 0; i < GameModel.gameObjectList.size()-1; i++) {
//            for (int g = i+1; g <GameModel.gameObjectList.size() ; g++) {
//                for (int j = 0; j < tasks.size(); j++) {
//                    if (tasks.get(j) instanceof CollisionDetection) {
//                        if (!(((CollisionDetection) tasks.get(j)).collision(GameModel.gameObjectList.get(i),
//                                GameModel.gameObjectList.get(g)))) {
//                            break;
//                        }
//                    } else {
//                        tasks.get(j).doTask();
//                    }
//                }
//            }
//        }
        for (int j = 0; j < tasks.size(); j++) {
             tasks.get(j).doTask();
        }
        return true;
    }

    public CollisionDetectionChain() {
                 add(new TankTankCollisionDetection())
                .add(new TankBulletCollisionDetection())
                .add(new WallBulletCollisionDetection())
                .add(new WallTankCollisionDetection())
                .add(new DecoratorCollisionDetection())
                .add(new DecoratorDie())
                .add(new BulletDie())
                .add(new ExplodeDie())
                .add(new TankDie());
    }

    public CollisionDetectionChain add(Task task){
        tasks.add(task);
        return this;
    }
    public void remove(Task task){
        tasks.remove(task);
    }
}
