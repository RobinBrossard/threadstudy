package com.robin.lib;

public class ComputerAI extends PersonalAI implements Runnable {
    private int x;
    private int y;
    private int speed=minspeed;
    final static int minspeed = 0;
    final static int maxspeed = 10;
    final static int speedstep = 5;
    private GamePanel gp; //游戏盒子指针，用于数据传递

    ComputerAI(){

    }

    @Override
    public void run() {

    }
}
