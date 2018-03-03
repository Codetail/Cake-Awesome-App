package io.horlock.cakeadminapp.domain;

/**
 * Created by 00003130 on 10/23/17.
 */

public class Order {

    public String name;
    public String adding = "";
    public String address;
    public String cake_type;
    public long price;
    public String deliver_time;
    public String phone;
    public String id;
    public String comment;
    public long amount;
    public int pos;

    public Order(String name, String adding, String address, String cake_type, long price,
        String comment, long amount, String deliver_time, String id) {
        this.name = name;
        this.adding = adding;
        this.address = address;
        this.cake_type = cake_type;
        this.price = price;
        this.deliver_time = deliver_time;
        this.id = id;
        this.comment = comment;
        this.amount = amount;
    }
}
