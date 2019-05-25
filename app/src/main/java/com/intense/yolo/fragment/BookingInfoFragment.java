package com.intense.yolo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.intense.yolo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingInfoFragment extends Fragment {

    private TextView tvJourneyDates, tvJourneyTimes, tvPickupLocation, tvDropOffLocation;
    private Spinner spinnerPickupType, spinnerPassenger, spinnerCheckInBags, spinnerCabinBags;

    public BookingInfoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_info, container, false);

        tvJourneyDates = (TextView) view.findViewById(R.id.tv_journey_dates);
        tvJourneyTimes = (TextView) view.findViewById(R.id.tv_journey_times);
        tvPickupLocation = (TextView) view.findViewById(R.id.tv_pickup_location);
        tvDropOffLocation = (TextView) view.findViewById(R.id.tv_drop_off_location);
        spinnerPickupType = (Spinner) view.findViewById(R.id.spinner_pickup_type);
        spinnerPassenger = (Spinner) view.findViewById(R.id.spinner_passenger);
        spinnerCheckInBags = (Spinner) view.findViewById(R.id.spinner_check_in_bags);
        spinnerCabinBags = (Spinner) view.findViewById(R.id.spinner_cabin_bags);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            String pickupPlace = getArguments().getString("pickup_place");
            String destPlace = getArguments().getString("dest_place");
            String date = getArguments().getString("date");
            String time = getArguments().getString("time");

            tvJourneyDates.setText(date);
            tvJourneyTimes.setText(time);
            tvPickupLocation.setText(pickupPlace);
            tvDropOffLocation.setText(destPlace);

        }

        String[] categories = getResources().getStringArray(R.array.pickup_type_arrays);
        List<String> stringList = new ArrayList<>(Arrays.asList(categories));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stringList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPickupType.setAdapter(dataAdapter);

        String[] passenger = getResources().getStringArray(R.array.passenger_arrays);
        List<String> passengerList = new ArrayList<>(Arrays.asList(passenger));
        ArrayAdapter<String> passengerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, passengerList);
        passengerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPassenger.setAdapter(passengerAdapter);

        String[] chkBag = getResources().getStringArray(R.array.passenger_arrays);
        List<String> chkBagList = new ArrayList<>(Arrays.asList(chkBag));
        ArrayAdapter<String> chkBagAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, chkBagList);
        chkBagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCheckInBags.setAdapter(chkBagAdapter);

        String[] cabinBag = getResources().getStringArray(R.array.passenger_arrays);
        List<String> cabinBagList = new ArrayList<>(Arrays.asList(cabinBag));
        ArrayAdapter<String> cabinBagAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cabinBagList);
        cabinBagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCabinBags.setAdapter(cabinBagAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}