package com.mashibing.tank.Netty;

import com.mashibing.tank.Tank;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 18:06
 * @Description: Tank
 * @version: 1.0
 */
public class TankMsg extends MyMsg{
    public Tank tank;
    @Override
    public void readMsg() {

    }
    @Override
    public void writeMsg() {

    }
    @Override
    public void callMe(Object... objects) {

    }

    public TankMsg(MsgType msgType, Tank tank) {
        super(msgType);
        this.tank = new Tank(tank.coordinate.x, tank.coordinate.y, tank.camp);
    }
}
