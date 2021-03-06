package com.uzcustomcake.order;

import android.app.AlertDialog;
import android.arch.lifecycle.*;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.uzcustomcake.MainActivity;
import com.uzcustomcake.R;

import java.util.*;

/**
 * Created by 00003130 on 10/23/17.
 */

public class PreOrderFragment extends Fragment {

  Button order;
  RecyclerView items;
  ImageView close;
  OrderViewModel model;
  TextView totalPrice;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.pre_order_sheet, container, false);
    order = v.findViewById(R.id.order);
    items = v.findViewById(R.id.items);
    close = v.findViewById(R.id.close);
    totalPrice = v.findViewById(R.id.totalPrice);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    model = ((MainActivity) getActivity()).getModel();

    order.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).finilizeOrderProcess();
      }
    });

    items.setLayoutManager(new LinearLayoutManager(getContext()));
    populateList();
    close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).hideBottomSheet();
      }
    });

    model.observeTotalCount().observe(this, new Observer<String>() {
      @Override public void onChanged(@Nullable String s) {
        totalPrice.setText(s);
      }
    });
  }

  public void populateList(){
//    Map<String, Filling> map = model.getChosenFillings();
//    for(Iterator<Map.Entry<String, Filling>> it = map.entrySet().iterator(); it.hasNext(); ) {
//      Map.Entry<String, Filling> entry = it.next();
//      if(!entry.getValue().isSelected()) {
//        it.remove();
//      }
//    }
    items.setAdapter(new PreOrderAdapter(model));
  }

  static class PreOrderAdapter extends RecyclerView.Adapter<PreOrderAdapter.ViewHolder>{

    private final List<PreOrderItem> items;
    private final OrderViewModel model;

    public PreOrderAdapter(OrderViewModel model) {
      this.model = model;
      this.items = model.getFilteredList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      return new ViewHolder(model, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_order_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
      holder.name.setText(items.get(i).name);
      holder.price.setText(items.get(i).priceTitle);
      holder.amount.setText(items.get(i).amount);
    }

    @Override
    public int getItemCount() {
      return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

      TextView name, price, amount;

      public ViewHolder(final OrderViewModel model, final View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
        amount = itemView.findViewById(R.id.amount);
        amount.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            final View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.choose_amount_fragment, null);
            final EditText customAmount = view.findViewById(R.id.amount);
            customAmount.setText(model.getAmount(getLayoutPosition()));

            view.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                customAmount.setText(String.valueOf(Integer.valueOf(customAmount.getText().toString()) + 1));
              }
            });
            view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                if(!customAmount.getText().toString().equals("1"))
                  customAmount.setText(String.valueOf(Integer.valueOf(customAmount.getText().toString()) - 1));
              }
            });
            AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.choose_lang)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
                    model.setAmount(getLayoutPosition(), Integer.valueOf(customAmount.getText().toString()));
                    amount.setText(model.getCustomAmount(getLayoutPosition()));
                    price.setText(model.getCustomPrice(getLayoutPosition()));
                  }
                })
                .create();

            dialog.show();
          }
        });
      }
    }
  }
}
