package com.example.devops.fragments;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.devops.R;

public class HomeScreenDirections {
  private HomeScreenDirections() {
  }

  @NonNull
  public static NavDirections actionHomeScreenToYoungArtFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeScreen_to_YoungArtFragment);
  }

  @NonNull
  public static NavDirections actionHomeScreenToMarketPlaceFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeScreen_to_MarketPlaceFragment);
  }

  @NonNull
  public static NavDirections actionHomeScreenToShoppingCartFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeScreen_to_shoppingCartFragment);
  }

  @NonNull
  public static NavDirections actionHomeScreenToProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeScreen_to_ProfileFragment);
  }

  @NonNull
  public static NavDirections actionHomeScreenToGalleryFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeScreen_to_galleryFragment);
  }
}
