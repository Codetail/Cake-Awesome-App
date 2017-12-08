package io.horlock.cakeadminapp.service;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by horlock on 12/8/17.
 */

public class FirebaseService extends FirebaseMessagingService {

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
    
  }
}
