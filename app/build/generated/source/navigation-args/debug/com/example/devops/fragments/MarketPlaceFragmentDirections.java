package com.example.devops.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.devops.R;

public class MarketPlaceFragmentDirections {
  private MarketPlaceFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMarketPlaceFragmentToDetailViewFragment() {
    return new ActionOnlyNavDirections(R.id.action_MarketPlaceFragment_to_detailViewFragment);
  }
}
