package com.ankush.takeaway;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private FloatingActionButton mFAB;
    private FrameLayout mContainer;
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Food> foods = new ArrayList<>(4);
        foods.add(new Food(R.drawable.coffee, getString(R.string.coffee), ""));
        foods.add(new Food(R.drawable.dessert, getString(R.string.dessert), ""));
        foods.add(new Food(R.drawable.icecream, getString(R.string.ice_cream), ""));
        foods.add(new Food(R.drawable.snacks, getString(R.string.snacks), ""));

        mGridView = findViewById(R.id.gv);
        mFAB = findViewById(R.id.fab);
        mContainer = findViewById(R.id.container);

        mFAB.setOnTouchListener(new ChoiceTouchListener());
        mGridView.setAdapter(new CustomGridAdapter(this, foods));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, CoffeeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, DessertActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, IceCreamActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SnacksActivity.class));
                        break;
                }
            }
        });

    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int X = (int)motionEvent.getRawX();
            final int Y = (int)motionEvent.getRawY();
            switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - params.leftMargin;
                    _yDelta = Y - params.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            mContainer.invalidate();
            return true;
        }
    }
}
