package com.example.cs478.project4mtgame;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;

public class MainActivity extends AppCompatActivity implements PlayFragment.MyActionListener{

    public Handler mHandler;
    private int winningHole;
    private Player1 player1;
    private Player2 player2;
    private Result result;
    private int previousHole;
    private PlayFragment mPlayFragment;
    private HoleListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mPlayFragment = new PlayFragment();
        mListFragment = new HoleListFragment();
        fragmentTransaction.add(R.id.list_frame, mListFragment);
        fragmentTransaction.add(R.id.play_frame, mPlayFragment);
        fragmentTransaction.commit();

        mHandler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {

                int hole = msg.arg1;
                int player = msg.arg2;
                String shotSelected = (String)msg.obj;

                if(null == shotSelected){
                    updateResult(hole);
                    mPlayFragment.checkGameStatusAndContinue(player, result, previousHole);
                }else{
                    mPlayFragment.updateShotSelected(player, shotSelected);
                }
            }
        };

    }

    @Override
    public void onStop(){
        super.onStop();
        player1.interrupt();
        player2.interrupt();
    }

    @Override
    public void startPlay(int winningHole){

        this.winningHole = winningHole;
        this.previousHole = -1;
        mListFragment.loadHoleData();
        Hole hole = mListFragment.getHoleList().get(winningHole);
        hole.setImage(R.drawable.win);
        mListFragment.getListView().smoothScrollToPosition(winningHole);
        ((BaseAdapter) mListFragment.getListView().getAdapter()).notifyDataSetChanged();

        player1 = new Player1(this);
        player2 = new Player2(this);
        player1.start();
        player2.start();
        try {
            Thread.sleep(1000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        player1.mHandler.post(new Runnable() {
            @Override
            public void run() {
                player1.playNextShot();
            }
        });
    }

    public void updateResult(int hole) {

        if (winningHole == hole) {
            result = Result.JACKPOT;
            player1.interrupt();
            player2.interrupt();
        }
        else if(hole == previousHole) {
            result = Result.CATASTROPHE;
            player1.interrupt();
            player2.interrupt();
        }
        else{
            int diff = Math.abs(winningHole/10 - hole/10);
            if(diff == 0)
                result = Result.NEAR_MISS;
            else if(diff == 1)
                result = Result.NEAR_GROUP;
            else
                result = Result.BIG_MISS;
        }

        previousHole = hole;
    }

    public void playNextShot(int player){

        Hole hole = mListFragment.getHoleList().get(previousHole);
        if(player == 1){
            hole.setImage(R.drawable.player1);
            mListFragment.getListView().smoothScrollToPosition(previousHole);
            ((BaseAdapter) mListFragment.getListView().getAdapter()).notifyDataSetChanged();
            player1.setResult(result);
            player2.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    player2.playNextShot();
                }
            });
        }else{
            hole.setImage(R.drawable.player2);
            mListFragment.getListView().smoothScrollToPosition(previousHole);
            ((BaseAdapter) mListFragment.getListView().getAdapter()).notifyDataSetChanged();
            player2.setResult(result);
            player1.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    player1.playNextShot();
                }
            });
        }
    }
}
