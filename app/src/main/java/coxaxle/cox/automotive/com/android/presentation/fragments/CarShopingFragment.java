package coxaxle.cox.automotive.com.android.presentation.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import coxaxle.cox.automotive.com.android.R;
import coxaxle.cox.automotive.com.android.presentation.DealerInventorySearchFiltersActivity;
import coxaxle.cox.automotive.com.android.presentation.DealerInventorySearchResultsActivity;
import coxaxle.cox.automotive.com.android.presentation.FavoriteCarsActivity;
import coxaxle.cox.automotive.com.android.presentation.HomeScreenActivity;
import coxaxle.cox.automotive.com.android.presentation.SavedSearchActivity;

public class CarShopingFragment extends Fragment {

    private ImageView calculatorsLearnMoreIv, vehicleValuesLearnMoreIv;
    private RelativeLayout calculatorsHeaderLayout, calculatorsSubLayout,
            vehicleValuesHeaderLayout, vehicleValuesSubLayout;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_car_shopping, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadViews();
    }

    void loadViews() {
        ((HomeScreenActivity) getActivity()).toolbar_title.setText("Car Shopping");
        ((HomeScreenActivity) getActivity()).toolbar_icon.setVisibility(View.INVISIBLE);
        ((HomeScreenActivity) getActivity()).toolbar_notifications_count.setVisibility(View.INVISIBLE);

        TextView dealerInventory = (TextView) view.findViewById(R.id.car_shopping_dealer_inventory_tv);

        dealerInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DealerInventorySearchResultsActivity.class);
                startActivity(intent);
            }

        });

        TextView savedSerches = (TextView) view.findViewById(R.id.car_shopping_saved_searches_tv);

        savedSerches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SavedSearchActivity.class);
                startActivity(intent);
            }

        });


        TextView favoriteCars = (TextView) view.findViewById(R.id.car_shopping_favorite_cars_tv);

        favoriteCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavoriteCarsActivity.class);
                startActivity(intent);
            }

        });


        calculatorsHeaderLayout = (RelativeLayout) view.findViewById(R.id.car_shopping_calculators_header_layout);
        calculatorsSubLayout = (RelativeLayout) view.findViewById(R.id.car_shopping_calculators_sub_layout);
        vehicleValuesHeaderLayout = (RelativeLayout) view.findViewById(R.id.car_shopping_vehicle_values_header_layout);
        vehicleValuesSubLayout = (RelativeLayout) view.findViewById(R.id.car_shopping_vehicle_values_sub_layout);
        calculatorsLearnMoreIv = (ImageView) view.findViewById(R.id.car_shopping_calculators_iv);
        vehicleValuesLearnMoreIv = (ImageView) view.findViewById(R.id.car_shopping_vehicle_values_iv);

        calculatorsSubLayout.setVisibility(View.GONE);
        vehicleValuesSubLayout.setVisibility(View.GONE);


        calculatorsHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calculatorsSubLayout.getVisibility() == View.VISIBLE) {

                    calculatorsSubLayout.setVisibility(View.GONE);
                    calculatorsLearnMoreIv.setRotation(0);


                } else {
                    calculatorsSubLayout.setVisibility(View.VISIBLE);
                    calculatorsLearnMoreIv.setRotation(90);

                }
            }
        });

        vehicleValuesHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicleValuesSubLayout.getVisibility() == View.VISIBLE) {
                    vehicleValuesSubLayout.setVisibility(View.GONE);
                    vehicleValuesLearnMoreIv.setRotation(0);
                } else {
                    vehicleValuesSubLayout.setVisibility(View.VISIBLE);
                    vehicleValuesLearnMoreIv.setRotation(90);
                }
            }
        });

    }
}