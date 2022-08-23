package com.mashibing.tank.Netty.Decode;

import com.mashibing.tank.Netty.Msg.*;
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
public class MsgDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<4) return;//TCP分包、粘包处理
        byteBuf.markReaderIndex();
        int length=byteBuf.readInt();
        int readMax=byteBuf.readableBytes();
        if (byteBuf.readableBytes()<length){
            byteBuf.resetReaderIndex();
            return;
        }
        MsgType msgType=MsgType.values()[byteBuf.readInt()];
        Msg msg=null;
        byte[] bytes=new byte[length];
        System.out.println("开始"+msgType+"消息的拆包");
        switch (msgType){
            case Tank:
                msg=new TankMsg();
                byteBuf.readBytes(bytes);
                break;
            case Exit:
                msg=new ExitMsg();
                byteBuf.readBytes(bytes);
                break;
            case UUID:
                msg=new UUIDMsg();
                byteBuf.readBytes(bytes);
                break;
            case Join:
                msg=new JoinMsg();
                byteBuf.readBytes(bytes);
                break;
        }
        msg.byteCome(bytes);
        System.out.println("拆包完成"+msg);
        list.add(msg);

    }
}
