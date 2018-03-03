package io.horlock.cakeadminapp.service;

import android.arch.lifecycle.LiveData;
import io.horlock.cakeadminapp.domain.Bakery;
import java.util.List;

/**
 * created at 10/13/17
 *
 * @author 00003130
 * @version 1.0
 */

public interface ProductService {

  LiveData<List<String>> getTypes(String language);

  LiveData<List<Bakery>> getProducts(String type, String language);
}
