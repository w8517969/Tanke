package com.mashibing.tank.Netty.Msg;

import java.io.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/21 - 08 - 21 - 15:59
 * @Description: com.mashibing.tank.Netty
 * @version: 1.0
 */
public abstract class Msg {
    public MsgType msgType;//消息类型
    public abstract void doIt();
    protected abstract void toBytesWork(DataOutputStream dos) throws IOException;
    protected abstract void byteComeWork(DataInputStream dis) throws IOException;
    public  byte[] toBytes(){
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        baos=new ByteArrayOutputStream();
        dos=new DataOutputStream(baos);
        byte[] bytes;
        try {
            toBytesWork(dos);
            dos.flush();
            bytes=baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bytes=baos.toByteArray();
        return bytes;
    };
    public  void byteCome(byte[] bytes){
        ByteArrayInputStream bais=null;
        DataInputStream dis=null;
        bais=new ByteArrayInputStream(bytes);
        dis=new DataInputStream(bais);
        //略过消息类型和包长度
        try {
            byteComeWork(dis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
