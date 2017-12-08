package io.horlock.cakeadminapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import io.horlock.cakeadminapp.domain.Order;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.w3c.dom.Text;

/**
 * Created by horlock on 12/8/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

  private final List<Order> orders;
  private final View.OnClickListener onClickListener;

  public EventsAdapter(List<Order> orders, View.OnClickListener onClickListener) {
    this.orders = orders;
    this.onClickListener = onClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder h, int position) {
    Order order = orders.get(position);
    order.pos = position;
    h.address.setText(order.address);
    h.addings.setText(order.adding.subSequence(0, order.adding.length() - 1));
    h.name.setText(order.name);
    h.cakeType.setText(order.cake_type);
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.parseLong(order.deliver_time));
    h.deliverDate.setText(new SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.getTime()));
    h.phone.setText(order.phone);
    h.price.setText(order.price + " сум");
    h.delete.setTag(order);
    h.delete.setOnClickListener(onClickListener);
  }

  @Override public int getItemCount() {
    return orders.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder{

    TextView name, cakeType, addings, address, deliverDate, phone, price;
    ImageButton delete;

    public ViewHolder(View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.name);
      cakeType = itemView.findViewById(R.id.cakeType);
      addings = itemView.findViewById(R.id.addings);
      address = itemView.findViewById(R.id.address);
      deliverDate = itemView.findViewById(R.id.deliverDate);
      phone = itemView.findViewById(R.id.phone);
      price = itemView.findViewById(R.id.price);
      delete = itemView.findViewById(R.id.delete);
    }
  }
}
