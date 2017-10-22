package com.uzcustomcake;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseError;
import com.uzcustomcake.core.CoreApplication;
import com.uzcustomcake.core.models.BakeryProductsViewModel;
import com.uzcustomcake.fillings.SelectFillingsAdapter;
import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * created at 10/1/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {

  private BakeryProductsViewModel productsViewModel;
  public ImageView busket;

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

    ((CoreApplication) getApplication()).liveDatabaseErrors()
        .observe(this, errorObserver);

    productsViewModel = ViewModelProviders.of(this).get(BakeryProductsViewModel.class);

    busket = findViewById(R.id.buscket);

    setContentView(R.layout.activity_main);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    final ViewPager productsViewPager = findViewById(R.id.productsViewPager);
    productsViewPager.setAdapter(
        new SelectFillingsAdapter(getSupportFragmentManager(), this, productsViewModel));
  }
}
