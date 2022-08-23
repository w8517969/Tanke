package com.mashibing.tank.Netty.Msg;

import com.mashibing.tank.Dir;
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
public class TankMsg extends Msg {
    //消息长度
    public static final int LENGTH =33;
    //消息的长度就是消息体已经排除过消息长度和消息类型的剩余长度，
    // 也就是此行下面所有数据的长度之和
    public int x,y;//坐标
    public Dir dir;//方向
    public int life;//生命
    public boolean moving;//移动状态
    public UUID uuid;

    @Override
    public void doIt() {
        //更新坦克信息，如果没有就添加

    }

    @Override
    protected void toBytesWork(DataOutputStream dos) throws IOException {
        dos.writeInt(this.LENGTH);//包长度
        dos.writeInt(msgType.ordinal());//包类型
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeInt(dir.ordinal());
        dos.writeInt(life);
        dos.writeBoolean(moving);
        dos.writeLong(uuid.getMostSignificantBits());
        dos.writeLong(uuid.getLeastSignificantBits());
    }

    @Override
    protected void byteComeWork(DataInputStream dis) throws IOException {
        //已经跳过包类型和长度
        this.x=dis.readInt();
        this.y=dis.readInt();
        this.dir=Dir.values()[dis.readInt()];
        this.life=dis.readInt();
        this.moving=dis.readBoolean();
        this.uuid=new UUID(dis.readLong(), dis.readLong());
    }

    public TankMsg(Tank tank) {
        this.msgType=MsgType.Tank;
        this.x = tank.coordinate.x;
        this.y = tank.coordinate.y;
        this.dir = tank.dir;
        this.life = tank.life;
        this.moving = tank.isMoving();
        this.uuid=tank.uuid;
    }
    public TankMsg(int x, int y, Dir dir, int life, boolean moving, UUID uuid) {
        this.msgType=MsgType.Tank;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.life = life;
        this.moving = moving;
        this.uuid=uuid;
    }

    public TankMsg() {
        this.msgType=MsgType.Tank;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", life=" + life +
                ", moving=" + moving +
                ", uuid=" + uuid +
                ", msgType=" + msgType +
                '}';
    }
}
