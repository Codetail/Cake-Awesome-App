package com.uzcustomcake.core;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uzcustomcake.core.service.FirebaseDatabaseService;

/**
 * created at 9/30/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class CoreApplication extends Application {

  private FirebaseDatabaseService firebaseService;
  private MutableLiveData<DatabaseError> liveDatabaseErrors;

  @Override public void onCreate() {
    super.onCreate();
    FirebaseApp.initializeApp(this);

    final DatabaseReference reference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://uzcustomcakes.firebaseio.com/");

    liveDatabaseErrors = new MutableLiveData<>();

    firebaseService = new FirebaseDatabaseService(reference, liveDatabaseErrors);
  }

  public FirebaseDatabaseService firebaseService() {
    return firebaseService;
  }

  public MutableLiveData<DatabaseError> liveDatabaseErrors() {
    return liveDatabaseErrors;
  }
}
