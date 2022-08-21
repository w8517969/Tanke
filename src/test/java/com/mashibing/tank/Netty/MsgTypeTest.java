package com.mashibing.tank.Netty;

import com.mashibing.tank.Camp;
import com.mashibing.tank.Tank;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;


/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/21 - 08 - 21 - 13:41
 * @Description: com.mashibing.tank.Netty
 * @version: 1.0
 */
class MsgTypeTest {

    @Test
    void Msg() {
        Tank tank=new Tank(8, 6, Camp.MY);
        MyMsg test=new TankMsg(MsgType.TankMsg1, tank);

        EmbeddedChannel ch=new EmbeddedChannel();
        ch.writeInbound(test);
        /*ByteBuf buf=(ByteBuf)ch.readOutbound();
        int x=buf.readInt();
        int y=buf.readInt();*/

        TankMsg test2=ch.readInbound();
        ((TankMsg) test).tank.setCoordinate(50, 60);
        System.out.println(test2==test);
    }

}