package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.domain.Filling;
import java.util.List;
import java.util.Map;

/**
 * created at 10/13/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public interface FillingsService {

  LiveData<List<String>> getFillingsTypesByProduct(Bakery product);

  LiveData<Map<String, List<Filling>>> getFillingsByProduct(Bakery product, String type);
}
