package com.mashibing.tank.Netty.Client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/23 - 08 - 23 - 0:50
 * @Description: com.mashibing.tank.Netty.Client
 * @version: 1.0
 */
class ClientTest {

    @Test
    void startUp() {
        Client.getClient().startUp();
    }
}