package com.mashibing.tank.Netty.Client;

import com.mashibing.tank.Netty.Decode.MsgDecode;
import com.mashibing.tank.Netty.Encode.MsgEncode;
import com.mashibing.tank.Netty.Msg.JoinMsg;
import com.mashibing.tank.Netty.Msg.Msg;
import com.mashibing.tank.Netty.Msg.TankMsg;
import com.mashibing.tank.Netty.Msg.UUIDMsg;
import com.mashibing.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

public  class Client {
    private static final Client CLIENT=new Client();
    public static TankFrame frame;//游戏窗口
    public  static Channel channel=null;//通道
    public static UUID uuid=null;//唯一标示

    private Client() {

    }
    public static Client getClient(){
        return CLIENT;
    }

    public void startUp() {
        /*netty中所有的方法都是异步方法，没有同步方法，要想确认方法执行完程序才可以往下走就需要调用sync()方法*/

        //事件处理线程池----事件（Event）不停处理（Loop）的一个池子（group）
        //事件处理线程池group负责处理整个Channel上的所有事件
        EventLoopGroup group = new NioEventLoopGroup(1);//这里默认无参是创建计算机有多少个核*2个线程，客户端如果业务不多建议传1
        //辅助启动类Bootstrap
        Bootstrap b=new Bootstrap();
        //处理
        ClientChannelInitializer clientChannelInitializer=new ClientChannelInitializer();
        try {
            //ChannelFuture f=b.group(group). channel(NioSocketChannel.class). handler（）. connect("localhost", 8888);
            //未来结果          线程池           管道                            处理者       连接
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)//一个Nio版本（非阻塞）的管道，也可以换成阻塞版
                    //handler代表这个管道上如果发生事件的时候交给谁去处理
                    .handler(clientChannelInitializer)
                    //这里的connect方法是异步的，无论连接成功与否都会返回并且继续往下执行，
                    // 所以要确认方法执行完有结果了程序才可以往下走，就需要调用f.sync()方法一直等到未来的结果产生
                    .connect("localhost", 8888);
            //f是方法connect的一个ChannelFuture类型的返回值，代表一个异步方法执行后产生的后果
            //f.addListener是为这个结果添加一个监听器,如果在添加好监听器之前就已经有了结果，那么会直接调用operationComplete方法
            f.addListener(new ChannelFutureListener() {
                //只要未来的结果一出来，就会调用operationComplete方法
                @Override
                public void operationComplete(ChannelFuture channelFuture) {
                    if (channelFuture.isSuccess()) {//如果未来的结果是成功的
                        System.out.println("结果是成功");
                    } else {
                        System.out.println("结果是没成功");
                    }
                }
            });
            System.out.println("结果出来了");
            //调用f.sync()方法一直等到未来的结果产生
            f.sync();
            //初始化
            frame = TankFrame.getTankFrame();
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //因为直接调用paint方法是没办法拿到系统画笔Graphics的，所以调用Frame类的repaint方法会自动调用paint方法
                    frame.repaint();
                }
            },"刷新窗口").start();
            //调用startUp方法的线程会阻塞在这里
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //让客户端优雅的结束
            group.shutdownGracefully();
        }
    }

    //ChannelInitializerChannel的初始化器，参数SocketChannel是指定为网络连接管道
    private class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        //initChannel方法在连接建立的时候调用，一般只写事件发生后干什么就行
        @Override
        protected void initChannel(SocketChannel ch) {
            System.out.println("通道创建完成");
            ChannelPipeline p1=ch.pipeline();
            p1.addLast(new MsgEncode())//在这里挂上自己写的模块
                    .addLast(new MsgDecode())
                    .addLast(new clientHandler());//这个是管道自动读取数据的重点任务模块，必须有
            channel = p1.channel();
        }
    }

    private class clientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("客户端接收到新消息: "+msg);
            ((Msg) msg).doIt();
            switch (((Msg) msg).msgType){
                case UUID:
                    //确定所有单例对象已经初始化
                    while (true){
                        if (Client.getClient()==null|| TankFrame.getTankFrame() ==null|| TankFrame.GM==null) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else break;
                    }
                    //更新自己的UUID号
                    Client.uuid=((UUIDMsg)msg).uuid;
                    //更新主战坦克
                    Client.frame.getGM().pushMy();
                    //发送新人上线消息
                    Msg msg1=new JoinMsg();
                    System.out.println("向服务器发送新人上线消息"+msg1);
                    ctx.writeAndFlush(msg1);
                    break;
                case Exit:
                    //删除对应坦克
                    Client.frame.getGM().remove(uuid);
                case Tank:
                    //如果不是自己的坦克，就更新坦克信息，如果没有就添加
                    if (uuid.equals(((TankMsg)msg).uuid)){
                        System.out.println("是自己的Tank，不处理");
                        break;
                    }
                    System.out.println("不是自己的坦克，进行处理中");
                    frame.getGM().pushTank((TankMsg)msg);
                    break;
                case Join:
                    //如果有新人进来上报自己的坦克信息
                    Msg msg2=new TankMsg(frame.getGM().myTank);
                    System.out.println("向服务器发送自己的坦克信息"+msg2);
                    ctx.writeAndFlush(msg2);
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //channel第一次连上可用

        }
    }
}