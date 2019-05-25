package com.intense.yolo.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.intense.yolo.R;
import com.intense.yolo.fragment.BookingHistoryFragment;
import com.intense.yolo.fragment.BookingInfoFragment;
import com.intense.yolo.fragment.LocationFragment;
import com.intense.yolo.helper.BackToMain;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, BackToMain {

    private SupportMapFragment fragmentMaps;
    private GoogleMap yMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Boolean locationPermissionGranted, doubleBackPressed = false;
    private FloatingActionButton fabBtnLocation;
    private View btnLocation;
    private static final float MAP_ZOOM = 14.0f;
    private CircleImageView civBookingHistory;
    private CardView cardViewSearchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUIComponents();
    }

    private void setUIComponents() {

        fragmentMaps = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        assert fragmentMaps != null;
        fragmentMaps.getMapAsync(this);
        cardViewSearchContainer = (CardView) findViewById(R.id.card_view_search_container);
        fabBtnLocation = (FloatingActionButton) findViewById(R.id.fab_btn_location);

        cardViewSearchContainer.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_main_activity, new LocationFragment(), "Location")
                        .addToBackStack(null)
                        .commit();

                civBookingHistory.setVisibility(View.GONE);
                fabBtnLocation.setVisibility(View.GONE);
                cardViewSearchContainer.setVisibility(View.GONE);
            }
        });

        civBookingHistory = (CircleImageView) findViewById(R.id.civ_booking_history);
        civBookingHistory.setImageResource(R.drawable.ic_history);
        civBookingHistory.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                civBookingHistory.setVisibility(View.GONE);
                fabBtnLocation.setVisibility(View.GONE);
                cardViewSearchContainer.setVisibility(View.GONE);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_main_activity, new BookingInfoFragment(), "BookingHistory")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        onBackInMain();
    }

    private void snackBarMsg(String msg) {
        Snackbar snackbar = Snackbar.make(cardViewSearchContainer, msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        yMap = googleMap;
        yMap.setTrafficEnabled(false);
        yMap.setIndoorEnabled(false);
        yMap.setBuildingsEnabled(false);
        yMap.getUiSettings().setZoomControlsEnabled(false);

        getLocationPermission();
        getLocations();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getLocations() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng pickupLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    // yMap.addMarker(new MarkerOptions().position(pickupLatLng).title("Current Location"));
                    yMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickupLatLng, MAP_ZOOM));
                    yMap.setMyLocationEnabled(true);
                    customiseBtnLocation();
                    //getPickupAddress();
                }
            }
        });
    }

    private void customiseBtnLocation() {
        if (yMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                btnLocation = ((View) fragmentMaps.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                if (btnLocation != null) {
                    btnLocation.setVisibility(View.GONE);
                }
                fabBtnLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (yMap != null) {
                            if (btnLocation != null) {
                                btnLocation.callOnClick();
                            }
                        }
                    }
                });
            } else {
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {
                        locationPermissionGranted = true;
                        getLocations();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel(
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("You need to allow location access permission")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onBackInMain() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 1 || fm.getBackStackEntryCount() == 2) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            MainActivity.this.startActivity(intent);
            MainActivity.this.overridePendingTransition(0, 0);
            MainActivity.this.finish();
        } else if (fm.getBackStackEntryCount() == 3) {
            fm.popBackStack();
        } else {
            if (doubleBackPressed) {
                finish();
                // super.onBackPressed();
                // moveTaskToBack(true);
            } else {
                doubleBackPressed = true;
                snackBarMsg(getString(R.string.back_press));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackPressed = false;
                    }
                }, 2000);
            }
        }
    }
}