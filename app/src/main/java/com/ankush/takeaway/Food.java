package com.ankush.takeaway;

/**
 * Model class for each grid item
 */

public class Food {
    // Resource Id of the image
    private int mImageId;
    // Title of the food type
    private String mTitle;
    // Summary of the food type
    private String mSummary;

    public Food(int mImageId, String mTitle, String mSummary) {
        this.mImageId = mImageId;
        this.mTitle = mTitle;
        this.mSummary = mSummary;
    }

    public int getmImageId() {
        return mImageId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSummary() {
        return mSummary;
    }
}
