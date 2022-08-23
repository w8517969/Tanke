package com.mashibing.tank.Netty.Server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/23 - 08 - 23 - 0:50
 * @Description: com.mashibing.tank.Netty.Server
 * @version: 1.0
 */
class ServerTest {

    @Test
    void startUp() {
        new Server().startUp();
    }
}