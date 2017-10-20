package com.uzcustomcake.fillings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.uzcustomcake.R;
import com.uzcustomcake.core.adapter.MultiViewAdapter;
import com.uzcustomcake.core.domain.Bakery;
import com.uzcustomcake.core.domain.Filling;
import com.uzcustomcake.core.models.FillingProductsViewModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * created at 10/1/17
 *
 * @author 00003130
 * @version 1.0
 */

public class FillingViewFragment extends Fragment {

  public static FillingViewFragment forType(Bakery product, String type) {
    final Bundle bundle = new Bundle();
    bundle.putString("type", type);
    bundle.putParcelable("product", product);

    final FillingViewFragment fragment = new FillingViewFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  private RecyclerView contentView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return contentView = new RecyclerView(inflater.getContext());
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    final String type = getArguments().getString("type");
    final Bakery product = getArguments().getParcelable("product");

    final FillingProductsViewModel model = ViewModelProviders.of(this)
        .get(FillingProductsViewModel.class);
    final FillingsAdapter adapter = new FillingsAdapter(model.getFillingsByProduct(product, type));

    contentView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    contentView.setAdapter(adapter);
  }

  static class FillingsAdapter extends MultiViewAdapter<Filling> {

    public FillingsAdapter(LiveData<Map<String, List<Filling>>> liveData) {
      super(liveData);
    }

    @Override public ViewHolderWithBindAction<Filling>
    onCreateViewHolder(ViewGroup parent, int viewType) {
      // Check whether parent has already create ViewHolder for RecyclerView,
      // if not create ItemView
      final ViewHolderWithBindAction parentInitiatedHolder =
          super.onCreateViewHolder(parent, viewType);

      if (parentInitiatedHolder == null) {
        return FillingViewHolder.create(parent);
      } else {
        return parentInitiatedHolder;
      }
    }
  }

  /**
   * this holder to bind Section certain Item data to view
   */
  static class FillingViewHolder extends MultiViewAdapter.ViewHolderWithBindAction<Filling> {
    private final static NumberFormat MoneyFormat;

    static {
      MoneyFormat = DecimalFormat.getCurrencyInstance(Locale.ENGLISH);
    }

    static FillingViewHolder create(ViewGroup parent) {
      return new FillingViewHolder(LayoutInflater.from(parent.getContext())
          .inflate(R.layout.layout_filling_view, parent, false));
    }

    final TextView title;
    final TextView price;
    final ImageView image;

    FillingViewHolder(View itemView) {
      super(itemView);

      this.title = itemView.findViewById(R.id.title);
      this.price = itemView.findViewById(R.id.price);
      this.image = itemView.findViewById(R.id.image);
    }

    @Override public void bind(int position, Filling o) {
      title.setText(o.name());
      price.setText(MoneyFormat.format(o.price()));
    }
  }
}
