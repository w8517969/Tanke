package com.mashibing.tank.Bullet;

import com.mashibing.tank.Camp;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.coordinate.Coordinate;


import java.awt.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/13 - 08 - 13 - 19:44
 * @Description: com.mashibing.tank.Bullet
 * @version: 1.0
 */
public abstract class Bullet extends GameObject {
    public Camp camp;
    public abstract Rectangle getRectangle();
    public abstract boolean isExist();
    public abstract void setExist(boolean exist);
    public abstract  int getWidth();
    public abstract int getHeight();

    public Bullet(Coordinate coordinate, Camp camp) {
        super(coordinate);
        this.camp = camp;
    }

    public Bullet(int x, int y, Camp camp) {
        super(x, y);
        this.camp = camp;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "isExit="+isExist()+
                ",camp=" + camp +
                ", coordinate=" + coordinate +
                '}';
    }
}
