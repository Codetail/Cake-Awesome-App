package io.horlock.cakeadminapp.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import io.horlock.cakeadminapp.CoreApplication;
import io.horlock.cakeadminapp.domain.Bakery;
import io.horlock.cakeadminapp.service.ProductService;
import java.util.List;
import java.util.Map;

/**
 * created at 9/30/17
 *
 * @author 00003130
 * @version 1.0
 */
public class BakeryProductsViewModel extends AndroidViewModel implements ProductService {

  public BakeryProductsViewModel(Application application) {
    super(application);
  }

  public LiveData<List<String>> getTypes(String lang) {
    return this.<CoreApplication>getApplication().firebaseService().getTypes(lang);
  }

  public LiveData<Map<String, Bakery>> getProductsByTypesMap(String lang) {
    return this.<CoreApplication>getApplication().firebaseService().getProductsByTypeMap(lang);
  }

  @Override public LiveData<List<Bakery>> getProducts(String type, String lang) {
    return this.<CoreApplication>getApplication().firebaseService().getProducts(type, lang);
  }
}
