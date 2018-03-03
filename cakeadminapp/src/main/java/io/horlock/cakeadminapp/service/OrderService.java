package io.horlock.cakeadminapp.service;

import android.arch.lifecycle.LiveData;
import io.horlock.cakeadminapp.domain.Order;
import java.util.List;
import java.util.Map;

/**
 * Created by 00003130 on 10/24/17.
 */

public interface OrderService {

  void setOrder(Map<String, List<Order>> orders);
  LiveData<Map<String, List<Order>>> getOrders();
  void deleteOrder(Order order);
}
