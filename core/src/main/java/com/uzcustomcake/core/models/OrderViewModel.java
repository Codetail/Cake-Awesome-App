package com.uzcustomcake.core.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.domain.Order;
import com.uzcustomcake.core.service.OrderService;
import java.util.List;
import java.util.Map;

/**
 * Created by horlock on 12/7/17.
 */

public class OrderViewModel extends AndroidViewModel implements OrderService {
  public OrderViewModel(Application application) {
    super(application);
  }

  @Override public void setOrder(Map<String, List<Order>> orders) {

  }

  @Override public LiveData<Map<String, List<Order>>> getOrders() {
    return this.<CoreApplication>getApplication().firebaseService().getOrders();
  }
}
