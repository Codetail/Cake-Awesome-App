package com.uzcustomcake;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by horlock on 10/23/17.
 */

public class PriceFormatter {

  private final NumberFormat priceFormat;

  public PriceFormatter(Locale locale) {
    priceFormat = NumberFormat.getInstance(locale);
  }

  public String formatPrice(String price){
    return priceFormat.format(price) + " сум";
  }
}
