package com.example.myapplication;

public class IotData {
    String xspeed;
    String yspeed;
    String zspeed;
    String angle;
    String point_left_front;
    String point_left_back;
    String point_right_front;
    String point_right_back;

    public IotData(String xspeed, String yspeed, String zspeed,
                   String angle, String point_left_front, String point_left_back,
                   String point_right_front, String point_right_back) {
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        this.zspeed = zspeed;
        this.angle = angle;
        this.point_left_front = point_left_front;
        this.point_left_back = point_left_back;
        this.point_right_front = point_right_front;
        this.point_right_back = point_right_back;
    }

    public IotData() {
    }
}
