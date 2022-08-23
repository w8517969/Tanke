package com.mashibing.tank.Netty.Msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/22 - 08 - 22 - 3:23
 * @Description: com.mashibing.tank.Netty.Msg
 * @version: 1.0
 */
public class ExitMsg extends Msg{
    public static final int LENGTH =16;//消息长度
    public UUID uuid;

    @Override
    public void doIt() {

    }

    @Override
    protected void toBytesWork(DataOutputStream dos) throws IOException {
        dos.writeInt(this.LENGTH);//包长度
        dos.writeInt(msgType.ordinal());//包类型
        dos.writeLong(uuid.getMostSignificantBits());
        dos.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    protected void byteComeWork(DataInputStream dis) throws IOException {
        //已经跳过包类型和长度
        this.uuid=new UUID(dis.readLong(), dis.readLong());
    }

    public ExitMsg() {
        this.msgType=MsgType.Exit;
    }

    public ExitMsg(UUID uuid) {
        this.msgType=MsgType.Exit;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ExitMsg{" +
                "uuid=" + uuid +
                ", msgType=" + msgType +
                '}';
    }
}
