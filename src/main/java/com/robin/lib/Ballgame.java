package com.robin.lib;


import javax.swing.*;
import java.awt.*;


public class Ballgame {
    private static Ballgame instance;

    static {
        try {
            instance = new Ballgame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Ballgame getInstance(){
        return instance;
    }

    private Ballgame() throws InterruptedException {
        JFrame f = new JFrame("PingPong Game");
        f.setLocation(300, 100);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setResizable(false);
        GamePanel mp = new GamePanel();//建立绘图容器，容器开始刷新画图
        f.add(mp, BorderLayout.CENTER);
        f.setVisible(true);
        f.pack();
      //  new Thread(mp).start();

        //增加frame的键盘监听
        f.addKeyListener(mp.player);
        //  mp.addKeyListener(mp.player);

    }
}
