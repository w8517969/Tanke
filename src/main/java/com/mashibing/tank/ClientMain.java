package com.mashibing.tank;

import com.mashibing.tank.Netty.Client.Client;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/22 - 08 - 22 - 18:13
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class ClientMain {
    public static void main(String[] args) {
        Client.getClient().startUp();
    }
}
