package com.uzcustomcake.fillings;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.models.BakeryProductsViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created at 10/1/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class SelectFillingsAdapter extends FragmentStatePagerAdapter {
  private final ArrayMap<String, Bakery> bakeryMap = new ArrayMap<>();

  public SelectFillingsAdapter(@NonNull FragmentManager fm, LifecycleOwner host,
      final BakeryProductsViewModel viewModel) {
    super(fm);
    viewModel.getProductsByTypesMap().observe(host, new Observer<Map<String, Bakery>>() {
      @Override public void onChanged(@Nullable Map<String, Bakery> newData) {
        if (newData == null) {
          newData = Collections.emptyMap();
        }

        bakeryMap.putAll(newData);
        notifyDataSetChanged();
      }
    });
  }

  @Override public Fragment getItem(int position) {
    return FillingViewFragment.forType(bakeryMap.valueAt(position), bakeryMap.keyAt(position));
  }

  @Override public CharSequence getPageTitle(int position) {
    return bakeryMap.keyAt(position);
  }

  @Override public int getCount() {
    return bakeryMap.size();
  }
}
