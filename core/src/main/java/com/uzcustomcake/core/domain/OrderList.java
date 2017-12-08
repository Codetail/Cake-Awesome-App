package com.uzcustomcake.core.domain;

import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;

/**
 * Created by horlock on 12/8/17.
 */

public class OrderList extends ArrayList<Order> {

  public OrderList(DataSnapshot input) {
    super();

    for (DataSnapshot child : input.getChildren()) {
      add(createOrder(child));
    }
  }

  private static Order createOrder(DataSnapshot child) {
    return new Order(
        (String)child.child("name").getValue(),
        (String)child.child("adding").getValue(),
        (String)child.child("address").getValue(),
        (String) child.child("cake_type").getValue(),
        (int) child.child("price").getValue(),
        (String) child.child("deliver_time").getValue()
    );
  }
}
