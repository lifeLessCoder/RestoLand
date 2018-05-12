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

public class SnacksActivity extends AppCompatActivity {
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

    // Base price of burger
    private int burgerBasePrice;
    // Base price of pizza
    private int pizzaBasePrice;
    // Cost of extra Cheese
    private int extraCheesePrice;
    // Cost of tomatoes
    private int tomatoesPrice;
    // Cost of extra olives
    private int extraOlivesPrice;
    // Cost of the cheese burst
    private int cheeseBurstPrice;


    // Burger quantity
    private int quantityBurger = 0;
    // Pizza quantity
    private int quantityPizza = 0;

    // Check if extra cheese is added for burger
    private boolean isExtraCheeseAddedBurger;
    // Check if tomatoes are added for burger
    private boolean isTomatoAddedBurger;
    // Check if extra olives is added for pizza
    private boolean isExtraOliveAddedPizza;
    // Check if cheese burst is added for pizza
    private boolean isCheeseBurstAddedPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        burgerBasePrice = 90;
        pizzaBasePrice = 120;
        extraCheesePrice = 20;
        tomatoesPrice = 15;
        extraOlivesPrice = 25;
        cheeseBurstPrice = 50;

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
        Glide.with(this).load(R.drawable.hamburger).into(imageViewCard1);
        Glide.with(this).load(R.drawable.pizza).into(imageViewCard2);

        //Populate the views with relevant data
        checkBox1Card1.setText(getString(R.string.extra_cheese, extraCheesePrice));
        checkBox2Card1.setText(getString(R.string.tomato, tomatoesPrice));
        checkBox1Card2.setText(getString(R.string.extra_olives, extraOlivesPrice));
        checkBox2Card2.setText(getString(R.string.cheese_burst, cheeseBurstPrice));

        basePriceTextViewCard1.append(" ₹" + String.valueOf(burgerBasePrice));
        basePriceTextViewCard2.append(" ₹" + String.valueOf(pizzaBasePrice));

        foodItemTextViewCard1.setText(R.string.burger);
        foodItemTextViewCard2.setText(R.string.pizza);

        // Setting up the stepper touch
        stepperTouch1.stepper.setMin(0);
        stepperTouch1.stepper.setMax(10);
        stepperTouch1.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityBurger = value;
            }
        });

        stepperTouch2.stepper.setMin(0);
        stepperTouch2.stepper.setMax(10);
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityPizza = value;
            }
        });

        // Setting up OnCheckedChange listeners on the check boxes
        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isExtraCheeseAddedBurger = isChecked;
            }
        });

        checkBox2Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isTomatoAddedBurger = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isExtraOliveAddedPizza = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheeseBurstAddedPizza = isChecked;
            }
        });
    }

    // Override in order to send the data to the MainActivity
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.quantity_key_1), quantityBurger);
        returnIntent.putExtra(getString(R.string.quantity_key_2), quantityPizza);
        returnIntent.putExtra(getString(R.string.cb1_key1), isExtraCheeseAddedBurger);
        returnIntent.putExtra(getString(R.string.cb2_key1), isTomatoAddedBurger);
        returnIntent.putExtra(getString(R.string.cb1_key2), isExtraOliveAddedPizza);
        returnIntent.putExtra(getString(R.string.cb2_key2), isCheeseBurstAddedPizza);
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
