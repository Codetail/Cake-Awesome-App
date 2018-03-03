package io.horlock.cakeadminapp.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import io.horlock.cakeadminapp.CoreApplication;
import io.horlock.cakeadminapp.domain.Order;
import io.horlock.cakeadminapp.service.OrderService;
import java.util.List;
import java.util.Map;

/**
 * Created by 00003130 on 12/7/17.
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

  @Override public void deleteOrder(Order order) {
    this.<CoreApplication>getApplication().firebaseService().deleteOrder(order);
  }
}
