package com.ankush.takeaway;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter to populate the grid view
 */
public class CustomGridAdapter extends ArrayAdapter<Food>{
    private Context mContext;
    private ArrayList<Food> mFoods;

    public CustomGridAdapter(@NonNull Context context, @NonNull ArrayList<Food> foods) {
        super(context, 0, foods);
        mContext = context;
        mFoods = foods;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data for the current position
        Food currentFood = getItem(position);
        View currentView = convertView;
        if (currentView == null){
            currentView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        }
        ImageView imageView = currentView.findViewById(R.id.iv);
        TextView titleTextView = currentView.findViewById(R.id.tv_title);
        TextView summaryTextView = currentView.findViewById(R.id.tv_summary);

        if (currentFood != null){
            Glide.with(mContext).load(currentFood.getmImageId()).into(imageView);
            titleTextView.setText(currentFood.getmTitle());
            summaryTextView.setText(mContext.getString(R.string.default_summary));
        }

        return currentView;
    }

    @Override
    public int getCount() {
        return mFoods.size();
    }
}