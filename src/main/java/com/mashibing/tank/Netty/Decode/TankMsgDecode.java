package com.mashibing.tank.Netty.Decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/20 - 08 - 20 - 20:29
 * @Description: Decode
 * @version: 1.0
 */
public class TankMsgDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<8) return;//TCP分包、粘包处理
        int x=byteBuf.readInt();
        int y=byteBuf.readInt();
        //list.add(new TankMsg(x, y));
    }
}
