package com.robin.lib;

import java.util.Random;

public class Ballclass implements Runnable {
    //球的坐标是由球外切正方形左上角位置决定的

    public final static int diameter = 20;
    final static private int left_limit = 0;
    final static private int right_limit = GamePanel.playgroundwidth - diameter;
    final static private int up_limit = GamePanel.bordlinehigh + GamePanel.playerhigh;
    final static private int down_limit =
            GamePanel.playgroundhigh - GamePanel.bordlinehigh - diameter - GamePanel.playerhigh;

    public int getX() {
        return (int)x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return (int)y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double x; //球的位置左上角横轴left_limit ~ right_Limit
    private double y;//球的位置左上角纵轴，up_limit ~ down_limit


    public void setSigma(float sigma) {
        this.sigma = sigma;
    }

    private float sigma = -90; //球的运动方向，0 ~ 360度，极坐标，初始发球为向正下方
    private static final int minSpeed = 1;
    private static final int maxSpeed = 5;
    private int speed = minSpeed; //球速
    private static final int speedStep = 1;

    private Thread t;
    private GamePanel gp; //游戏盒子指针，用于数据传递

    Random r = new Random();

    public Ballclass(GamePanel tmp) throws InterruptedException {

        gp = tmp;
        r.setSeed(System.currentTimeMillis());
        resetBall();
        //小球开始滚动
        if (t == null) {
            t = new Thread(this);
            t.start();
        }

    }


    public void resetBall() throws InterruptedException {

        setX(GamePanel.playgroundwidth / 2 - diameter / 2);
        setY(GamePanel.bordlinehigh + GamePanel.playerhigh - 1);

        speed = minSpeed;
        this.setSigma(-(r.nextInt(120) + 30));//随机发球

        System.out.println("随机发球，sigma：" + sigma);
        Thread.sleep(GamePanel.sleeptime);

    }

    //球滚动，建立一个线程，一直在运动
    public void roll() throws InterruptedException {
        double dRadians = 0;

        while (true) {

            gp.repaint();
            Thread.sleep(GamePanel.sleeptime);
            dRadians = Math.toRadians(sigma);
            x =  (x + (Math.cos(dRadians) * speed));
            y =  (y - (Math.sin(dRadians) * speed));


            //左碰撞
            if (sigma > 90 || sigma < -90) {
                if (x <= left_limit) {  //碰撞左边了
                    x = left_limit; //超出部分修正
                    //下面再修改运动角度
                    if (sigma > 90) {
                        sigma = sigma + 90 - 180;
                    } else if (sigma < -90) {
                        sigma = -(180 + sigma);
                    }

                    continue;
                }
            }

            //右碰撞
            if (sigma < 90 && sigma > -90) {
                if (x >= right_limit) {  //碰撞右边了
                    x = right_limit; //超出部分修正
                    //下面再修改运动角度
                    if (sigma > 0) {
                        sigma = 180 - sigma;
                    } else if (sigma < 0) {
                        sigma = -sigma - 180;
                    }

                    continue;
                }
            }


            //上碰撞
            if (sigma < 180 && sigma > 0) {
                if (y <= up_limit) {  //碰撞上边了

                    y = up_limit; //超出部分修正
                    //下面再修改运动角度
                    sigma = -sigma;

                    //增加娱乐性，上下碰撞会增加速度，但不超过最大速度
                    if (speed < maxSpeed) {
                        speed = speed + speedStep;
                    }
                    if (speed > maxSpeed) {
                        speed = maxSpeed;
                    }

                    continue;
                }
            }


            //下碰撞
            if (sigma < 0 && sigma > -180) {
                if (y >= down_limit) {  //碰撞下边了

                    y = down_limit; //超出部分修正

                    //下面再修改运动角度
                    //TODO: 需要查看当前玩家位置，根据玩家坐标，判断球出界或者角度转向
                    float ipingpong = 0;
                    ipingpong = pingpongplayer((int)x, gp.player.x, gp.playerwidth, sigma, speed);
                    if (ipingpong > 0) {

                        this.setSigma(ipingpong);
                        System.out.println("接球后sigma：" + sigma);
                    } else { //球没有接到
                        //对方比分+1；
                        gp.setScoreB(gp.getScoreB() + 1);
                        this.resetBall(); //ilock会设为1
                    }

                    continue;
                }
            }

        }

    }

    //如果接到球，返回值一定大于0，是反弹球的角度，否则返回-1，代表没有接到球。
    private float pingpongplayer(int ballx, int playx, int playwidth, float sigmax, int speed) {

        int ballCenterx = ballx + diameter / 2;

        //增加娱乐性，上下碰撞会增加速度，但不超过最大速度
        if (speed < maxSpeed) {
            speed = speed + speedStep;
        }
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }

        float iresult = 0;
        //调整下左边界，增加一个球半径接球距离
        if (ballCenterx >= (playx - diameter / 2) && ballCenterx < (playx + playwidth * 0.1)) {
            //左边角接球
            speed = maxSpeed;
            System.out.println("左边角接球" + gp.player.speed);
            iresult= 135 - gp.player.speed+r.nextInt(30)-15;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;
        }
        if (ballCenterx >= (playx + playwidth * 0.1) && ballCenterx < (playx + playwidth * 0.4)) {
            //左边反弹接球
            System.out.println("左边反弹接球" + gp.player.speed);
            iresult = -sigmax;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;
        }
        if (ballCenterx >= (playx + playwidth * 0.4) && ballCenterx < (playx + playwidth * 0.5)) {
            //中左杀球
            speed = maxSpeed;
            System.out.println("中左杀球" + gp.player.speed);
            iresult= 90 - gp.player.speed+r.nextInt(30)-15;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;
        }
        if (ballCenterx >= (playx + playwidth * 0.5) && ballCenterx < (playx + playwidth * 0.6)) {
            //中右杀球
            speed = maxSpeed;
            System.out.println("中右杀球" + gp.player.speed);
            iresult= 90 + gp.player.speed+r.nextInt(30)-15;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;

        }
        if (ballCenterx >= (playx + playwidth * 0.6) && ballCenterx < (playx + playwidth * 0.9)) {
            //右边反弹接球
            System.out.println("右边反弹接球" + gp.player.speed);
            iresult = -sigmax;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;
        }
        //调整下右边界，增加一个半径接球范围
        if (ballCenterx >= (playx + playwidth * 0.9) && ballCenterx <= (playx + playwidth + diameter / 2)) {
            //右边角接球
            speed = maxSpeed;
            System.out.println("右边角接球" + gp.player.speed);
            iresult= 45 + gp.player.speed+r.nextInt(30)-15;
            if(iresult<=10) iresult=10;
            if(iresult>=170) iresult=170;
            return iresult;
        }

        //没有接住球
        if (iresult == 0) {
            System.out.println("没有接住球");
            iresult = sigmax;
        }


        return iresult;
    }

    @Override
    public void run() {

        try {
            roll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
