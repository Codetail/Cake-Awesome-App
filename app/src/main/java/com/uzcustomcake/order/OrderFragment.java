package com.uzcustomcake.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.uzcustomcake.MainActivity;
import com.uzcustomcake.OrderViewModel;
import com.uzcustomcake.R;

/**
 * Created by horlock on 10/23/17.
 */

public class OrderFragment extends Fragment {

  Button order;
  ImageView close;
  EditText name, phone, address, date;
  TextInputLayout nameLayout, phoneLayout, addressLayout, dateLayout;
  OrderViewModel model;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.order_sheet, container, false);
    order = v.findViewById(R.id.order);
    close = v.findViewById(R.id.close);
    name = v.findViewById(R.id.name);
    phone = v.findViewById(R.id.phone);
    address = v.findViewById(R.id.address);
    date = v.findViewById(R.id.date);
    nameLayout = v.findViewById(R.id.nameLayout);
    phoneLayout = v.findViewById(R.id.phoneLayout);
    addressLayout = v.findViewById(R.id.addressLayout);
    dateLayout = v.findViewById(R.id.dateLayout);

    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    model = ((MainActivity) getActivity()).getModel();

    order.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(everyThingIsDone()) {
          ((MainActivity) getActivity()).sendDataToServer();
        }
      }
    });
    close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          ((MainActivity) getActivity()).hideBottomSheet();
        }
    });
  }

  private boolean everyThingIsDone() {
    if(name.getText().toString().isEmpty()) {
      nameLayout.setError("Please enter your name");
      return false;
    }else if(phone.getText().toString().isEmpty()
        && !phone.getText().toString().startsWith("998") && phone.getText().toString().length() != 12){
      phoneLayout.setError("Please enter correct phone number");
      return false;
    }else if(address.getText().toString().isEmpty()){
      addressLayout.setError("Please enter your address");
      return false;
    }else if(date.getText().toString().isEmpty()){
      dateLayout.setError("Please enter deliver date by template e.g. 2017-10-24 15:00");
      return false;
    }

    model.saveUserData(name.getText().toString(), phone.getText().toString(),
        address.getText().toString(), date.getText().toString());
    return true;
  }
}
