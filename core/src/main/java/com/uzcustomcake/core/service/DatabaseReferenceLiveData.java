package com.uzcustomcake.core.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

class DatabaseReferenceLiveData<T> extends LiveData<T> {

  private final DataSnapshotConverter<T> converter;
  private final MutableLiveData<DatabaseError> liveErrors;
  private final DatabaseReference reference;

  private final ValueEventListener onValueChange = new ValueEventListener() {
    @Override public void onDataChange(DataSnapshot dataSnapshot) {
      postValue(converter.map(dataSnapshot));
    }

    @Override public void onCancelled(DatabaseError databaseError) {
      liveErrors.postValue(databaseError);
    }
  };

  DatabaseReferenceLiveData(final DatabaseReference reference,
      final MutableLiveData<DatabaseError> liveErrors,
      final DataSnapshotConverter<T> converter) {
    this.converter = converter;
    this.reference = reference;
    this.liveErrors = liveErrors;
  }

  @Override protected void onActive() {
    super.onActive();
    reference.addValueEventListener(onValueChange);
  }

  @Override protected void onInactive() {
    super.onInactive();
    reference.removeEventListener(onValueChange);
  }

  interface DataSnapshotConverter<Out> {
    Out map(DataSnapshot input);
  }
}