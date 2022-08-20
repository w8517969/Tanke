package com.mashibing.coordinate;

import com.mashibing.tank.Dir;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/6 - 08 - 06 - 18:30
 * @Description: com.mashibing.coordinate
 * @version: 1.0
 */
public class Coordinate {
    //坐标类
    public int x,y;
    //构造
    public Coordinate(Coordinate coordinate) {
        this.x=coordinate.x;
        this.y=coordinate.y;
    }
    public Coordinate() {
    }
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //计算坐标点相对与自己的方向，重合默认为上
    public Dir dirOfMe(Coordinate there){
        return dirOfMe(there.x,there.y);
    }
    public Dir dirOfMe(int x1,int y1){
        double angle=angleOfMe(x1, y1);
        Dir dir=Dir.UP;//默认为上
        if (angle>=-45&&angle<45) {//上
        }else if (angle>=45&&angle<135) {//右
            dir=Dir.RIGHT;
        }else if ((angle>=135&&angle<=180)||(angle<-135&&angle>=-180)){//下
            dir=Dir.DOWN;
        }else if (angle>=-135&&angle<-45){//左
            dir=Dir.LEFT;
        }
        return dir;
    }
    //计算坐标点相对于自己的角度，重合默认为0度
    public double angleOfMe(int x1,int y1){
        int julix = Math.abs(x-x1);
        int juliy =Math.abs(y-y1);
        double angle=0;//默认角度为0
        //判断两点是否水平或垂直
        if (julix==0){//垂直
            //正上和重合为默认
            if (y<y1){//正下
                angle=180;
            }
        }else if (juliy==0){//水平
            if (x<x1){//正右
                angle=90;
            }else if (x>x1){//正左
                angle= -90;
            }
        }
        if (julix==0||juliy==0){return angle;}
        //进行角度计算
        angle=Math.atan(julix/juliy) * 180 / Math.PI;//默认为右上
        //相对在右半部分
        if (x<x1){
            //右上已经默认计算过
            //右下
            if (y<y1){
                angle = 180-angle;
            }
            //相对在左半部分
        }else if (x>x1){
            //左上
            if (y>y1){
                angle = -angle;
                //左下
            }else if (y<y1){
                angle -= 180;
            }
        }
        return angle;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
