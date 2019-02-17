package com.example.cs478.project4mtgame;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;


public class PlayFragment extends Fragment {

    private MyActionListener mActionListener;
    private View mRootView;

    public interface MyActionListener{
        void startPlay(int winningHole);
        void playNextShot(int player);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mActionListener = (MyActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_playview,
                container, false);

        final Button srtBtn = mRootView.findViewById(R.id.start_game);
        srtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
                srtBtn.setEnabled(false);
            }
        });
        return mRootView;
    }

    public void startNewGame(){
        int winningHole = new Random().nextInt(50);

        TextView whTv = mRootView.findViewById(R.id.wh_tv);
        whTv.setText(String.valueOf(winningHole));
        TextView resultTv = mRootView.findViewById(R.id.result_tv);
        resultTv.setText("");

        TextView player1 = mRootView.findViewById(R.id.player1);
        player1.setTypeface(player1.getTypeface(), Typeface.NORMAL);
        TextView player1HsTv = mRootView.findViewById(R.id.player1_holeshot_tv);
        player1HsTv.setText("");
        TextView player1SsTv = mRootView.findViewById(R.id.player1_shot_selected_tv);
        player1SsTv.setText("");

        TextView player2 = mRootView.findViewById(R.id.player2);
        player2.setTypeface(player2.getTypeface(), Typeface.NORMAL);
        TextView player2HsTv = mRootView.findViewById(R.id.player2_holeshot_tv);
        player2HsTv.setText("");
        TextView player2SsTv = mRootView.findViewById(R.id.player2_shot_selected_tv);
        player2SsTv.setText("");

        mActionListener.startPlay(winningHole);
    }

    public void checkGameStatusAndContinue(int player, Result result, int hole){

        if(player == 1) {
            TextView player1Tv = mRootView.findViewById(R.id.player1_holeshot_tv);
            player1Tv.setText(String.valueOf(hole));
        }else{
            TextView player2Tv = mRootView.findViewById(R.id.player2_holeshot_tv);
            player2Tv.setText(String.valueOf(hole));
        }
        if(result == Result.JACKPOT){
            TextView resultTv = mRootView.findViewById(R.id.result_tv);
            if(player == 1){
                resultTv.setText(R.string.player1_won_jackpot);
                TextView player1 = mRootView.findViewById(R.id.player1);
                player1.setTypeface(player1.getTypeface(), Typeface.BOLD);
            }else{
                resultTv.setText(R.string.player2_won_jackpot);
                TextView player2 = mRootView.findViewById(R.id.player2);
                player2.setTypeface(player2.getTypeface(), Typeface.BOLD);
            }
            Button btn = mRootView.findViewById(R.id.start_game);
            btn.setEnabled(true);
        }else if(result == Result.CATASTROPHE){
            TextView resultTv = mRootView.findViewById(R.id.result_tv);
            if(player == 1){
                resultTv.setText(R.string.player2_won_catastrophe);
                TextView player2 = mRootView.findViewById(R.id.player2);
                player2.setTypeface(player2.getTypeface(), Typeface.BOLD);
            }else{
                resultTv.setText(R.string.player1_won_catastrophe);
                TextView player1 = mRootView.findViewById(R.id.player1);
                player1.setTypeface(player1.getTypeface(), Typeface.BOLD);
            }
            Button btn = mRootView.findViewById(R.id.start_game);
            btn.setEnabled(true);
        }else{
            mActionListener.playNextShot(player);
        }
    }

    public void updateShotSelected(int player, String shotSelected) {
        if(player == 1) {
            TextView player1Tv = mRootView.findViewById(R.id.player1_shot_selected_tv);
            player1Tv.setText(String.valueOf(shotSelected));
        }else{
            TextView player2Tv = mRootView.findViewById(R.id.player2_shot_selected_tv);
            player2Tv.setText(String.valueOf(shotSelected));
        }
    }
}
