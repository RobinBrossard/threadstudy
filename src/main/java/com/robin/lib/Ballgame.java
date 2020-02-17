package com.robin.lib;


import javax.swing.*;
import java.awt.*;


public class Ballgame {
    public Ballgame() {
        JFrame f = new JFrame("小球游戏");
        f.setLocation(300, 100);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setResizable(false);
        GamePanel mp = new GamePanel();//建立绘图容器，容器开始刷新画图
        f.add(mp, BorderLayout.CENTER);
        f.setVisible(true);
        f.pack();
        new Thread(mp).start();

        //增加frame的键盘监听
        f.addKeyListener(mp.player);
        //  mp.addKeyListener(mp.player);

    }
}
