package com.mashibing.tank.Netty.Msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 18:06
 * @Description: Tank
 * @version: 1.0
 */
public class JoinMsg extends Msg {
    public static final int LENGTH =0;//消息长度
    @Override
    public void doIt() {
    }
    @Override
    protected void toBytesWork(DataOutputStream dos) throws IOException {
        dos.writeInt(JoinMsg.LENGTH);//包长度
        dos.writeInt(msgType.ordinal());//包类型
    }
    @Override
    protected void byteComeWork(DataInputStream dis) throws IOException {
        //已经跳过包类型和长度
    }

    public JoinMsg() {
        this.msgType=MsgType.Join;
    }

    @Override
    public String toString() {
        return "JoinMsg{" +
                "msgType=" + msgType +
                '}';
    }
}
