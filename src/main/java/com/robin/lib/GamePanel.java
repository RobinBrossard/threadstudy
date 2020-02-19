package com.robin.lib;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


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
    ComputerAI computer;
    ImageIcon icon;
    Image ballimg, computerimg, playerimg, playgroundimg;


    public GamePanel() throws InterruptedException//构造函数，初始化各个变量
    {
        //设定球场大小
        this.setPreferredSize(new Dimension(playgroundwidth, playgroundhigh));
        this.setLayout(new BorderLayout());

        //初始化小球
        if (ball == null) {
            ball = new Ballclass(this);
        }

        //初始机器人
        if (computer == null) {
            computer = new ComputerAI(this);
        }


        //初始化玩家
        if (player == null) {
            player = new StudentAI(this);
        }

        //读取图像
        System.out.println(System.getProperty("user.dir"));
        icon = new ImageIcon("./src/main/resources/playground.jpg");
        if(icon.getIconWidth()==-1){
            icon = new ImageIcon("playground.jpg");
        }

        playgroundimg = icon.getImage();
        icon = new ImageIcon("./src/main/resources/ball.png");
        if(icon.getIconWidth()==-1){
            icon = new ImageIcon("ball.png");
        }

        ballimg = icon.getImage();


    }

    public void paint(Graphics g) {
        super.paint(g); //调用父类清屏，不然不会清屏
        //画球场
        g.drawImage(playgroundimg, 0, 0, this.getWidth(), this.getHeight(), this);

        //TODO: 画小球
        g.setColor(Color.BLUE);//设置画笔颜色为蓝色
        //  g.fillOval(ball.getX(), ball.getY(), Ballclass.diameter, Ballclass.diameter);//调用画圆的方法绘制小球
        g.drawImage(ballimg, ball.getX(), ball.getY(), Ballclass.diameter, Ballclass.diameter, this);

        //TODO: 画机器人A
        g.setColor(Color.RED);
        g.fillRect(computer.getX(), computer.getY(), playerwidth, playerhigh);

        //TODO: 画玩家
        g.setColor(Color.YELLOW);
        g.fillRect(player.x, player.y, playerwidth, playerhigh);

        //TODO: 显示比分
        Font font = new Font(null, Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.YELLOW);
        g.drawString("Player " + scoreA, playgroundwidth / 2 - 100 - String.valueOf(getScoreA()).length() * 20, playgroundhigh / 2);
        g.setColor(Color.WHITE);
        g.drawString(":", playgroundwidth / 2, playgroundhigh / 2);
        g.setColor(Color.RED);
        g.drawString(scoreB + " Computer", playgroundwidth / 2 + 20, playgroundhigh / 2);

    }

}
