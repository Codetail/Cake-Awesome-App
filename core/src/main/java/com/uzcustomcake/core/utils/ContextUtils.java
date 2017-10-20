package com.uzcustomcake.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

/**
 * created at 10/5/17
 *
 * @author Ozodrukh
 * @version 1.0
 */

public class ContextUtils {

  public static <T extends Activity> T getActivity(@NonNull final Context context) {
    if (context instanceof Activity) {
      //noinspection unchecked
      return (T) context;
    } else if (context instanceof ContextWrapper) {
      return getActivity(((ContextWrapper) context).getBaseContext());
    } else {
      throw new IllegalStateException("Unknown Context type, can't retrieve Activity");
    }
  }
}
