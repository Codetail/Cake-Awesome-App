package com.uzcustomcake.core.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sher on 10/23/17.
 */

public class Order {

    public String name;
    public String adding = "";
    public String address;
    public String cake_type;
    public int price;
    public String deliver_time;
    public String phone;

    public Order(String name, String adding, String address, String cake_type, int price,
        String deliver_time) {
        this.name = name;
        this.adding = adding;
        this.address = address;
        this.cake_type = cake_type;
        this.price = price;
        this.deliver_time = deliver_time;
    }

    public Order() {
    }
}
