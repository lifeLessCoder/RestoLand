package com.ankush.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Views in the layout
    private GridView mGridView;
    private FloatingActionButton mFAB;
    private FrameLayout mContainer;
    // parameters for moving around FAB
    private int _xDelta;
    private int _yDelta;
    // Coordinated of the FAC
    int[] mCoordinates = new int[2];
    // Request codes for each order activity
    private final int COFFEE_REQUEST_CODE = 1;
    private final int DESSERT_REQUEST_CODE = 2;
    private final int ICECREAM_REQUEST_CODE = 3;
    private final int SNACKS_REQUEST_CODE = 4;

    // Summary for each card
    private String mSummaryCoffee;
    private String mSummaryDessert;
    private String mSummaryIceCream;
    private String mSummarySnacks;
    // Hold total price for each category
    int[] priceTotal = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restoring the state
        if (savedInstanceState != null){
            TextView coffeeSummaryTextView = mGridView.getChildAt(0).findViewById(R.id.tv_summary);
            coffeeSummaryTextView.setText(savedInstanceState.getString(getString(R.string.summary_coffee)));
            TextView dessertSummaryTextView = mGridView.getChildAt(1).findViewById(R.id.tv_summary);
            dessertSummaryTextView.setText(savedInstanceState.getString(getString(R.string.summary_dessert)));
            TextView iceCreamSummaryTextView = mGridView.getChildAt(2).findViewById(R.id.tv_summary);
            iceCreamSummaryTextView.setText(savedInstanceState.getString(getString(R.string.summary_ice_cream)));
            TextView snacksTextView = mGridView.getChildAt(3).findViewById(R.id.tv_summary);
            snacksTextView.setText(savedInstanceState.getString(getString(R.string.summary_snacks)));
        }
        // Data source for the CustomGridAdapter
        ArrayList<Food> foods = new ArrayList<>(4);
        foods.add(new Food(R.drawable.coffee, R.color.coffee_color, getString(R.string.coffee)));
        foods.add(new Food(R.drawable.dessert, R.color.dessert_color, getString(R.string.dessert)));
        foods.add(new Food(R.drawable.icecream, R.color.icecream_color, getString(R.string.ice_cream)));
        foods.add(new Food(R.drawable.snacks, R.color.snacks_color, getString(R.string.snacks)));
        // Handles to the views
        mGridView = findViewById(R.id.gv);
        mFAB = findViewById(R.id.fab);
        mContainer = findViewById(R.id.container);
        // OnTouchListener for moving around the FAB
        mFAB.setOnTouchListener(new ChoiceTouchListener());
        // Creating and setting the adapter on the GridView
        CustomGridAdapter customGridAdapter = new CustomGridAdapter(this, foods);
        mGridView.setAdapter(customGridAdapter);
    }

    // Executed when order button is clicked
    public void order(View view) {
        int totalOrderPrice = 0;
        for (int i = 0; i < 4; i++) {
            totalOrderPrice += priceTotal[i];
        }
        String summary = mSummaryCoffee + "\n"
                + mSummaryDessert + "\n"
                + mSummaryIceCream + "\n"
                + mSummarySnacks + "\n"
                + "Total Order Price: ₹" + totalOrderPrice;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + getString(R.string.my_email)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, summary);
        if (emailIntent.resolveActivity(getPackageManager()) != null){
            startActivity(emailIntent);
        }

    }

    // Custom OnTouchListener for the FAB
    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int X = (int) motionEvent.getRawX();
            final int Y = (int) motionEvent.getRawY();
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - params.leftMargin;
                    _yDelta = Y - params.topMargin;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    view.setLayoutParams(layoutParams);
                    break;
                case MotionEvent.ACTION_UP:
                    startChoosingActivity();
            }
            mContainer.invalidate();
            return true;
        }

    }


    // starts a specific order activity depending on the new location of the FAB
    private void startChoosingActivity() {
        mFAB.getLocationOnScreen(mCoordinates);
        // Get the coordinates of the FAB
        int x = mCoordinates[0];
        int y = mCoordinates[1];
        Log.e("MainActivity", mCoordinates[0] + " " + mCoordinates[1]);
        if (x < 304 && y < 616) {
            startActivityForResult(new Intent(this, CoffeeActivity.class), COFFEE_REQUEST_CODE);
        } else if (x > 304 && y < 616) {
            startActivityForResult(new Intent(this, DessertActivity.class), DESSERT_REQUEST_CODE);
        } else if (x < 304 && y > 616) {
            startActivityForResult(new Intent(this, IceCreamActivity.class), ICECREAM_REQUEST_CODE);
        } else if (x > 304 && y > 616) {
            startActivityForResult(new Intent(this, SnacksActivity.class), SNACKS_REQUEST_CODE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set the FAB to the center of the screen every time the activity starts
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFAB.getLayoutParams();
        params.leftMargin = 0;
        params.topMargin = 0;
        mFAB.setLayoutParams(params);

    }

    // Get the results by starting various order activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                mofifyGridData(requestCode, data);
            }
    }

    // Modify the GridItem for each order received in the order activity
    private void mofifyGridData(int requestCode, Intent data) {
        int price1;
        int price2;
        int quantity1;
        int quantity2;
        boolean isCb1Card1Checked;
        boolean isCb2Card1Checked;
        boolean isCb1Card2Checked;
        boolean isCb2Card2Checked;
        View rootView;
        TextView summaryTextView;
        quantity1 = data.getIntExtra(getString(R.string.quantity_key_1),0);
        Log.e("MainActivity", quantity1 + "");
        quantity2 = data.getIntExtra(getString(R.string.quantity_key_2), 0);
        isCb1Card1Checked = data.getBooleanExtra(getString(R.string.cb1_key1), false);
        Log.e("MainActivity", isCb1Card1Checked + "");
        isCb2Card1Checked = data.getBooleanExtra(getString(R.string.cb2_key1), false);
        isCb1Card2Checked = data.getBooleanExtra(getString(R.string.cb1_key2),false);
        isCb2Card2Checked = data.getBooleanExtra(getString(R.string.cb2_key2), false);

        switch (requestCode) {
            case COFFEE_REQUEST_CODE:
                price1 = calculatePrice1(isCb1Card1Checked, isCb2Card1Checked, quantity1, requestCode);
                price2 = calculatePrice2(isCb1Card2Checked, isCb2Card2Checked, quantity2, requestCode);
                priceTotal[0] = price1 + price2;
                rootView = mGridView.getChildAt(0);
                summaryTextView = rootView.findViewById(R.id.tv_summary);
                mSummaryCoffee = quantity1 + " Latte: ₹" + price1 + "\n"
                        + quantity2 + " Espresso: ₹" + price2;
                summaryTextView.setText(mSummaryCoffee);
                break;
            case DESSERT_REQUEST_CODE:
                price1 = calculatePrice1(isCb1Card1Checked, isCb2Card1Checked, quantity1, requestCode);
                price2 = calculatePrice2(isCb1Card2Checked, isCb2Card2Checked, quantity2, requestCode);
                priceTotal[1] = price1 + price2;
                rootView = mGridView.getChildAt(1);
                summaryTextView = rootView.findViewById(R.id.tv_summary);
                mSummaryDessert = quantity1 + " Pancake: ₹" + price1 + "\n"
                        + quantity2 + " Strawberry Roll: ₹" + price2;
                summaryTextView.setText(mSummaryDessert);
                break;
            case ICECREAM_REQUEST_CODE:
                price1 = calculatePrice1(isCb1Card1Checked, isCb2Card1Checked, quantity1, requestCode);
                price2 = calculatePrice2(isCb1Card2Checked, isCb2Card2Checked, quantity2, requestCode);
                priceTotal[2] = price1 + price2;
                rootView = mGridView.getChildAt(2);
                summaryTextView = rootView.findViewById(R.id.tv_summary);
                mSummaryIceCream = quantity1 + " Sundae: ₹" + price1 + "\n"
                        + quantity2 + " Oreo Ice Cream: ₹" + price2;
                summaryTextView.setText(mSummaryIceCream);
                break;
            case SNACKS_REQUEST_CODE:
                price1 = calculatePrice1(isCb1Card1Checked, isCb2Card1Checked, quantity1, requestCode);
                price2 = calculatePrice2(isCb1Card2Checked, isCb2Card2Checked, quantity2, requestCode);
                priceTotal[3] = price1 + price2;
                rootView = mGridView.getChildAt(3);
                summaryTextView = rootView.findViewById(R.id.tv_summary);
                mSummarySnacks = quantity1 + " Burger: ₹" + price1 + "\n"
                        + quantity2 + " Pizza: ₹" + price2;
                summaryTextView.setText(mSummarySnacks);
                break;
        }

    }

    // Calculates the total price for the 2nd card in order activity
    private int calculatePrice2(boolean isCb1Card2Checked, boolean isCb2Card2Checked, int quantity2,
                                int requestCode) {
        int price2 = 0;
        switch (requestCode){
            case COFFEE_REQUEST_CODE:
                price2 = 50;
                if (isCb1Card2Checked) {
                    price2 += 10;
                }
                if (isCb2Card2Checked) {
                    price2 += 15;
                }
                break;
            case DESSERT_REQUEST_CODE:
                price2 = 60;
                if (isCb1Card2Checked) {
                    price2 += 25;
                }
                if (isCb2Card2Checked) {
                    price2 += 15;
                }
                break;
            case ICECREAM_REQUEST_CODE:
                price2 = 100;
                if (isCb1Card2Checked) {
                    price2 += 20;
                }
                if (isCb2Card2Checked) {
                    price2 += 35;
                }
                break;
            case SNACKS_REQUEST_CODE:
                price2 = 120;
                if (isCb1Card2Checked) {
                    price2 += 25;
                }
                if (isCb2Card2Checked) {
                    price2 += 50;
                }
                break;
        }

        return price2 * quantity2;
    }

    // Calculates the total price for 1st card in order activity
    private int calculatePrice1(boolean isCb1Card1Checked, boolean isCb2Card1Checked, int quantity1,
                                int requestCode) {
        int price1 = 0;
        switch (requestCode){
            case COFFEE_REQUEST_CODE:
                price1 = 30;
                if (isCb1Card1Checked) {
                    price1 += 10;
                }
                if (isCb2Card1Checked) {
                    price1 += 15;
                }
                break;
            case DESSERT_REQUEST_CODE:
                price1 = 80;
                if (isCb1Card1Checked) {
                    price1 += 20;
                }
                if (isCb2Card1Checked) {
                    price1 += 30;
                }
                break;
            case ICECREAM_REQUEST_CODE:
                price1 = 90;
                if (isCb1Card1Checked) {
                    price1 += 25;
                }
                if (isCb2Card1Checked) {
                    price1 += 20;
                }
                break;
            case SNACKS_REQUEST_CODE:
                price1 = 90;
                if (isCb1Card1Checked) {
                    price1 += 20;
                }
                if (isCb2Card1Checked) {
                    price1 += 15;
                }
                break;
        }

        return price1 * quantity1;
    }

    // Saving the state of the activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.summary_coffee), mSummaryCoffee);
        outState.putString(getString(R.string.summary_dessert), mSummaryDessert);
        outState.putString(getString(R.string.summary_ice_cream), mSummaryIceCream);
        outState.putString(getString(R.string.summary_snacks), mSummarySnacks);
    }
}
