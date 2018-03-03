package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;
import com.uzcustomcake.core.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by 00003130 on 10/24/17.
 */

public interface OrderService {

  void setOrder(Map<String, List<Order>> orders);
  LiveData<Map<String, List<Order>>> getOrders();
}
