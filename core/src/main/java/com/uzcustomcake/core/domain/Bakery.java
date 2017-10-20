package com.uzcustomcake.core.domain;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * created at 9/30/17
 *
 * @author Ozodrukh
 * @version 1.0
 */
public class Bakery implements Parcelable {

  private final String name;
  private final String fillingId;
  private final String[] sort;

  public Bakery(String name, String fillingId, String[] sort) {
    this.name = name;
    this.sort = sort;
    this.fillingId = fillingId;
  }

  private Bakery(Parcel in) {
    name = in.readString();
    sort = in.createStringArray();
    fillingId = in.readString();
  }

  public static final Creator<Bakery> CREATOR = new Creator<Bakery>() {
    @Override
    public Bakery createFromParcel(Parcel in) {
      return new Bakery(in);
    }

    @Override
    public Bakery[] newArray(int size) {
      return new Bakery[size];
    }
  };

  public String name() {
    return name;
  }

  public String fillingId() {
    return fillingId;
  }

  public String[] sort() {
    return sort;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(fillingId);
    dest.writeStringArray(sort);
  }
}
