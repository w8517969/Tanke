package com.mashibing.tank.Bullet;

import com.mashibing.tank.Tank;

import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/13 - 08 - 13 - 19:59
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public abstract class BulletFactory {
    //获取单个子弹
    public  abstract Bullet getBullet(Tank tank);
    //获取特殊子弹列表
    public  abstract List<Bullet> getBullets(Tank tank, BulletDemand demand);
    //获取四方向子弹
    public  abstract List<Bullet> getBulletsFourDir(Tank tank);
    //获取全屏子弹
    public  abstract List<Bullet> getBulletsAll(Tank tank);
}
