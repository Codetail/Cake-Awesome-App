package com.uzcustomcake;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.uzcustomcake.core.domain.Filling;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sher on 10/23/17.
 */

public class OrderViewModel extends AndroidViewModel {

    private Map<String, Filling> chosenFillings = new HashMap<>();

    public OrderViewModel(Application application) {
        super(application);
    }

    public Map<String, Filling> getChosenFillings(){
        return chosenFillings;
    }
}
