package com.intense.yolo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.intense.yolo.R;
import com.intense.yolo.helper.BackToMain;

public class BookingPreviewFragment extends Fragment {

    private String pickupPlace, destPlace, minPrice, maxPrice;
    private BackToMain backToMain;

    public BookingPreviewFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_preview, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_booking_info);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backToMain.onBackInMain();

                /* FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack(); */

                /* Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("BookingPreview");
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                } */
            }
        });
        TextView tvPickupPlace = (TextView) view.findViewById(R.id.tv_pickup_place);
        TextView tvDestPlace = (TextView) view.findViewById(R.id.tv_dest_place);
        TextView tvMinPrice = (TextView) view.findViewById(R.id.tv_min_price);
        TextView tvPriceText = (TextView) view.findViewById(R.id.tv_price_text);
        TextView tvMaxPrice = (TextView) view.findViewById(R.id.tv_max_price);
        Button btnBookingNow = (Button) view.findViewById(R.id.btn_booking_now);
        RelativeLayout rlAirPortBookingContainer = (RelativeLayout) view.findViewById(R.id.rl_air_port_booking_container);

        if (getArguments() != null) {
            String status = getArguments().getString("status");
            pickupPlace = getArguments().getString("pickup_place");
            destPlace = getArguments().getString("dest_place");
            minPrice = getArguments().getString("min_price");
            assert status != null;
            if (status.equalsIgnoreCase("airport_booking")) {
                rlAirPortBookingContainer.setVisibility(View.VISIBLE);
                maxPrice = getArguments().getString("max_price");
                tvPriceText.setText("From");
                tvMaxPrice.setText(maxPrice);
            } else {
                tvPriceText.setText("From Start");
            }
            tvPickupPlace.setText(pickupPlace);
            tvDestPlace.setText(destPlace);
            tvMinPrice.setText(minPrice);
        }

        btnBookingNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("pickup_place", pickupPlace);
                bundle.putString("dest_place", destPlace);
                bundle.putString("min_price", minPrice);
                bundle.putString("max_price", maxPrice);

                bundle.putParcelable("pickup_lat_lng", new LatLng(23.8365, 90.3695));
                bundle.putParcelable("dest_lat_lng", new LatLng(23.7805, 90.4267));

                BookingFragment fragment = new BookingFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_main_activity, fragment, "Booking")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backToMain = (BackToMain) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}