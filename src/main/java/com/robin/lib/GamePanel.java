package com.robin.lib;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private int x;//该变量表示小球左上角的横坐标
    private int y;//该变量表示小球左上角的纵坐标
    private int diameter;//该变量表示小球的直径
    private int sleeptime;
    public GamePanel()//构造函数，初始化各个变量
    {
        x=10;
        y=10;
        diameter=20;
        sleeptime=30;

    }

    public void paint(Graphics g)
    {
        super.paint(g); //调用父类清屏，不然不会清屏
        g.setColor(Color.BLUE);//设置画笔颜色为蓝色
        g.fillOval(x,y,diameter,diameter);//调用画圆的方法绘制小球

    }

    public void gameLoop() throws InterruptedException//该方法用来动态改变小球的坐标，并对小球进行重绘
    {
        try{
        while(true)
        {
            Thread.sleep(sleeptime);
            repaint();
            x++;y++;
            if(x>300){x=0;}
            if(y>300){y=0;}
        }}
        catch (InterruptedException e){
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
