package com.example.deepanshub.weddingplanner.vendors;

/**
 * Created by deepanshub on 23/3/18.
 */

public class Model {
    private int mVendorImage;
    private String mVendorName;

    public Model(int mVendorImage, String mVendorName) {
        this.mVendorImage = mVendorImage;
        this.mVendorName = mVendorName;
    }

    public String getmVendorName() {
        return mVendorName;
    }

    public void setmVendorName(String mVendorName) {
        this.mVendorName = mVendorName;
    }

    public int getmVendorImage() {
        return mVendorImage;
    }

    public void setmVendorImage(int mVendorImage) {
        this.mVendorImage = mVendorImage;
    }
}
