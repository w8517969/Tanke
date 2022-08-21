package com.mashibing.tank.resourceMgr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/7 - 08 - 07 - 1:36
 * @Description: com.mashibing.tank.resourceMgr
 * @version: 1.0
 */
public class ResourceMgr {
    //资源管理器
    public static BufferedImage tankU,tankD,tankL,tankR,bandTankU,bandTankD,bandTankL,bandTankR,bulletU,bulletD,bulletL,bulletR;
    public static BufferedImage[] explodes=new BufferedImage[16];
    static {
        //导入需要的图片
        try {
            bandTankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            bulletU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            //我方坦克
            tankD=ImageUtil.rotateImage(tankU, 180);
            tankL=ImageUtil.rotateImage(tankU, -90);
            tankR=ImageUtil.rotateImage(tankU, 90);
            //敌方坦克
            bandTankD=ImageUtil.rotateImage(bandTankU, 180);
            bandTankL=ImageUtil.rotateImage(bandTankU, -90);
            bandTankR=ImageUtil.rotateImage(bandTankU, 90);
            //炮弹
            bulletD=ImageUtil.rotateImage(bulletU, 180);
            bulletL=ImageUtil.rotateImage(bulletU, -90);
            bulletR=ImageUtil.rotateImage(bulletU, 90);
            //爆炸
            for (int i = 0; i < 16; i++) {
                explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream(
                            "images/e" +(i+1)+".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
