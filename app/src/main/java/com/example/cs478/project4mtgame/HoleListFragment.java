package com.example.cs478.project4mtgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;


public class HoleListFragment extends ListFragment{

    private ArrayList<Hole> holeList = new ArrayList<>();

    public ArrayList<Hole> getHoleList() {
        return holeList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadHoleData();
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setListAdapter(new ImageAdapter(getActivity(), holeList));
        getListView().setDivider(null);
    }

    public void loadHoleData(){
        holeList.clear();
        int i = 0;
        while(i<50){
            Hole hole = new Hole(i,R.drawable.hole,i/10);
            holeList.add(hole);
            i++;
        }
    }
}