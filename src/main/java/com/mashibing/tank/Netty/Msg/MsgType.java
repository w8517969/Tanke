package com.mashibing.tank.Netty.Msg;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/21 - 08 - 21 - 12:55
 * @Description: com.mashibing.tank.Netty
 * @version: 1.0
 */
public enum  MsgType{
    Tank,UUID,Exit,Join;
    public String getName(){
        String name=null;
        switch (this){
            case Tank:
                name="Tank";
                break;
            case UUID:
                name="UUID";
                break;
            case Exit:
                name="Exit";
                break;
            case Join:
                name="Join";
                break;
        }
        return name;
    }

    @Override
    public String toString() {
        return "消息枚举："+this.getName()+"Msg";
    }

}
