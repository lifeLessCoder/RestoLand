package com.ankush.takeaway;

/**
 * Model class for each grid item
 */

public class Food {
    // Resource Id of the image
    private int mImageId;
    // Color of the card
    private int mColorId;
    // Title of the food type
    private String mTitle;


    public Food(int mImageId, int mColorId, String mTitle) {
        this.mImageId = mImageId;
        this.mTitle = mTitle;
        this.mColorId = mColorId;
    }

    public int getmImageId() {
        return mImageId;
    }

    public int getmColorId(){return mColorId;}

    public String getmTitle() {
        return mTitle;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    public void setmColorId(int mColorId) {
        this.mColorId = mColorId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
