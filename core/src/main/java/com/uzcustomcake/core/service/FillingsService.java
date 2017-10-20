package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.domain.Filling;
import java.util.List;

/**
 * created at 10/13/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public interface FillingsService {

  LiveData<List<String>> getFillingsTypesByProduct(Bakery product);

  LiveData<List<Filling>> getFillingsByProduct(Bakery product, String type);
}
