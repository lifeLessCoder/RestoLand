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

public class IceCreamActivity extends AppCompatActivity {
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

    // Base price of ice cream sundae
    private int sundaeBasePrice;
    // Base price of oreo ice cream
    private int oreoIceCreamBasePrice;
    // Cost of Strawberry syrup
    private int strawberrySyrupPrice;
    // Cost of chocolate toppings
    private int chocolatePrice;
    // Cost of extra oreo
    private int extraOreoPrice;
    // Cost of the waffles
    private int wafflePrice;


    // Sundae quantity
    private int quantitySundae = 0;
    // Oreo ice cream quantity
    private int quantityOreoIceCream = 0;

    // Check if strawberry syrup is added for sundae
    private boolean isStrawberrySyrupAddedSundae;
    // Check if chocolate toppings is added for sundae
    private boolean isChocolateAddedSundae;
    // Check if extra oreo is added for oreo ice cream
    private boolean isExtraOreoAddedOreoIceCream;
    // Check if waffles is added for oreo ice cream
    private boolean isWafflesAddedOreoIceCream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sundaeBasePrice = 90;
        oreoIceCreamBasePrice = 100;
        strawberrySyrupPrice = 25;
        chocolatePrice = 20;
        extraOreoPrice = 20;
        wafflePrice = 35;

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
        Glide.with(this).load(R.drawable.ice_cream_sundae).into(imageViewCard1);
        Glide.with(this).load(R.drawable.ice_cream_oreo).into(imageViewCard2);

        //Populate the views with relevant data
        checkBox1Card1.setText(getString(R.string.strawberry_syrup, strawberrySyrupPrice));
        checkBox2Card1.setText(getString(R.string.chocolate, chocolatePrice));
        checkBox1Card2.setText(getString(R.string.extra_oreo, extraOreoPrice));
        checkBox2Card2.setText(getString(R.string.waffles, wafflePrice));

        basePriceTextViewCard1.append(" ₹" + String.valueOf(sundaeBasePrice));
        basePriceTextViewCard2.append(" ₹" + String.valueOf(oreoIceCreamBasePrice));

        foodItemTextViewCard1.setText(R.string.sundae);
        foodItemTextViewCard2.setText(R.string.oreo_icecream);

        // Setting up the stepper touch
        stepperTouch1.stepper.setMin(0);
        stepperTouch1.stepper.setMax(10);
        stepperTouch1.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantitySundae = value;
            }
        });

        stepperTouch2.stepper.setMin(0);
        stepperTouch2.stepper.setMax(10);
        stepperTouch2.stepper.addStepCallback(new OnStepCallback() {
            @Override
            public void onStep(int value, boolean b) {
                quantityOreoIceCream = value;
            }
        });

        // Setting up OnCheckedChange listeners on the check boxes
        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isStrawberrySyrupAddedSundae = isChecked;
            }
        });

        checkBox2Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isChocolateAddedSundae = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isExtraOreoAddedOreoIceCream = isChecked;
            }
        });

        checkBox1Card1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isWafflesAddedOreoIceCream = isChecked;
            }
        });
    }

    // Override in order to send the data to the MainActivity
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(getString(R.string.quantity_key_1), quantitySundae);
        returnIntent.putExtra(getString(R.string.quantity_key_2), quantityOreoIceCream);
        returnIntent.putExtra(getString(R.string.cb1_key1), isStrawberrySyrupAddedSundae);
        returnIntent.putExtra(getString(R.string.cb2_key1), isChocolateAddedSundae);
        returnIntent.putExtra(getString(R.string.cb1_key2), isExtraOreoAddedOreoIceCream);
        returnIntent.putExtra(getString(R.string.cb2_key2), isWafflesAddedOreoIceCream);
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
