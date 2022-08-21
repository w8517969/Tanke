package com.mashibing.tank.Netty.Server;

import com.mashibing.tank.Netty.Encode.TankMsgEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/19 - 08 - 19 - 3:20
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Server {
    public static List<ChannelHandlerContext> list=new ArrayList<>();
    static int anInt=1;

    public  void startUp() throws InterruptedException {
        //线程池
        EventLoopGroup boosGroup=new NioEventLoopGroup(1);//boosGroup只负责客户端的连接
        EventLoopGroup workerGroup=new NioEventLoopGroup(2);//处理连接好以后发生的IO事件
        //辅助启动类ServerBootstrap
        ServerBootstrap b=new ServerBootstrap();
        //ChannelFuture f=b.group(group). channel(NioSocketChannel.class). handler（）. connect("localhost", 8888);
        //未来结果          线程池           管道                            处理者       连接
        try {
            ChannelFuture f=b.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer())
                    .bind(8888)
                    .sync();
            System.out.println("服务器启动完成");
            f.channel().closeFuture().sync();//closeFuture--》等着有人调用close（）方法的时候的钩子函数
        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }

    }
    private class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
        //initChannel方法在连接建立的时候调用，一般只写事件发生后干什么就行
        @Override
        protected void initChannel(SocketChannel ch) {
            System.out.println("通道创建完成");
            ChannelPipeline p1=ch.pipeline();
            p1.addLast(new TankMsgEncode())
                    .addLast(new ServerChildHandler());
        }
    }
    static class ServerChildHandler extends ChannelInboundHandlerAdapter {
        public int ID;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf1=(ByteBuf)msg;
            byte[] bytes=new byte[buf1.readableBytes()+1];
            buf1.getBytes(buf1.readerIndex(), bytes);
            bytes[bytes.length-1]="\n".getBytes()[0];
            String string=ID+"号同学发来消息：\n";
            System.out.println("收到客户端"+"的消息："+new String(bytes));
            for (ChannelHandlerContext i:list) {
                ByteBuf buf2=Unpooled.copiedBuffer(string.getBytes());
                ByteBuf buf3=Unpooled.copiedBuffer(bytes);
                i.writeAndFlush(buf2);
                i.writeAndFlush(buf3);//writeAndFlush方法会自动释放ByteBuf的内存，所以需要注意
            }
            System.out.println("向客户端发送了消息："+new String(bytes));
            if (buf1!=null){
                ReferenceCountUtil.release(buf1);//手动释放ByteBuf的内存
            }
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            list.add(ctx);
            //ctx.write(new TankMsg(50,40));
            ID=anInt;
            for (ChannelHandlerContext i:list) {
                ByteBuf buf1= Unpooled.copiedBuffer((anInt+"号同学上线并加入聊天室\n").getBytes());
                i.writeAndFlush(buf1);
            }
            anInt++;
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            //删除出异常的客户端channel，并关闭连接
            Server.list.remove(ctx);
            ctx.close();
        }

    }
}
