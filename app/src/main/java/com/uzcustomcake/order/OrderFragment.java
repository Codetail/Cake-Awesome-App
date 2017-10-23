package com.uzcustomcake.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.uzcustomcake.MainActivity;
import com.uzcustomcake.R;

/**
 * Created by horlock on 10/23/17.
 */

public class OrderFragment extends Fragment {

  Button order;
  ImageView close;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.order_sheet, container, false);
    order = v.findViewById(R.id.order);
    close = v.findViewById(R.id.close);
    return v;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    order.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        checkIsEverythingWritten();
        ((MainActivity) getActivity()).sendDataToServer();
      }
    });
    close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ((MainActivity) getActivity()).hideBottomSheet();
      }
    });
  }

  private void checkIsEverythingWritten() {

  }
}
