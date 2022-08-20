package com.mashibing.tank;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/6 - 08 - 06 - 15:47
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public enum Dir {
    LEFT,UP,RIGHT,DOWN;

    @Override
    public String toString() {
        String str;
        switch (this){
            case UP:
                str="上";
                break;
            case DOWN:
                str="下";
                break;
            case LEFT:
                str="左";
                break;
            case RIGHT:
                str="右";
                break;
            default:
                str="未知";
        }
        return str;
    }
}
