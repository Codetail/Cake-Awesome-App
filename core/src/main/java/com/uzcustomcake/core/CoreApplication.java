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
 * @author 00003130
 * @version 1.0
 */

public class CoreApplication extends Application {

  private FirebaseDatabaseService firebaseService;
  private String language = "US";
  private MutableLiveData<DatabaseError> liveDatabaseErrors;

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

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
