package com.uzcustomcake.core.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sher on 10/23/17.
 */

public class Order implements Parcelable {

    protected Order(Parcel in) {
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
