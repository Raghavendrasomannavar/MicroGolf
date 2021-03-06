package com.example.cs478.project4mtgame;

import android.content.Context;
import android.os.Message;

public class Player2 extends Player {

    private final int PLAYER = 2;

    public Player2(Context mContext) {
        super(mContext);
    }

    public int sameGroupShot(int previousHole){
        int boundStart = previousHole - previousHole%10;
        return randomInt(boundStart, 10);
    }

    public int nearGroupShot(int previousHole){
        if(previousHole < 10){
            return randomInt(0,20);
        }else if(previousHole >= 40){
            return randomInt(30, 20);
        }else{
            int boundStart = previousHole - previousHole%10 - 10;
            return randomInt(boundStart, 30);
        }
    }

    public int bigMissShot(int previousHole){
        if(previousHole >= 40){
            return randomInt(0,30);
        }else if(previousHole < 10){
            return randomInt(10,30);
        }else{
            int boundStart = previousHole + (10 - previousHole%10);
            return randomInt(boundStart,10);
        }
    }

    public void updateUIShotSelected(){
        Message msg = new Message();
        msg.arg2 = PLAYER;
        msg.obj = shotSelected;
        mainActivity.mHandler.sendMessage(msg);
    }

    public void callBackUIThread() {
        Message msg = new Message();
        msg.arg1 = holeShot;
        msg.arg2 = PLAYER;
        mainActivity.mHandler.sendMessage(msg);
    }
}
