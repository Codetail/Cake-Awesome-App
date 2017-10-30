package com.uzcustomcake.order;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.v4.util.Pair;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.domain.Filling;
import com.uzcustomcake.core.domain.Order;
import com.uzcustomcake.order.PreOrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sher on 10/23/17.
 */

public class OrderViewModel extends AndroidViewModel {

    private List<List<Pair<String, Filling>>> separatedList = new ArrayList<>();
    private String name, phone, address, date;

    public OrderViewModel(Application application) {
        super(application);
    }

    public List<Pair<String, Filling>> getNewMap(){
        List<Pair<String, Filling>> emptyMap = new ArrayList<>();
        separatedList.add(emptyMap);
        return emptyMap;
    }

    public List<PreOrderItem> getFilteredList(){
        List<PreOrderItem> items = new ArrayList<>();
        for(List<Pair<String, Filling>> list: separatedList){
            PreOrderItem item = new PreOrderItem();
            boolean entered = false;
            int price = 0;
            for(Pair<String, Filling> pair: list){
                item.name = pair.first;
                if(pair.second.isSelected()){
                    entered = true;
                    price += pair.second.price();
                    item.price = String.valueOf(price) + " сум";
                }
            }
            if(entered){
                items.add(item);
            }
        }
        return items;
    }

    public Map<String, List<Order>> getOrdersList(){
        List<Order> items = new ArrayList<>();
        for(List<Pair<String, Filling>> list: separatedList){
            Order item = new Order();
            item.name = name;
            item.address = address;
            item.deliver_time = date;
            boolean entered = false;
            for(Pair<String, Filling> pair: list){
                item.cake_type = pair.first;
                if(pair.second.isSelected()){
                    entered = true;
                    item.price += pair.second.price();
                    item.adding += pair.second.name() + "+";
                }
            }
            if(entered){
                items.add(item);
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

    public String getTotalCount(){
        int price = 0;
        for (List<Pair<String, Filling>> list : separatedList) {
            for (Pair<String, Filling> pair : list) {
                if (pair.second.isSelected()) {
                    price += pair.second.price();
                }
            }
        }
        return String.valueOf(price) + " сум";
    }

    public void saveUserData(String name, String phone, String address, String date){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
    }

    public void sendOrdersToServer(){
        this.<CoreApplication>getApplication().firebaseService().setOrder(getOrdersList());
    }

    public void clear() {
        separatedList.clear();
    }
}
