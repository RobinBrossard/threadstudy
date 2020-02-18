package com.robin.lib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable {


    private int PlayA_x; //机器人左上角横坐标
    private int PlayA_y; //机器人左上角纵坐标


    public void setPlayA_x(int playA_x) {
        PlayA_x = playA_x;
    }


    final public static int playerwidth = 80;
    public static final int playerhigh = 5;

    final public static int bordlinehigh = 20;
    final static public int playgroundwidth = 500;
    final public static int playgroundhigh = 300;
    final public static int sleeptime = 10;

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public int getScoreA() {
        return scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    private int scoreA = 0;
    private int scoreB = 0;

    public Ballclass getBall() {
        return ball;
    }

    public StudentAI getPlayer() {
        return player;
    }

    Ballclass ball;
    StudentAI player;


    public GamePanel() throws InterruptedException//构造函数，初始化各个变量
    {
        //设定球场大小
        this.setPreferredSize(new Dimension(playgroundwidth, playgroundhigh));
        this.setLayout(new BorderLayout());

        //初始化小球
        if (ball == null) {
            ball = new Ballclass(this);
        }

        //初始位置，机器人
        PlayA_x = playgroundwidth / 2 - playerwidth / 2 - 1;
        PlayA_y = bordlinehigh - 1;

        //初始化玩家
        if (player == null) {
            player = new StudentAI(this);

        }


    }

    public void paint(Graphics g) {
        super.paint(g); //调用父类清屏，不然不会清屏
        //TODO: 画小球
        g.setColor(Color.BLUE);//设置画笔颜色为蓝色
        //小球轨迹计算中，会锁住

           g.fillOval(ball.getX(), ball.getY(), Ballclass.diameter, Ballclass.diameter);//调用画圆的方法绘制小球

        //TODO: 画机器人A
        g.setColor(Color.RED);
        g.fillRect(PlayA_x, PlayA_y, playerwidth, playerhigh);

        //TODO: 画玩家
        g.setColor(Color.YELLOW);
        g.fillRect(player.x, player.y, playerwidth, playerhigh);

        //TODO: 显示比分
        g.setColor(Color.WHITE);
        Font font = new Font(null, Font.BOLD, 30);
        g.setFont(font);
        g.drawString(scoreA + ":" + scoreB, playgroundwidth / 2 - 20, playgroundhigh / 2);

    }

    public  void gameLoop() throws InterruptedException//该方法用来动态改变小球的坐标，并对小球进行重绘
    {
        try {
            while (true) {
                Thread.sleep(sleeptime);
                repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            gameLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
