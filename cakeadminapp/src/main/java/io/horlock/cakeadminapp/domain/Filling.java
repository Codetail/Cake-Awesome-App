package io.horlock.cakeadminapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * created at 9/30/17
 *
 * @author 00003130
 * @version 1.0
 */
public class Filling implements Parcelable {

  private final String name;
  private final String description;
  private final String imageUrl;
  private final double price;
  private final String type;
  private boolean isSelected;
  private final String comment;
  private final int amount;

  public Filling(String type, String name, String description, String imageUrl, double price,
      String comment, int amount) {
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.price = price;
    this.type = type;
    this.comment = comment;
    this.amount = amount;
  }

  protected Filling(Parcel in) {
    name = in.readString();
    description = in.readString();
    imageUrl = in.readString();
    price = in.readDouble();
    type = in.readString();
    comment = in.readString();
    amount = in.readInt();
  }

  public static final Creator<Filling> CREATOR = new Creator<Filling>() {
    @Override
    public Filling createFromParcel(Parcel in) {
      return new Filling(in);
    }

    @Override
    public Filling[] newArray(int size) {
      return new Filling[size];
    }
  };

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public String imageUrl() {
    return imageUrl;
  }

  public double price() {
    return price;
  }

  public void setSelected(boolean isSelected){
    this.isSelected = isSelected;
  }

  public boolean isSelected(){
    return isSelected;
  }

  public String type() {
    return type;
  }

  public String getComment() {
    return comment;
  }

  public int getAmount() {
    return amount;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(description);
    dest.writeString(imageUrl);
    dest.writeDouble(price);
    dest.writeString(type);
  }
}
