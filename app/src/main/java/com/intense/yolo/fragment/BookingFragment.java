package com.intense.yolo.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.intense.yolo.R;
import com.intense.yolo.adapter.VehicleAdapter;
import com.intense.yolo.entity.BookingInfo;
import com.intense.yolo.entity.VehicleInfo;
import com.intense.yolo.helper.Commons;
import com.intense.yolo.helper.GoogleAPI;
import com.intense.yolo.helper.OnItemClickListener;
import com.intense.yolo.localdb.BookingDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment implements OnMapReadyCallback {

    private RecyclerView rvVehicle;
    private TextView tvDatePicker, tvTimePicker, tvPickupsPlace, tvDestinationPlaces, tvDistance, tvDuration;
    private String pickupPlace, destPlace, distance, duration, minPrice, maxPrice;
    private GoogleMap bookingMap;
    private GoogleAPI mService;
    private List<LatLng> polyLineList;
    private ProgressDialog pDialog;
    private static final float MAP_ZOOM = 14.0f;
    private LatLng pickupLatLng, destLatLng;
    private List<VehicleInfo> vehicleInfoList;

    public BookingFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_booking);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
                /* Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("Booking");
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                } */
            }
        });
        tvPickupsPlace = (TextView) view.findViewById(R.id.tv_pickups_place);
        tvDestinationPlaces = (TextView) view.findViewById(R.id.tv_destination_places);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        rvVehicle = (RecyclerView) view.findViewById(R.id.recycler_view_vehicle);
        tvDatePicker = (TextView) view.findViewById(R.id.tv_date_picker);
        tvTimePicker = (TextView) view.findViewById(R.id.tv_time_picker);
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                tvDatePicker.setText(year + "-" + month + "-" + day);
                            }
                        }, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });
        tvTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                tvTimePicker.setText(hour + ":" + minute);
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mService = Commons.getGoogleAPI();
        pDialog = new ProgressDialog(getActivity());
        SupportMapFragment fragmentBookingMaps = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_booking_maps);
        assert fragmentBookingMaps != null;
        fragmentBookingMaps.getMapAsync(this);
        if (getArguments() != null) {
            pickupPlace = getArguments().getString("pickup_place");
            destPlace = getArguments().getString("dest_place");
            minPrice = getArguments().getString("min_price");
            maxPrice = getArguments().getString("max_price");
            pickupLatLng = getArguments().getParcelable("pickup_lat_lng");
            destLatLng = getArguments().getParcelable("dest_lat_lng");
            tvPickupsPlace.setText(pickupPlace);
            tvDestinationPlaces.setText(destPlace);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvVehicle.setLayoutManager(layoutManager);
        rvVehicle.setHasFixedSize(true);
        rvVehicle.setNestedScrollingEnabled(false);
        getVehicle();
    }

    private void getVehicle() {
        vehicleInfoList = new ArrayList<VehicleInfo>();

        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setImage("https://www.yoloridelondon.com/uploads/vehicle/VehicleTypeImg/Saloon-1471796082.jpg");
        vehicleInfo.setName("Maruti");
        vehicleInfo.setPassenger(4);
        vehicleInfo.setBaggage(3);
        vehicleInfo.setLuggage(1);
        vehicleInfo.setVehicleType("Saloon");
        vehicleInfo.setEstimateFare(53.20f);

        VehicleInfo vehicleInfo2 = new VehicleInfo();
        vehicleInfo2.setImage("https://www.yoloridelondon.com/uploads/vehicle/VehicleTypeImg/MPV-1471798367.jpg");
        vehicleInfo2.setName("BMW");
        vehicleInfo2.setPassenger(6);
        vehicleInfo2.setBaggage(4);
        vehicleInfo2.setLuggage(2);
        vehicleInfo2.setVehicleType("Estate");
        vehicleInfo2.setEstimateFare(58.20f);

        VehicleInfo vehicleInfo3 = new VehicleInfo();
        vehicleInfo3.setImage("https://www.yoloridelondon.com/uploads/vehicle/VehicleTypeImg/7%20Seater-1471798587.jpg");
        vehicleInfo3.setName("Suzuki");
        vehicleInfo3.setPassenger(5);
        vehicleInfo3.setBaggage(3);
        vehicleInfo3.setLuggage(2);
        vehicleInfo3.setVehicleType("Executive");
        vehicleInfo3.setEstimateFare(75.20f);

        vehicleInfoList.add(vehicleInfo);
        vehicleInfoList.add(vehicleInfo2);
        vehicleInfoList.add(vehicleInfo3);

        VehicleAdapter vehicleAdapter = new VehicleAdapter(vehicleInfoList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String name = vehicleInfoList.get(position).getName();
                String type = vehicleInfoList.get(position).getVehicleType();
                String image = vehicleInfoList.get(position).getImage();
                String fare = String.valueOf(vehicleInfoList.get(position).getEstimateFare());
                String date = tvDatePicker.getText().toString();
                String time = tvTimePicker.getText().toString();

                if (date.equalsIgnoreCase("")) {
                    snackBarMsg("Please select date");
                } else if (time.equalsIgnoreCase("")) {
                    snackBarMsg("Please select time");
                } else {
                    BookingInfo bookingInfo = new BookingInfo();

                    bookingInfo.setPickupPlace(pickupPlace);
                    bookingInfo.setDestPlace(destPlace);
                    bookingInfo.setMinPrice(minPrice);
                    bookingInfo.setMaxPrice(maxPrice == null ? "" : maxPrice);
                    bookingInfo.setEstimateFare(fare);
                    bookingInfo.setDistance(distance);
                    bookingInfo.setDuration(duration);
                    bookingInfo.setVehicleName(name);
                    bookingInfo.setVehicleType(type);
                    bookingInfo.setVehicleImage(image);
                    bookingInfo.setJourneyDate(date);
                    bookingInfo.setJourneyTime(time);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("pickup_place", pickupPlace);
                    bundle.putSerializable("dest_place", destPlace);
                    bundle.putSerializable("date", date);
                    bundle.putSerializable("time", time);


                    BookingInfoFragment fragment = new BookingInfoFragment();
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_main_activity, fragment, "BookingInfo")
                            .addToBackStack(null)
                            .commit();

                    // bookingSubmit(bookingInfo);
                }
            }
        });
        rvVehicle.setAdapter(vehicleAdapter);
    }

    private void bookingSubmit(BookingInfo bookingInfo) {
        long status = getDBInstance().bookingDao().insertBooking(bookingInfo);
        if (status < 1) {
            Toasty.error(getActivity().getApplicationContext(), "Booking submit failed", Toast.LENGTH_LONG, true).show();
        } else {
            Toasty.success(getActivity().getApplicationContext(), "Booking submit successful", Toast.LENGTH_LONG, true).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_main_activity, new BookingHistoryFragment(), "BookingHistory")
                    .addToBackStack(null)
                    .commit();
        }
    }

    public BookingDB getDBInstance() {
        return Room.databaseBuilder(getActivity().getApplicationContext(), BookingDB.class, "db_booking_info").allowMainThreadQueries().build();
    }

    private void snackBarMsg(String msg) {
        Snackbar snackbar = Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        bookingMap = googleMap;
        bookingMap.setTrafficEnabled(false);
        bookingMap.setIndoorEnabled(false);
        bookingMap.setBuildingsEnabled(false);
        bookingMap.getUiSettings().setZoomControlsEnabled(false);
        drawRoute();
    }

    private void drawRoute() {
        Commons.startWaitingDialog(pDialog);
        bookingMap.addMarker(new MarkerOptions().position(pickupLatLng).title("Pickup"));
        bookingMap.addMarker(new MarkerOptions().position(destLatLng).title("Destination"));
        bookingMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destLatLng, MAP_ZOOM));
        try {
            mService.getPath(Commons.directionsApi(pickupLatLng, destLatLng, getActivity().getApplicationContext())).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject route = jsonArray.getJSONObject(i);
                            JSONObject poly = route.getJSONObject("overview_polyline");
                            String polyLine = poly.getString("points");
                            polyLineList = Commons.decodePoly(polyLine);
                        }
                        JSONObject object = jsonArray.getJSONObject(0);
                        JSONArray legs = object.getJSONArray("legs");
                        JSONObject legsObject = legs.getJSONObject(0);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng latLng : polyLineList) {
                            builder.include(latLng);
                        }
                        LatLngBounds bounds = builder.build();
                        CameraUpdate destCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                        bookingMap.animateCamera(destCameraUpdate, new GoogleMap.CancelableCallback() {
                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onFinish() {
                                CameraUpdate zOut = CameraUpdateFactory.zoomBy(-1.0f);
                                bookingMap.animateCamera(zOut);
                            }
                        });

                        JSONObject distanceObj = legsObject.getJSONObject("distance");
                        String distanceText = distanceObj.getString("text");
                        distance = distanceText.replaceAll("[^0-9\\\\.]+", "");
                        tvDistance.setText("Total distance is " + distance + " km");

                        JSONObject durationObj = legsObject.getJSONObject("duration");
                        String durationText = durationObj.getString("text");
                        duration = durationText.replaceAll("[^0-9\\\\.]+", "");
                        tvDuration.setText("Journey time is " + duration + " min**(Approximate)");

                        Commons.stopWaitingDialog(pDialog);
                    } catch (Exception e) {
                        Commons.stopWaitingDialog(pDialog);
                        Toasty.warning(getActivity().getApplicationContext(), "No Points", Toast.LENGTH_LONG, true).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Commons.stopWaitingDialog(pDialog);
                    Toasty.warning(getActivity().getApplicationContext(), "No Points", Toast.LENGTH_LONG, true).show();
                }
            });
        } catch (Exception e) {
            Commons.stopWaitingDialog(pDialog);
            Toasty.warning(getActivity().getApplicationContext(), "No Points", Toast.LENGTH_LONG, true).show();
        }
    }
}