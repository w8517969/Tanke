package com.mashibing.tank.Netty.Encode;

import com.mashibing.tank.Netty.Msg.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 18:16
 * @Description: Encode
 * @version: 1.0
 */
public class MsgEncode extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(msg.toBytes());
    }
}
