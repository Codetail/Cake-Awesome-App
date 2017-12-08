package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;
import com.uzcustomcake.core.domain.Bakery;
import java.util.List;

/**
 * created at 10/13/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public interface ProductService {

  LiveData<List<String>> getTypes(String language);

  LiveData<List<Bakery>> getProducts(String type, String language);
}
