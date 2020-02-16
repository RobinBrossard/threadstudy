package com.robin.lib;

public class StudentAI extends PersonalAI implements Runnable{

    @Override
    public void run() {
        System.out.println("I'm a student.");
    }
}
