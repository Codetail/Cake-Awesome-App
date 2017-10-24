package com.uzcustomcake;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
  BottomSheetBehavior bottomSheetBehavior;
  private OrderViewModel model;
  private View hider;

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

    model = new OrderViewModel(getApplication());

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
    hider = findViewById(R.id.hider);
    hider.setVisibility(View.GONE);
    hider.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
      }
    });

    getPreOrderFragment();

    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    final ViewPager productsViewPager = findViewById(R.id.productsViewPager);
    productsViewPager.setAdapter(
        new SelectFillingsAdapter(getSupportFragmentManager(), this, productsViewModel));

    bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    bottomSheetBehavior.setHideable(true);
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View v, int i) {
        if (BottomSheetBehavior.STATE_DRAGGING == i) {

        } else if (BottomSheetBehavior.STATE_COLLAPSED == i) {
          hider.setVisibility(View.GONE);
          isVisible = false;
          View view = MainActivity.this.getCurrentFocus();
          if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
          }
        }else if(BottomSheetBehavior.STATE_HIDDEN == i){
          hider.setVisibility(View.GONE);
          isVisible = false;
          View view = MainActivity.this.getCurrentFocus();
          if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
          }
        }
      }

      @Override
      public void onSlide(@NonNull View view, float v) {

      }
    });
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
    if(model.isEmpty()) {
      if (!isVisible) {
        hider.setVisibility(View.VISIBLE);
        getPreOrderFragment();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        isVisible = true;
      } else {
        isVisible = false;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        hider.setVisibility(View.GONE);
      }
    }else {
      Snackbar.make(this.getCurrentFocus(), "Choose at least one fitting to get an order", LENGTH_SHORT).show();
    }
  }

  public void finilizeOrderProcess(){
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.flContent, new OrderFragment())
        .commitAllowingStateLoss();
  }

  public void sendDataToServer(){
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    hider.setVisibility(View.GONE);

    model.sendOrdersToServer();
    model.clear();
    recreate();
  }

  public OrderViewModel getModel(){
    return model;
  }

  public void hideBottomSheet(){
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    hider.setVisibility(View.GONE);
  }
}
