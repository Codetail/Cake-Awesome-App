package com.uzcustomcake.core.service;

import com.uzcustomcake.core.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by horlock on 10/24/17.
 */

public interface OrderService {

  void setOrder(Map<String, List<Order>> orders);
}
