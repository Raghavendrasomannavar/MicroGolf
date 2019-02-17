package com.example.cs478.project4mtgame;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Hole> mHoleList;   // Adapter must store AdapterView's items

    // Save the list of image IDs and the context
    public ImageAdapter(Context c, List<Hole> holeList) {
        mContext = c;
        this.mHoleList = holeList;
    }

    // Now the methods inherited from abstract superclass BaseAdapter

    // Return the number of items in the Adapter
    @Override
    public int getCount() {
        return mHoleList.size();
    }

    // Return the data item at position
    @Override
    public Object getItem(int position) {
        return mHoleList.get(position);
    }

    // Will get called to provide the ID that
    // is passed to OnItemClickListener.onItemClick()
    @Override
    public long getItemId(int position) {
        return mHoleList.get(position).getHoleNumber();
    }

    // Return an ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) convertView;

        // if convertView's not recycled, initialize some attributes
        if (textView == null) {
            LayoutInflater layoutInflater = ((Activity)mContext).getLayoutInflater();
            textView = (TextView)layoutInflater.inflate(R.layout.fragment_holelist, parent, false);
        }
        textView.setBackgroundResource(mHoleList.get(position).getImage());
        //textView.setTextColor(ContextCompat.getColor(mContext, mHoleList.get(position).getImage()));
        textView.setText(String.valueOf(mHoleList.get(position).getHoleNumber()));
        return textView;
    }
}
