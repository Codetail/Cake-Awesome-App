package io.horlock.cakeadminapp.service;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by horlock on 12/8/17.
 */

public class FirebaseIdService extends FirebaseInstanceIdService {

  @Override public void onTokenRefresh() {
    super.onTokenRefresh();

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d(TAG, "Refreshed token: " + refreshedToken);
  }
}
