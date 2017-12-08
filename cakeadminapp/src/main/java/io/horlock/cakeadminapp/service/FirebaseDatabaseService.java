package io.horlock.cakeadminapp.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.ArrayMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import io.horlock.cakeadminapp.domain.Bakery;
import io.horlock.cakeadminapp.domain.BakeryList;
import io.horlock.cakeadminapp.domain.Filling;
import io.horlock.cakeadminapp.domain.FillingList;
import io.horlock.cakeadminapp.domain.Order;
import io.horlock.cakeadminapp.domain.OrderList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created at 10/13/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class FirebaseDatabaseService implements FillingsService, ProductService, OrderService {

  private final static GenericTypeIndicator<List<Filling>> GENERIC_FILLINGS =
      new GenericTypeIndicator<>();

  private final static GenericTypeIndicator<List<Bakery>> GENERIC_PRODUCTS =
      new GenericTypeIndicator<>();

  private final static GenericTypeIndicator<List<String>> GENERIC_TYPES =
      new GenericTypeIndicator<>();

  private final DatabaseReference rootReference;
  private final MutableLiveData<DatabaseError> liveErrors;

  public FirebaseDatabaseService(DatabaseReference rootReference,
      MutableLiveData<DatabaseError> liveErrors) {
    this.rootReference = rootReference;
    this.liveErrors = liveErrors;
  }

  public LiveData<Map<String, Bakery>> getProductsByTypeMap(String language) {
    String child = "bakery";
    if (language == "US") {
      child = "bakery";
    } else if (language == "RU") {
      child = "bakery_ru";
    } else if (language == "UZ") child = "bakery_uz";
    return new DatabaseReferenceLiveData<>(rootReference.child(child), liveErrors,
        new DatabaseReferenceLiveData.DataSnapshotConverter<Map<String, Bakery>>() {
          @Override public Map<String, Bakery> map(DataSnapshot input) {
            final List<Bakery> bakeries = new BakeryList(input);
            final String[] types = input.child("types").getValue(String.class).split(",");

            final Map<String, Bakery> map = new HashMap<>();
            for (String type : types) {
              if (type == null) {
                continue;
              }

              for (Bakery bakery : bakeries) {
                if (type.equals(bakery.name())) {
                  map.put(type, bakery);
                  break;
                }
              }
            }
            return map;
          }
        });
  }

  @Override public LiveData<List<String>> getFillingsTypesByProduct(Bakery product) {
    final MutableLiveData<List<String>> liveTypes = new MutableLiveData<>();
    liveTypes.setValue(Arrays.asList(product.sort()));
    return liveTypes;
  }

  @Override
  public LiveData<Map<String, List<Filling>>> getFillingsByProduct(String language, Bakery product,
      final String type) {
    String child = "fillings";
    if (language == "US") {
      child = "fillings";
    } else if (language == "RU") {
      child = "fillings_ru";
    } else if (language == "UZ") child = "fillings_uz";
    return new DatabaseReferenceLiveData<>(rootReference.child(child).child(product.fillingId()),
        liveErrors, new DatabaseReferenceLiveData.DataSnapshotConverter<Map<String, List<Filling>>>() {
      @Override public Map<String, List<Filling>> map(DataSnapshot input) {
        Map<String, List<Filling>> map = new HashMap<>();
        for (DataSnapshot snapshot : input.getChildren()) {
          FillingList list = new FillingList(snapshot.getKey(), snapshot);
          map.put(snapshot.getKey(), list);
        }
        return map;
      }
    });
  }

  @Override public LiveData<List<String>> getTypes(String language) {
    String child = "bakery";
    if (language == "US") {
      child = "bakery";
    } else if (language == "RU") {
      child = "bakery_ru";
    } else if (language == "UZ") child = "bakery_uz";

    return new DatabaseReferenceLiveData<>(rootReference.child(child).child("types"), liveErrors,
        new DatabaseReferenceLiveData.DataSnapshotConverter<List<String>>() {
          @Override public List<String> map(DataSnapshot input) {
            return input.getValue(GENERIC_TYPES);
          }
        });
  }

  @Override public LiveData<List<Bakery>> getProducts(String type, String language) {
    String child = "bakery";
    if (language == "US") {
      child = "bakery";
    } else if (language == "RU") {
      child = "bakery_ru";
    } else if (language == "UZ") child = "bakery_uz";

    return new DatabaseReferenceLiveData<>(rootReference.child(child).child(type), liveErrors,
        new DatabaseReferenceLiveData.DataSnapshotConverter<List<Bakery>>() {
          @Override public List<Bakery> map(DataSnapshot input) {
            return new BakeryList(input);
          }
        });
  }

  @Override public void setOrder(Map<String, List<Order>> orders) {
    rootReference.child("orders").push().setValue(orders);
  }

  @Override public LiveData<Map<String, List<Order>>> getOrders() {
    return new DatabaseReferenceLiveData<>(rootReference.child("orders"), liveErrors,
        new DatabaseReferenceLiveData.DataSnapshotConverter<Map<String, List<Order>>>() {
          @Override public Map<String, List<Order>> map(DataSnapshot input) {
            Map<String, List<Order>> map = new ArrayMap<>();
            for(DataSnapshot snapshot : input.getChildren()){
              for (DataSnapshot snap : snapshot.getChildren()) {
                List<Order> orderList = new OrderList(snap, snapshot.getKey());

                map.put(snap.getKey(), orderList);
              }
            }
            return map;
          }
        });
  }

  @Override public void deleteOrder(Order order) {
    rootReference.child("orders").child(order.id).child(order.phone).child(String.valueOf(order.pos)).removeValue();
  }
}