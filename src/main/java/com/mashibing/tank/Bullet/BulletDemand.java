package com.mashibing.tank.Bullet;

import com.mashibing.tank.Dir;
import com.mashibing.tank.coordinate.Coordinate;


/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/12 - 08 - 12 - 21:43
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public class BulletDemand {
    public int size;
    //方向
    public Dir[] dirs;
    //各个方向的子弹数量
    public int[] quantity;
    //子弹飞行速度
    public int[] speeds;
    //子弹坐标
    public Coordinate[][] coordinates;
//构造方法
    public BulletDemand( Dir[] dirs, int[] quantity, int[] speeds, Coordinate[][] coordinates) {
        this.dirs = dirs;
        this.quantity = quantity;
        this.speeds = speeds;
        this.coordinates = coordinates;
        this.size = dirs.length;
    }
}
