package com.uzcustomcake.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.uzcustomcake.MainActivity;
import com.uzcustomcake.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horlock on 10/23/17.
 */

public class PreOrderFragment extends Fragment {

  Button order;
  RecyclerView items;
  ImageView close;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.pre_order_sheet, container, false);
    order = v.findViewById(R.id.order);
    items = v.findViewById(R.id.items);
    close = v.findViewById(R.id.close);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    order.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).finilizeOrderProcess();
      }
    });

    items.setLayoutManager(new LinearLayoutManager(getContext()));
    List<PreOrderItem> list = new ArrayList<>();
    list.add(new PreOrderItem());
    list.add(new PreOrderItem());
    items.setAdapter(new PreOrderAdapter(list));
    close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).hideBottomSheet();
      }
    });
  }


  static class PreOrderAdapter extends RecyclerView.Adapter{

    protected final List<PreOrderItem> items;

    public PreOrderAdapter(List<PreOrderItem> items) {
      this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pre_order_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
      return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

      TextView name, price;

      public ViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
      }
    }
  }
}
