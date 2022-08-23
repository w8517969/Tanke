package com.mashibing.tank.Netty.Msg;

import com.mashibing.tank.Tank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 18:06
 * @Description: Tank
 * @version: 1.0
 */
public class TankMoveMsg extends Msg {
    //消息长度
    public static final int LENGTH =25;
    //消息的长度就是消息体已经排除过消息长度和消息类型的剩余长度，
    // 也就是此行下面所有数据的长度之和
    public int x,y;//坐标
    public boolean moving;//移动状态
    public UUID uuid;
    @Override
    public void doIt() {
        //更新坦克信息，如果没有就添加
    }
    @Override
    protected void toBytesWork(DataOutputStream dos) throws IOException {
        dos.writeInt(TankMoveMsg.LENGTH);//包长度
        dos.writeInt(msgType.ordinal());//包类型
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeBoolean(moving);
        dos.writeLong(uuid.getMostSignificantBits());
        dos.writeLong(uuid.getLeastSignificantBits());
    }
    @Override
    protected void byteComeWork(DataInputStream dis) throws IOException {
        //已经跳过包类型和长度
        this.x=dis.readInt();
        this.y=dis.readInt();
        this.moving=dis.readBoolean();
        this.uuid=new UUID(dis.readLong(), dis.readLong());
    }
    public TankMoveMsg(Tank tank) {
        this.msgType=MsgType.TankMove;
        this.x = tank.coordinate.x;
        this.y = tank.coordinate.y;
        this.moving = tank.isMoving();
        this.uuid=tank.uuid;
    }
    public TankMoveMsg(int x, int y, boolean moving, UUID uuid) {
        this.msgType=MsgType.TankMove;
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.uuid=uuid;
    }
    public TankMoveMsg() {
        this.msgType=MsgType.TankMove;
    }
    @Override
    public String toString() {
        return "TankMoveMsg{" +
                "x=" + x +
                ", y=" + y +
                ", moving=" + moving +
                ", uuid=" + uuid +
                ", msgType=" + msgType +
                '}';
    }
}
