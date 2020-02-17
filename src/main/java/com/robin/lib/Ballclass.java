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

    public int x; //球的位置左上角横轴left_limit ~ right_Limit
    public int y;//球的位置左上角纵轴，up_limit ~ down_limit


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
    public static int ilock = 1;


    public Ballclass(GamePanel tmp) throws InterruptedException {

        gp = tmp;
        resetBall();
        //小球开始滚动
        if (t == null) {
            t = new Thread(this);
            t.start();
        }

    }


    public void resetBall() throws InterruptedException {
        ilock=0;
        x = GamePanel.playgroundwidth / 2 - diameter / 2;
        y = GamePanel.bordlinehigh + GamePanel.playerhigh - 1;
        speed = minSpeed;
        Random r=new Random();
        r.setSeed(System.currentTimeMillis());
        sigma=-(r.nextInt(120)+30); //随机发球
        Thread.sleep(GamePanel.sleeptime);
        ilock=1;
    }

    //球滚动，建立一个线程，一直在运动
    public void roll() throws InterruptedException {
        double dRadians = 0;
        int x1 = 0;
        int y1 = 0;
        while (ilock == 1) {
            ilock = 0;
            dRadians = Math.toRadians(sigma);
            x1 = (int) (x + Math.round(Math.cos(dRadians) * speed));
            y1 = (int) (y - Math.round(Math.sin(dRadians) * speed));


            //跟踪一下
            System.out.println("球的速度是:" + speed);
            System.out.println("球的行进角度是：" + sigma);
            System.out.println("球的当前位置是：X=" + x + "; " + "Y=" + y);


            //左碰撞
            if (sigma > 90 || sigma < -90) {
                if (x1 <= left_limit) {  //碰撞左边了
                    x = left_limit; //x修正
                    x1 = x;
                    //下面再修改运动角度
                    if (sigma > 90) {
                        sigma = sigma + 90 - 180;
                    } else if (sigma < -90) {
                        sigma = -(180 + sigma);
                    }
                }
            }

            //右碰撞
            if (sigma < 90 && sigma > -90) {
                if (x1 >= right_limit) {  //碰撞右边了
                    x = right_limit; //x修正
                    x1 = x;
                    //下面再修改运动角度
                    if (sigma > 0) {
                        sigma = 180 - sigma;
                    } else if (sigma < 0) {
                        sigma = -sigma - 180;
                    }
                }
            }


            //上碰撞
            if (sigma < 180 && sigma > 0) {
                if (y1 <= up_limit) {  //碰撞上边了
                    y = up_limit; //y修正
                    y1 = up_limit;
                    //下面再修改运动角度
                    sigma = -sigma;

                    //增加娱乐性，上下碰撞会增加速度，但不超过最大速度
                    if (speed < maxSpeed) {
                        speed = speed + speedStep;
                    }
                    if (speed > maxSpeed) {
                        speed = maxSpeed;
                    }


                }
            }


            //下碰撞
            if (sigma < 0 && sigma > -180) {
                if (y1 >= down_limit) {  //碰撞下边了
                    y = down_limit; //y修正
                    y1 = y;

                    //下面再修改运动角度
                    //   sigma = -sigma;

                    //TODO: 需要查看当前玩家位置，根据玩家坐标，判断球出界或者角度转向
                    float ipingpong = 0;
                    ipingpong = pingpongplayer(x, gp.player.x, gp.playerwidth, sigma, speed);
                    if (ipingpong > 0) {
                        sigma = ipingpong;
                    } else { //球没有接到
                        //对方比分+1；
                        gp.setScoreB(gp.getScoreB() + 1);
                        this.resetBall(); //ilock会设为1
                    }


                    //增加娱乐性，上下碰撞会增加速度，但不超过最大速度
                    if (speed < maxSpeed) {
                        speed = speed + speedStep;
                    }
                    if (speed > maxSpeed) {
                        speed = maxSpeed;
                    }

                }
            }


            //不碰撞，角度不变，原轨迹前进
            if (ilock == 0) {
                x = x1;
                y = y1;
                ilock = 1;
            }
            Thread.sleep(GamePanel.sleeptime);
        }

    }

    //如果接到球，返回值一定大于0，是反弹球的角度，否则返回-1，代表没有接到球。
    private float pingpongplayer(int ballx, int playx, int playwidth, float sigmax, int speed) {

        int ballcenterx = ballx + diameter / 2;

        if (ballcenterx > playx && ballcenterx <= (playx + playwidth * 0.1)) {
            //左边角接球
            speed = maxSpeed;
            return 135;
        }
        if (ballcenterx > (playx + playwidth * 0.1) && ballcenterx <= (playx + playwidth * 0.4)) {
            //左边反弹接球
            return -sigmax;
        }
        if (ballcenterx > (playx + playwidth * 0.4) && ballcenterx < (playx + playwidth * 0.6)) {
            //中间杀球
            speed = maxSpeed;
            return 90;
        }
        if (ballcenterx >= (playx + playwidth * 0.6) && ballcenterx < (playx + playwidth * 0.9)) {
            //右边反弹接球
            return -sigmax;
        }
        if (ballcenterx >= (playx + playwidth * 0.9) && ballcenterx <= (playx + playwidth)) {
            //右边角接球
            speed = maxSpeed;
            return 45;
        }

        //没有接住球

        return sigmax;
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
