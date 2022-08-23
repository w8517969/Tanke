package com.mashibing.tank.Bullet;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;
import com.mashibing.tank.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/6 - 08 - 06 - 18:22
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public class DefaultBulletFactory extends BulletFactory {
    public static DefaultBulletFactory bulletFactory;
    static {
        bulletFactory=new DefaultBulletFactory();
    }
//将构造方法隐藏
    private DefaultBulletFactory(){
    }
//获取子弹工厂的引用
    public static BulletFactory getBulletFactory() {
        return bulletFactory;
    }
//四方向子弹方法
    @Override
    public  List<Bullet> getBulletsFourDir(Tank tank){
        //方向
        Dir[] dirs={Dir.UP,Dir.DOWN,Dir.LEFT,Dir.RIGHT};
        //各个方向的子弹数量
        int[] quantitys={1,1,1,1};
        //子弹飞行速度
        int[] speeds={20,20,20,20};
        //子弹坐标
        Coordinate[][] coordinates=new Coordinate[quantitys.length][];
        for (int i = 0; i < quantitys.length; i++) {
            coordinates[i]=new Coordinate[quantitys[i]];
            for (int j = 0; j < quantitys[i]; j++) {
                coordinates[i][j] = new Coordinate(tank.coordinate.x+Tank.WIDTH/2,tank.coordinate.y+Tank.HEIGHT/2);
            }
        }
        //子弹需求
        List<Bullet> bullets= getBullets(tank ,new BulletDemand(dirs, quantitys, speeds, coordinates));
        for (int i = 0; i < bullets.size(); i++) {
            ((DefaultBullet)bullets.get(i)).setCoordinate(tank);
        }
        return bullets;
    }
//获取全屏子弹
    @Override
    public  List<Bullet> getBulletsAll(Tank tank){
        //方向
        Dir[] dirs={Dir.UP,Dir.UP,Dir.LEFT,Dir.LEFT};
        //各个方向的子弹数量
        int[] quantitys={15,15,10,10};
        //子弹飞行速度
        int[] speeds={20,30,20,30};
        //子弹坐标
        Coordinate[][] coordinates=new Coordinate[quantitys.length][];
        for (int i = 0; i < quantitys.length; i++) {
            coordinates[i]=new Coordinate[quantitys[i]];
            for (int j = 0; j < quantitys[i]; j++) {
                switch (dirs[i]){
                    case UP:
                        coordinates[i][j] = new Coordinate((TankFrame.GAME_WIDTH-DefaultBullet.width) / quantitys[i]*j+((TankFrame.GAME_WIDTH-DefaultBullet.width) / quantitys[i])/2, TankFrame.GAME_HEIGHT - DefaultBullet.height);
                        break;
                    case DOWN:
                        coordinates[i][j] = new Coordinate((TankFrame.GAME_WIDTH-DefaultBullet.width) / quantitys[i]*j+((TankFrame.GAME_WIDTH-DefaultBullet.width) / quantitys[i])/2, 20);
                        break;
                    case LEFT:
                        coordinates[i][j] = new Coordinate(TankFrame.GAME_WIDTH-DefaultBullet.width, (TankFrame.GAME_HEIGHT-DefaultBullet.height) / quantitys[i]*j+((TankFrame.GAME_HEIGHT-DefaultBullet.height) / quantitys[i])/2);
                        break;
                    case RIGHT:
                        coordinates[i][j] = new Coordinate(20, (TankFrame.GAME_HEIGHT-DefaultBullet.height) / quantitys[i]*j+((TankFrame.GAME_HEIGHT-DefaultBullet.height) / quantitys[i])/2);
                        break;
                }
            }
        }
        //子弹需求
        return getBullets(tank,new BulletDemand(dirs, quantitys, speeds, coordinates));
    }
    @Override
//获取特殊子弹
    public  List<Bullet> getBullets(Tank tank,BulletDemand demand){
        List<Bullet> bullets=new ArrayList<>();
        //总的需求数量
        for (int i = 0; i < demand.size; i++) {
            //需求[i]中的子弹需求数量
            for (int j = 0; j < demand.quantity[i]; j++) {
                DefaultBullet bullet=new DefaultBullet(demand.coordinates[i][j],tank.camp,demand.dirs[i]);
                //速度
                bullet.setSpeed(demand.speeds[i]);
                bullets.add(bullet);
            }
        }
        return bullets;
    }
    @Override
//普通子弹
    public  Bullet getBullet(Tank tank){
        DefaultBullet bullet=new DefaultBullet(tank.coordinate,tank.camp,tank.dir);
        bullet.setCoordinate(tank);
        return bullet;
    }
}
