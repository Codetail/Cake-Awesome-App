package com.uzcustomcake.core.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.service.ProductService;
import java.util.List;
import java.util.Map;

/**
 * created at 9/30/17
 *
 * @author Ozodrukh
 * @version 1.0
 */
public class BakeryProductsViewModel extends AndroidViewModel implements ProductService {

  public BakeryProductsViewModel(Application application) {
    super(application);
  }

  public LiveData<List<String>> getTypes() {
    return this.<CoreApplication>getApplication().firebaseService().getTypes();
  }

  public LiveData<Map<String, Bakery>> getProductsByTypesMap() {
    return this.<CoreApplication>getApplication().firebaseService().getProductsByTypeMap();
  }

  @Override public LiveData<List<Bakery>> getProducts(String type) {
    return this.<CoreApplication>getApplication().firebaseService().getProducts(type);
  }
}
