package com.mashibing.tank.Netty;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/21 - 08 - 21 - 15:59
 * @Description: com.mashibing.tank.Netty
 * @version: 1.0
 */
public abstract class MyMsg {
    public MsgType msgType;
    public abstract void readMsg();
    public abstract void writeMsg();
    public abstract void callMe(Object...objects);

    public MyMsg(MsgType msgType) {
        this.msgType = msgType;
    }
}
