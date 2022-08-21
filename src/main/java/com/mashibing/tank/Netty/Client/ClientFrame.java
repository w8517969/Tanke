package com.mashibing.tank.Netty.Client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/19 - 08 - 19 - 17:41
 * @Description: FrameNettyStudy
 * @version: 1.0
 */
public class ClientFrame extends Frame {
    public TextArea tLook=new TextArea();
    public TextField tin=new TextField();
    public boolean isExit=false;
    ChannelHandlerContext ctx=null;

    public ClientFrame(ChannelHandlerContext ctx) throws HeadlessException {
        this.ctx=ctx;
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.add(tLook,BorderLayout.CENTER);
        this.add(tin,BorderLayout.SOUTH);
        tin.addActionListener(new MyActionListener());
        this.addWindowListener(new WindowAdapter(){//为了关闭窗口
            public void windowClosing(WindowEvent e){
                isExit=true;
                ctx.writeAndFlush("_exit_");
                try {
                    ctx.close().sync();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //这里需要把字符串转发给服务器，在服务器把数据转发给所有的客户端
            ByteBuf buf= Unpooled.copiedBuffer(tin.getText().getBytes());
            ctx.writeAndFlush(buf);
            tin.setText("");
        }
    }
}

