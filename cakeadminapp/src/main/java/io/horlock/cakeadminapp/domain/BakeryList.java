package io.horlock.cakeadminapp.domain;

import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;

/**
 * created at 10/16/17
 *
 * @author 00003130
 * @version 1.0
 */

public class BakeryList extends ArrayList<Bakery> {

  public BakeryList(DataSnapshot input) {
    super();

    for (DataSnapshot child : input.getChildren()) {
      if ("types".equals(child.getKey())) {
        continue;
      }

      add(createBakery(child));
    }
  }

  private static Bakery createBakery(DataSnapshot child) {
    return new Bakery(
        child.getKey(),
        (String) child.child("fillingsId").getValue(),
        ((String) child.child("sort").getValue()).split(",")
    );
  }
}
