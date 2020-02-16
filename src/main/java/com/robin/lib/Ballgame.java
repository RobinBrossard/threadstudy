package com.robin.lib;



import javax.swing.*;


public class Ballgame {
    public Ballgame(){
        JFrame f=new JFrame("小球游戏");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Frame f=new Frame("小球游戏");//建立框架
        GamePanel mp=new GamePanel();//建立绘图容器

        f.setLocation(300,100);
        f.setSize(300,300);
        f.add(mp);//将GamePanel对象添加到Frame对象中去
        f.setVisible(true);
        new Thread(mp).start();

    }
}
