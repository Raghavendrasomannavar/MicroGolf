package com.example.cs478.project4mtgame;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player extends Thread {

    ArrayList<Integer> shotHoles = new ArrayList<>();
    int holeShot;
    Result result;
    MainActivity mainActivity;
    public Handler mHandler;
    String shotSelected;
    private final String SAME_GROUP = "Same Group";
    private final String CLOSE_GROUP = "Close Group";
    private final String TARGET_HOLE = "Target Hole";
    private final String RANDOM = "Random";

    public Player(Context mContext){
        this.mainActivity = (MainActivity)mContext;
    }

    public  void setResult(Result result){
        this.result = result;
    }

    public abstract int sameGroupShot(int previousHole);

    public abstract int nearGroupShot(int previousHole);

    public abstract int bigMissShot(int previousHole);

    public abstract void updateUIShotSelected();

    public abstract void callBackUIThread();

    @Override
    public void run(){
        Looper.prepare();
        mHandler = new Handler();
        Looper.loop();
    }

    public void playNextShot(){

        selectNextShot();
        try {
            Thread.sleep(2000);
            updateUIShotSelected();
            Thread.sleep(2500);
        }catch (Exception ex){
            return;
        }
        callBackUIThread();
    }

    public void selectNextShot() {
        if(result != null){
            switch (result){
                case NEAR_MISS:{
                    this.shotSelected = SAME_GROUP;
                    holeShot = sameGroupShot(holeShot);
                    break;
                }case NEAR_GROUP:{
                    this.shotSelected = CLOSE_GROUP;
                    holeShot = nearGroupShot(holeShot);
                    break;
                }case BIG_MISS:{
                    this.shotSelected = TARGET_HOLE;
                    holeShot = bigMissShot(holeShot);
                    break;
                }default:{
                    this.shotSelected = RANDOM;
                    holeShot = randomShot();
                    break;
                }
            }
        }else{
            this.shotSelected = RANDOM;
            holeShot = randomShot();
        }

        shotHoles.add(holeShot);
    }

    public int randomShot(){
        return randomInt(0,50);
    }

    public int randomInt(int start, int bound){
        int random;
        do{
            random =  start + new Random().nextInt(bound);
        }while(shotHoles.contains(random));
        return random;
    }
}