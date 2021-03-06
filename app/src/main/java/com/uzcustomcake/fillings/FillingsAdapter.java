package com.uzcustomcake.fillings;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.uzcustomcake.ItemClickListener;
import com.uzcustomcake.R;
import com.uzcustomcake.core.domain.Filling;
import java.util.List;

/**
 * Created by 00003130 on 10/21/17.
 */

public class FillingsAdapter extends RecyclerView.Adapter<FillingsAdapter.ViewHolder>
    implements ItemClickListener {

  final List<Filling> items;
  final View.OnClickListener onClickListener;
  private int lastSelectedPosition = -1;

  private final Drawable placeHolderDrawable = new ColorDrawable(Color.parseColor("#112244"));

  public FillingsAdapter(List<Filling> items, View.OnClickListener onClickListener) {
    this.items = items;
    this.onClickListener = onClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.layout_filling_view, viewGroup, false);
    return new ViewHolder(v, this);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int i) {
    final Filling filling = items.get(i);
    holder.title.setText(filling.name());
    String price = (int) filling.price() + " сум";
    holder.price.setText(price);
    holder.card.setTag(filling);
    if (lastSelectedPosition != i) {
      filling.setSelected(false);
      holder.selectedView.setVisibility(View.GONE);
    } else {
      filling.setSelected(true);
      holder.selectedView.setVisibility(View.VISIBLE);
    }

    if (filling.imageUrl() != null && !filling.imageUrl().isEmpty()) {
      holder.image.getViewTreeObserver()
          .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                Glide.with(holder.image.getContext().getApplicationContext())
                    .load(filling.imageUrl())
                    .apply(new RequestOptions().centerCrop().placeholder(placeHolderDrawable))
                    .into(holder.image);
              return true;
            }
          });
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public void onViewDetachedFromWindow(ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
  }

  @Override public void onClick(View v, int position) {
    onClickListener.onClick(v);
    if (lastSelectedPosition == position) {
      items.get(position).setSelected(false);
    } else {
      items.get(position).setSelected(true);
      notifyItemChanged(lastSelectedPosition);
    }
    lastSelectedPosition = position;
  }

  static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CardView card;
    ImageView image;
    TextView title, price;
    FrameLayout selectedView;
    ItemClickListener itemClickListener;

    public ViewHolder(View itemView, ItemClickListener onClickListener) {
      super(itemView);

      card = itemView.findViewById(R.id.card);
      image = itemView.findViewById(R.id.image);
      title = itemView.findViewById(R.id.title);
      price = itemView.findViewById(R.id.price);
      selectedView = itemView.findViewById(R.id.selectedView);
      itemView.setOnClickListener(this);
      itemClickListener = onClickListener;
    }

    @Override public void onClick(View v) {
      itemClickListener.onClick(v, getAdapterPosition());
      if (selectedView.getVisibility() == View.VISIBLE) {
        selectedView.setVisibility(View.GONE);
      } else {
        selectedView.setVisibility(View.VISIBLE);
      }
    }
  }
}