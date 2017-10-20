package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.domain.BakeryList;
import com.uzcustomcake.core.domain.Filling;
import com.uzcustomcake.core.domain.FillingList;
import com.uzcustomcake.core.service.DatabaseReferenceLiveData.DataSnapshotConverter;
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

public class FirebaseDatabaseService implements FillingsService, ProductService {

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

  public LiveData<Map<String, Bakery>> getProductsByTypeMap() {
    return new DatabaseReferenceLiveData<>(rootReference.child("bakery"), liveErrors,
        new DataSnapshotConverter<Map<String, Bakery>>() {
          @Override public Map<String, Bakery> map(DataSnapshot input) {
            final List<Bakery> bakeries = new BakeryList(input);
            final String[] types = input.child("types")
                .getValue(String.class).split(",");

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
  public LiveData<List<Filling>> getFillingsByProduct(Bakery product, final String type) {
    return new DatabaseReferenceLiveData<>(
        rootReference.child("fillings").child(product.fillingId()).child(type),
        liveErrors,
        new DataSnapshotConverter<List<Filling>>() {
          @Override public List<Filling> map(DataSnapshot input) {
            return new FillingList(type, input);
          }
        }
    );
  }

  @Override public LiveData<List<String>> getTypes() {
    return new DatabaseReferenceLiveData<>(
        rootReference.child("bakery").child("types"),
        liveErrors,
        new DataSnapshotConverter<List<String>>() {
          @Override public List<String> map(DataSnapshot input) {
            return input.getValue(GENERIC_TYPES);
          }
        }
    );
  }

  @Override public LiveData<List<Bakery>> getProducts(String type) {
    return new DatabaseReferenceLiveData<>(
        rootReference.child("bakery").child(type),
        liveErrors,
        new DataSnapshotConverter<List<Bakery>>() {
          @Override public List<Bakery> map(DataSnapshot input) {
            return new BakeryList(input);
          }
        }
    );
  }
}
