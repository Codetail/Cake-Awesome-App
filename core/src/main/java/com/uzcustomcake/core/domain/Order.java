package com.uzcustomcake.core.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 00003130 on 10/23/17.
 */

public class Order {

    public String name;
    public String adding = "";
    public String address;
    public String cake_type;
    public int price;
    public String deliver_time;
    public String phone;
    public String comment;
    public int amount;

    public Order(String name, String adding, String address, String cake_type, int price,
        String deliver_time, String comment) {
        this.name = name;
        this.adding = adding;
        this.address = address;
        this.cake_type = cake_type;
        this.price = price;
        this.deliver_time = deliver_time;
        this.comment = comment;
    }

    public Order() {
    }
}
