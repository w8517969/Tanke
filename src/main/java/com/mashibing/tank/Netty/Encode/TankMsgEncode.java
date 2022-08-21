package com.mashibing.tank.Netty.Encode;

import com.mashibing.tank.Netty.TankMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 18:16
 * @Description: Encode
 * @version: 1.0
 */
public class TankMsgEncode extends MessageToByteEncoder<TankMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
//        byteBuf.writeInt(tankMsg.x);
//        byteBuf.writeInt(tankMsg.y);
    }
}
