package com.uzcustomcake;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.LinearLayout;
import com.google.firebase.database.DatabaseError;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.models.BakeryProductsViewModel;
import com.uzcustomcake.fillings.SelectFillingsAdapter;
import com.uzcustomcake.order.OrderFragment;
import com.uzcustomcake.order.PreOrderFragment;

import java.util.List;
import java.util.Locale;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * created at 10/1/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private BakeryProductsViewModel productsViewModel;
  public ImageView basket;
  public PriceFormatter priceFormatter;
  public LinearLayout bottomSheet;
  public FrameLayout flContent;
  BottomSheetBehavior bottomSheetBehavior;

  private final Observer<DatabaseError> errorObserver = new Observer<DatabaseError>() {
    @Override public void onChanged(@Nullable DatabaseError databaseError) {
      if (databaseError == null) {
        return;
      }

      databaseError.toException().printStackTrace();

      Snackbar.make(findViewById(android.R.id.content), databaseError.getMessage(), LENGTH_SHORT)
          .show();
    }
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ((CoreApplication) getApplication()).liveDatabaseErrors()
        .observe(this, errorObserver);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      priceFormatter = new PriceFormatter(getResources().getConfiguration().getLocales().get(0));
    }else {
      priceFormatter = new PriceFormatter(getResources().getConfiguration().locale);
    }

    productsViewModel = ViewModelProviders.of(this).get(BakeryProductsViewModel.class);

    basket = findViewById(R.id.bascket);
    basket.setOnClickListener(this);
    bottomSheet = findViewById(R.id.bottom_sheet);

    getPreOrderFragment();

    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    final ViewPager productsViewPager = findViewById(R.id.productsViewPager);
    productsViewPager.setAdapter(
        new SelectFillingsAdapter(getSupportFragmentManager(), this, productsViewModel));

    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    bottomSheetBehavior.setHideable(true);
    float scale = getResources().getDisplayMetrics().density;
    int height = (int) (250*scale + 0.5f);
    bottomSheetBehavior.setPeekHeight(height);
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  public void getPreOrderFragment(){
    fragment = new PreOrderFragment();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.flContent, fragment)
        .commitAllowingStateLoss();
  }

  public Fragment fragment;
  public boolean isVisible = false;

  @Override
  public void onClick(View view) {
    if(!isVisible) {
      getPreOrderFragment();
      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
      isVisible = true;
    }else {
      isVisible = false;
      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
  }

  public void finilizeOrderProcess(){
    float scale = getResources().getDisplayMetrics().density;
    int height = (int) (340*scale + 0.5f);
    bottomSheetBehavior.setPeekHeight(height);
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.flContent, new OrderFragment())
        .commitAllowingStateLoss();
  }

  public void sendDataToServer(){
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  public void hideBottomSheet(){
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
  }
}
