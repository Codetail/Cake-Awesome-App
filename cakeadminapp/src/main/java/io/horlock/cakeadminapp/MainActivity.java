package io.horlock.cakeadminapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DatabaseError;
import io.horlock.cakeadminapp.domain.Order;
import io.horlock.cakeadminapp.models.OrderViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private CompactCalendarView calendarView;
  private RecyclerView rvItems;
  private TextView selectedDay;
  private OrderViewModel model;
  private EventsAdapter adapter;
  private TextView noEvents;
  private List<Order> orderList;

  private final Observer<DatabaseError> errorObserver = new Observer<DatabaseError>() {
    @Override public void onChanged(@Nullable DatabaseError databaseError) {
      if (databaseError == null) {
        return;
      }

      databaseError.toException().printStackTrace();
    }
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    calendarView = findViewById(R.id.compactcalendar_view);
    rvItems = findViewById(R.id.rvItems);
    selectedDay = findViewById(R.id.selectedDay);
    noEvents = findViewById(R.id.noEvents);
    Calendar cal = Calendar.getInstance();
    selectedDay.setText(new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime()));
    rvItems.setLayoutManager(new LinearLayoutManager(this));

    ((CoreApplication) getApplication()).liveDatabaseErrors()
        .observe(this, errorObserver);

    model =
        ViewModelProviders.of(this).get(OrderViewModel.class);

    LiveData<Map<String, List<Order>>> data = model.getOrders();
    data.observe(this, new Observer<Map<String, List<Order>>>() {
      @Override public void onChanged(@Nullable Map<String, List<Order>> map) {
        populateEvents(map);
      }
    });

    calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
      @Override public void onDayClick(Date dateClicked) {
        selectedDay.setText(new SimpleDateFormat("dd MMMM yyyy").format(dateClicked));
        List<Event> events = calendarView.getEvents(dateClicked);
        if(events.isEmpty())
          noEvents.setVisibility(View.VISIBLE);
        else noEvents.setVisibility(View.GONE);
        orderList = new ArrayList<>();
        for(Event event : events){
          orderList.add((Order) event.getData());
        }
        adapter = new EventsAdapter(orderList, MainActivity.this);
        rvItems.setAdapter(adapter);
      }

      @Override public void onMonthScroll(Date firstDayOfNewMonth) {

      }
    });
  }

  private void populateEvents(Map<String, List<Order>> map) {
    calendarView.removeAllEvents();
    for (String key :map.keySet()){
      List<Event> events = new ArrayList<>();
      //for(Order order : map.get(key)) {
      //  order.phone = key;
      //  calendarView.addEvent(new Event(R.color.colorAccent, Long.parseLong(order.deliver_time),
      //      order), true);
      //}
      for(Order order : map.get(key)){
        order.phone = key;
        events.add(new Event(R.color.colorAccent, Long.parseLong(order.deliver_time), order));
      }

      calendarView.addEvents(events);
    }
  }

  @Override public void onClick(final View v) {
    AlertDialog dialog = new AlertDialog.Builder(this)
        .setMessage("Do you want to delete this object?")
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            Order order = (Order) v.getTag();
            orderList.remove(order.pos);
            adapter.notifyItemRemoved(order.pos);
            adapter.notifyDataSetChanged();
            calendarView.removeEvent(calendarView.getEvents(
                Long.parseLong(order.deliver_time)).get(order.pos), true);
            model.deleteOrder(order);
          }
        })
        .create();

    dialog.show();
  }
}
