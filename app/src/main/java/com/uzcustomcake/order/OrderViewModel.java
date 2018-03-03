package com.uzcustomcake.order;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.domain.Filling;
import com.uzcustomcake.core.domain.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 00003130 on 10/23/17.
 */

public class OrderViewModel extends AndroidViewModel {

  private List<List<Pair<String, Filling>>> separatedList = new ArrayList<>();
  private List<Integer> amounts = new ArrayList<>();
  private String name, phone, address, date, comment;
  private List<PreOrderItem> items = new ArrayList<>();

  public OrderViewModel(Application application) {
    super(application);
  }

  public List<Pair<String, Filling>> getNewMap() {
    List<Pair<String, Filling>> emptyMap = new ArrayList<>();
    separatedList.add(emptyMap);
    return emptyMap;
  }

  public void setAmount(int position, int amount){
    amounts.set(position, amount);
    calculateTotalCount();
  }

  public List<PreOrderItem> getFilteredList() {
    List<PreOrderItem> items = new ArrayList<>();
    int counter = 0;
    for (List<Pair<String, Filling>> list : separatedList) {
      PreOrderItem item = new PreOrderItem();
      boolean entered = false;
      int price = 0;
      for (Pair<String, Filling> pair : list) {
        if(amounts.isEmpty() || amounts.size() == counter)
          amounts.add(counter, 1);
        if(TextUtils.isEmpty(item.name))
          item.name = pair.first + "(";
        if (pair.second.isSelected()) {
          item.name += " " + pair.second.name() + ",";
          entered = true;
          price += pair.second.price();
          if(((CoreApplication)getApplication()).getLanguage().equals("RU")){
            item.amount = amounts.get(counter).toString() + " шт";
            item.priceTitle = String.valueOf(price) + " сум";
          }else if(((CoreApplication)getApplication()).getLanguage().equals("US")){
            item.amount = amounts.get(counter).toString() + " item";
            item.priceTitle = String.valueOf(price) + " uzs";
          }else if(((CoreApplication)getApplication()).getLanguage().equals("UZ")){
            item.amount = amounts.get(counter).toString() + " ta";
            item.priceTitle = String.valueOf(price) + " so'm";
          }
          item.price = price;
        }
      }
      if(item.name !=  null)
        item.name = item.name.substring(0, item.name.length() - 1) + ")";
      if (entered) {
        items.add(item);
      }
      counter++;
    }
    calculateTotalCount();
    this.items = items;
    return items;
  }

  public Map<String, List<Order>> getOrdersList() {
    List<Order> items = new ArrayList<>();
    int counter = 0;
    for (List<Pair<String, Filling>> list : separatedList) {
      Order item = new Order();
      item.name = name;
      item.address = address;
      item.deliver_time = date;
      item.comment = comment;
      boolean entered = false;
      for (Pair<String, Filling> pair : list) {
        item.cake_type = pair.first;
        if (pair.second.isSelected()) {
          entered = true;
          item.amount = amounts.get(counter);
          item.price += pair.second.price();
          item.adding += pair.second.name() + "+";
        }
      }
      if (entered) {
        items.add(item);
        counter++;
      }
    }
    Map<String, List<Order>> map = new HashMap<>();
    map.put(phone, items);
    return map;
  }

  public boolean isEmpty() {
    for (List<Pair<String, Filling>> list : separatedList) {
      for (Pair<String, Filling> pair : list) {
        if (pair.second.isSelected()) {
          return true;
        }
      }
    }
    return false;
  }

  private void calculateTotalCount() {
    int price = 0;
    int counter = 0;
    for (List<Pair<String, Filling>> list : separatedList) {
      for (Pair<String, Filling> pair : list) {
        if (pair.second.isSelected()) {
          price += pair.second.price() * amounts.get(counter);
        }
      }
      counter++;
    }
    if(((CoreApplication) getApplication()).getLanguage().equals("US")){
       liveTotal.setValue(String.valueOf(price) + " uzs");
    }else if (((CoreApplication) getApplication()).getLanguage().equals("RU")){
      liveTotal.setValue(String.valueOf(price) + " сум");
    }else if (((CoreApplication) getApplication()).getLanguage().equals("UZ")){
      liveTotal.setValue(String.valueOf(price) + " so'm");
    }else {
      liveTotal.setValue(String.valueOf(price) + " uzs");
    }
  }

  private final MutableLiveData<String> liveTotal = new MutableLiveData<>();

  public LiveData<String> observeTotalCount(){
    return liveTotal;
  }

  public void saveUserData(String name, String phone, String address, String date, String comment) {
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.date = date;
    this.comment = comment;
  }

  public void sendOrdersToServer() {
    this.<CoreApplication>getApplication().firebaseService().setOrder(getOrdersList());
  }

  public void clear() {

    separatedList = null;
    separatedList = new ArrayList<>();
  }

  public String getCustomAmount(int position){
    if(((CoreApplication) getApplication()).getLanguage().equals("US")){
      return amounts.get(position).toString() + " items";
    }else if (((CoreApplication) getApplication()).getLanguage().equals("RU")){
      return amounts.get(position).toString() + " шт";
    }else if (((CoreApplication) getApplication()).getLanguage().equals("UZ")){
      return amounts.get(position).toString() + " ta";
    }else {
      return amounts.get(position).toString() + " items";
    }
  }

  public String getCustomPrice(int position){
    if(((CoreApplication) getApplication()).getLanguage().equals("US")){
      return (String.valueOf(amounts.get(position)*items.get(position).price) + " uzs");
    }else if (((CoreApplication) getApplication()).getLanguage().equals("RU")){
      return (String.valueOf(amounts.get(position)*items.get(position).price) + " сум");
    }else if (((CoreApplication) getApplication()).getLanguage().equals("UZ")){
      return (String.valueOf(amounts.get(position)*items.get(position).price) + " so'm");
    }
    return (String.valueOf(amounts.get(position)*items.get(position).price) + " uzs");
  }

  public String getAmount(int position){
    return amounts.get(position).toString();
  }
}
