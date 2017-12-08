package io.horlock.cakeadminapp.service;

import android.arch.lifecycle.LiveData;
import io.horlock.cakeadminapp.domain.Bakery;
import io.horlock.cakeadminapp.domain.Filling;
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

  LiveData<Map<String, List<Filling>>> getFillingsByProduct(String language, Bakery product,
      String type);
}
