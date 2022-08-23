package com.mashibing.tank.Netty.Server;

import com.mashibing.tank.Netty.Decode.MsgDecode;
import com.mashibing.tank.Netty.Encode.MsgEncode;
import com.mashibing.tank.Netty.Msg.Msg;
import com.mashibing.tank.Netty.Msg.UUIDMsg;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/19 - 08 - 19 - 3:20
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Server {
    public static HashMap<UUID,ChannelHandlerContext> map=new HashMap<>();

    public  void startUp() {
        //线程池
        EventLoopGroup boosGroup=new NioEventLoopGroup(1);//boosGroup只负责客户端的连接
        EventLoopGroup workerGroup=new NioEventLoopGroup(2);//处理连接好以后发生的IO事件
        //辅助启动类ServerBootstrap
        ServerBootstrap b=new ServerBootstrap();
        //ChannelFuture f=b.group(group). channel(NioSocketChannel.class). handler（）. connect("localhost", 8888);
        //未来结果          线程池           管道                            处理者       连接

        ChannelFuture f= null;
        try {
            f = b.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerChannelInitializer())
                    .bind(8888)
                    .sync();
        System.out.println("服务器启动完成");
            f.channel().closeFuture().sync();//closeFuture--》等着有人调用close（）方法的时候的钩子函数
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            p1.addLast(new MsgEncode())
                    .addLast(new MsgDecode())
                    .addLast(new ServerChildHandler());
        }
    }
    static class ServerChildHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Msg msg1=(Msg) msg;
            System.out.println("服务器收到消息："+msg1);
            switch (msg1.msgType){
                case Tank:
                    radioAll(msg1);
                    break;
                case Join:
                    //广播新玩家通知
                    radioAll(msg1);
                    break;
                case Exit:
                    System.out.println("广播消息"+msg1);
                    removeChannelHandlerContext(ctx);
                    radioAll(msg1);
                    break;
                case UUID:
                default:
                    System.out.println("类型不匹配");


            }
            System.out.println("广播了消息"+msg1);
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {


            UUID uuid=UUID.randomUUID();
            UUIDMsg msg=new UUIDMsg(uuid);
            System.out.println("连接成功，向客户端发送UUID"+msg);
            ctx.writeAndFlush(msg);
            addChannelHandlerContext(uuid, ctx);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            //删除出异常的客户端channel，并关闭连接
            Server.removeChannelHandlerContext(ctx);
        }
    }
    public static void removeChannelHandlerContext(ChannelHandlerContext ctx){
        UUID uuid=null;
        for (Map.Entry<UUID,ChannelHandlerContext> item:map.entrySet()) {
            if (item.getValue().equals(ctx)){
                uuid=item.getKey();
                break;
            }
        }
        map.remove(uuid);
        ctx.close();
    }
    public static void addChannelHandlerContext(UUID uuid,ChannelHandlerContext ctx){
        map.put(uuid, ctx);
    }
    public static void radioAll(Msg msg){
        for (Map.Entry<UUID,ChannelHandlerContext> item:map.entrySet()) {
            item.getValue().writeAndFlush(msg);
        }
    }
}
