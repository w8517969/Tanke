package com.mashibing.tank;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/7 - 08 - 07 - 13:19
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class TankFactory {
    public static List<Tank> getTanks(int quantity){
        List<Tank> tanks=new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            tanks.add(new Tank(i*80, 200,Camp.ENEMY));
        }
        return tanks;
    }
}
