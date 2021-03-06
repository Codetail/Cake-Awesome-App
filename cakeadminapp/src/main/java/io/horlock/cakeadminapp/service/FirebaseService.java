package io.horlock.cakeadminapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.horlock.cakeadminapp.MainActivity;
import io.horlock.cakeadminapp.R;

/**
 * Created by 00003130 on 12/8/17.
 */

public class FirebaseService extends FirebaseMessagingService {

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    sendNotification();
  }

  private void sendNotification() {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentText("Вы получили новый заказ");
    PendingIntent contentIntent = PendingIntent.getActivity(this,
        0, new Intent(this, MainActivity.class),
        PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(contentIntent);
    NotificationManager notificationManager = (NotificationManager) getSystemService(
        Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0, builder.build());
  }
}
