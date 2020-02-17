package com.robin.lib;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class StudentAI extends PersonalAI implements KeyListener {
    Thread t;
    final static int minspeed = 0;
    final static int maxspeed = 10;
    final static int speedstep = 5;
    int speed = minspeed;

    public StudentAI() {
        resetStudent();
    }

    void resetStudent() {
        this.setX(GamePanel.playgroundwidth / 2 - GamePanel.playerwidth / 2);
        this.setY(GamePanel.playgroundhigh - GamePanel.bordlinehigh - GamePanel.playerhigh);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == 39) {
            if (speed < 0) {
                speed = 0;
            }
            if (x + speed > GamePanel.playgroundwidth - GamePanel.playerwidth) {
                this.setX(GamePanel.playgroundwidth - GamePanel.playerwidth);

            } else {
                this.setX(x + speed);
            }
            if (speed < maxspeed) {
                speed = speed + speedstep;
            }

        }

        if (e.getKeyCode() == 37) {
            if (speed > 0) {
                speed = 0;
            }
            if (x + speed < 0) {
                this.setX(0);
            } else {
                this.setX(x + speed);
            }
            if (speed > -maxspeed) {
                speed = speed - speedstep;
            }
        }


        //  Thread.sleep(GamePanel.sleeptime);


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
