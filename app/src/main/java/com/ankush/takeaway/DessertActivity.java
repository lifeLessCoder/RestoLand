package com.ankush.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class DessertActivity extends AppCompatActivity {
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

    // Base price of panCake
    private int panCakeBasePrice;
    // Base price of strawberry Roll
    private int strawberryRollBasePrice;
    // Cost of Maple syrup
    private int mapleSyrupPrice;
    // Cost of raspberry toppings
    private int raspberryPrice;
    // Cost of blueberry toppings
    private int blueberryPrice;
    // Cost of the cherry toppings
    private int cherryPrice;


    // Pancake quantity
    private int quantityPancake = 0;
    // Strawberry roll quantity
    private int quantityStrawberryRoll = 0;

    // Check if maple syrup is added for pancake
    private boolean isMapleSyrupAddedPancake;
    // Check if raspberry toppings is added for pancake
    private boolean isRaspberryAddedPancake;
    // Check if blueberry toppings is added for strawberry roll
    private boolean isBlueberryAddedStrawberryRoll;
    // Check if cherry toppings is added for strawberry roll
    private boolean isCherryAddedStrawberryRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        panCakeBasePrice = 80;
        strawberryRollBasePrice = 60;
        mapleSyrupPrice = 20;
        blueberryPrice = 25;
        cherryPrice = 15;
        raspberryPrice = 30;

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
        Glide.with(this).load(R.drawable.pancakes).into(imageViewCard1);
        Glide.with(this).load(R.drawable.strawberry_roll).into(imageViewCard2);

        //Populate the views with relevant data
        checkBox1Card1.setText(getString(R.string.maple_syrup, mapleSyrupPrice));
        checkBox2Card1.setText(getString(R.string.raspberry_toppings, raspberryPrice));
        checkBox1Card2.setText(getString(R.string.blueberry_toppings, blueberryPrice));
        checkBox2Card2.setText(getString(R.string.cherry_toppings, cherryPrice));

        basePriceTextViewCard1.append(" ₹" + String.valueOf(panCakeBasePrice));
        basePriceTextViewCard2.append(" ₹" + String.valueOf(strawberryRollBasePrice));

        foodItemTextViewCard1.setText(R.string.pancake);
        foodItemTextViewCard2.setText(R.string.strawberry_roll);

        // Setting up the stepper touch
        stepperTouch1.stepper.setMin(0);
        stepperTouch1.stepper.setMax(10);
        stepperTouch1.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityPancake = value;
            }
        });

        stepperTouch2.stepper.setMin(0);
        stepperTouch2.stepper.setMax(10);
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityStrawberryRoll = value;
            }
        });

        // Setting up OnCheckedChange listeners on the check boxes
        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isMapleSyrupAddedPancake = isChecked;
            }
        });

        checkBox2Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isRaspberryAddedPancake = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isBlueberryAddedStrawberryRoll = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCherryAddedStrawberryRoll = isChecked;
            }
        });
    }

    // Override in order to send the data to the MainActivity
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.quantity_key_1), quantityPancake);
        returnIntent.putExtra(getString(R.string.quantity_key_2), quantityStrawberryRoll);
        returnIntent.putExtra(getString(R.string.cb1_key1), isMapleSyrupAddedPancake);
        returnIntent.putExtra(getString(R.string.cb2_key1), isRaspberryAddedPancake);
        returnIntent.putExtra(getString(R.string.cb1_key2), isBlueberryAddedStrawberryRoll);
        returnIntent.putExtra(getString(R.string.cb2_key2), isCherryAddedStrawberryRoll);
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
