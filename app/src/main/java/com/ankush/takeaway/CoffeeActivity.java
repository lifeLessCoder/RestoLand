package com.ankush.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class CoffeeActivity extends AppCompatActivity {
    // Card 1
    private ImageView imageViewCard1;
    private CheckBox checkBox1Card1;
    private CheckBox checkBox2Card1;
    private StepperTouch stepperTouch1;
    private TextView basePriceTextViewCard1;
    private TextView foodItemTextViewCard1;
    // Card 2
    private ImageView imageViewCard2;
    private CheckBox checkBox1Card2;
    private CheckBox checkBox2Card2;
    private StepperTouch stepperTouch2;
    private TextView basePriceTextViewCard2;
    private TextView foodItemTextViewCard2;

    // Base price of latte
    private int latteBasePrice;
    // Base price of espresso
    private int espressoBasePrice;
    // Cost of whipped cream
    private int whippedCreamPrice;
    // Cost of chocolate
    private int chocoPrice;

    // Latte quantity
    private int quantityLatte = 0;
    // Espresso quantity
    private int quantityEspresso = 0;

    // See if whipped cream is added for latte
    private boolean isWhippedCreamAddedLatte;
    // See is chocolate is added for latte
    private boolean isChocolateAddedLatte;
    // See if whipped cream is added for espresso
    private boolean isWhippedCreamAddedEspresso;
    // See is chocolate is added for espresso
    private boolean isChocolateAddedEspresso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the base prices
        latteBasePrice = 30;
        espressoBasePrice = 50;
        whippedCreamPrice = 10;
        chocoPrice = 15;

        // Get handles to the views
        imageViewCard1 = findViewById(R.id.iv_card1);
        checkBox1Card1 = findViewById(R.id.cb1_card1);
        checkBox2Card1 = findViewById(R.id.cb2_card1);
        stepperTouch1 = findViewById(R.id.stepperTouch1);
        basePriceTextViewCard1 = findViewById(R.id.tv_price_card1);
        foodItemTextViewCard1 = findViewById(R.id.text_view_food_item_1);

        imageViewCard2 = findViewById(R.id.iv_card2);
        checkBox1Card2 = findViewById(R.id.cb1_card2);
        checkBox2Card2 = findViewById(R.id.cb2_card2);
        stepperTouch2 = findViewById(R.id.stepperTouch2);
        basePriceTextViewCard2 = findViewById(R.id.tv_price_card2);
        foodItemTextViewCard2 = findViewById(R.id.text_view_food_item_2);

        // Load images into the ImageViews
        Glide.with(this).load(R.drawable.latte).into(imageViewCard1);
        Glide.with(this).load(R.drawable.espresso).into(imageViewCard2);

        //Populate the views with relevant data
        checkBox1Card1.setText(getString(R.string.whipped_cream, whippedCreamPrice));
        checkBox2Card1.setText(getString(R.string.chocolate, chocoPrice));

        checkBox1Card2.setText(getString(R.string.whipped_cream, whippedCreamPrice));
        checkBox2Card2.setText(getString(R.string.chocolate, chocoPrice));

        basePriceTextViewCard1.append(" ₹" + String.valueOf(latteBasePrice));
        basePriceTextViewCard2.append(" ₹" + String.valueOf(espressoBasePrice));

        foodItemTextViewCard1.setText(R.string.latte);
        foodItemTextViewCard2.setText(R.string.espresso);

        // Setting up the stepper touch
        stepperTouch1.stepper.setMin(0);
        stepperTouch1.stepper.setMax(10);
        stepperTouch1.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityLatte = value;
            }
        });

        stepperTouch2.stepper.setMin(0);
        stepperTouch2.stepper.setMax(10);
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityEspresso = value;
            }
        });

        // Setting up OnCheckedChange listeners on the check boxes
        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isWhippedCreamAddedLatte = isChecked;
            }
        });

        checkBox2Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isChocolateAddedLatte = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isWhippedCreamAddedEspresso = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isChocolateAddedEspresso = isChecked;
            }
        });

    }

    // Override in order to send the data to the MainActivity
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.quantity_key_1), quantityLatte);
        returnIntent.putExtra(getString(R.string.quantity_key_2), quantityEspresso);
        returnIntent.putExtra(getString(R.string.cb1_key1), isWhippedCreamAddedLatte);
        returnIntent.putExtra(getString(R.string.cb2_key1), isChocolateAddedLatte);
        returnIntent.putExtra(getString(R.string.cb1_key2), isWhippedCreamAddedEspresso);
        returnIntent.putExtra(getString(R.string.cb2_key2), isChocolateAddedEspresso);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
