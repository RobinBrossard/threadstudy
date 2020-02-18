package com.robin.lib;

public class ComputerAI extends PersonalAI implements Runnable {
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    private int x;
    private int y;
    private int speed=minspeed;
    final static int minspeed = 0;
    final static int maxspeed = 10;
    final static int speedstep = 5;
    final static int comDifficult=8;
    private GamePanel gp; //游戏盒子指针，用于数据传递
    private Thread t;

    ComputerAI(GamePanel gp){
        setGp(gp);
        resetComputer();
        if(t==null){
            t=new Thread(this);
            t.start();
        }


    }
    public void resetComputer(){
        setX(GamePanel.playgroundwidth / 2 - GamePanel.playerwidth / 2 - 1);
        setY(GamePanel.bordlinehigh);
    }

    @Override
    public void run() {
        System.out.println("Computer is online.");
        int diffX=0;
        while (true){
            try {
                Thread.sleep(GamePanel.sleeptime*comDifficult); //调整计算机思考时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*机器人思考:
            1. 往球的方向跑
            2. 如果来得及，倾向于用哪个位置击球？
            3. 初级AI：速度调整，始终跟着球跑
            4. 高难度AI：预测球的方向 === 反弹球怎么接，选择让玩家接不到
             */
            diffX=gp.ball.getX()-(x+GamePanel.playerwidth/2);
            if(diffX<0){ //往左跑，调整速度实现
                if((-diffX)>speed&&speed>-maxspeed){
                    speed=speed-speedstep;
                    if(speed<-maxspeed){
                        speed=-maxspeed;
                    }
                }
            }
            if(diffX>0){ //往右跑
                if(diffX>speed&&speed<maxspeed){
                    speed=speed+speedstep;
                    if(speed>maxspeed){
                        speed=maxspeed;
                    }
                }
            }
            diffX=getX()+speed;
            if(diffX<0) {
                diffX=0;
            } else if(diffX+GamePanel.playerwidth>GamePanel.playgroundwidth){
                diffX=GamePanel.playgroundwidth-GamePanel.playerwidth;
            }

            setX(diffX);
            gp.repaint();

        }

    }
}
