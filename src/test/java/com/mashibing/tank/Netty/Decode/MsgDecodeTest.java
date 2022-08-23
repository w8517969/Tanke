package com.mashibing.tank.Netty.Decode;

import com.mashibing.tank.Netty.Encode.MsgEncode;
import com.mashibing.tank.Netty.Msg.JoinMsg;
import com.mashibing.tank.Netty.Msg.Msg;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/23 - 08 - 23 - 1:40
 * @Description: com.mashibing.tank.Netty.Decode
 * @version: 1.0
 */
class MsgDecodeTest {
    @Test
    public void decode() {
        EmbeddedChannel ch = new EmbeddedChannel(new MsgEncode(),new MsgDecode());
        Msg msg=new JoinMsg();
        ch.writeOutbound(msg);
        ch.writeInbound(msg);
        Msg msg1 = ch.readOutbound();
        Msg msg2 = ch.readInbound();
        System.out.println(msg1.msgType);
        System.out.println(msg2.msgType);
    }
}