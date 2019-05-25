package com.intense.yolo.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.intense.yolo.R;
import com.intense.yolo.adapter.PlaceAutocompleteAdapter;
import com.intense.yolo.helper.Commons;
import com.intense.yolo.helper.GoogleAPI;
import com.intense.yolo.networks.listener.StringDataParserListener;
import com.intense.yolo.networks.manager.data.DataManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.intense.yolo.networks.AppsApi.MILEAGE_PRICE;

public class LocationToLocationFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_BANGLADESH = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));
    private RecyclerView mRecyclerView;
    private PlaceAutocompleteAdapter mAdapter;
    private EditText etDestSearch, etPickupSearch;
    private TextView tvPickupSign, tvDestSign;
    private ImageView ivPickupClear, ivDestClear;
    private static int pcs = 0, etClickNo = 0;
    private Boolean isPlacesCount = false;
    private LinearLayout llPickupContainer, llDestContainer;
    private LatLng pickupLatLng, destLatLng;
    private String pickupPlace, destPlace;
    private GoogleAPI mService;
    private InputMethodManager imm;
    private ProgressDialog pDialog;

    public LocationToLocationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_to_location, container, false);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mService = Commons.getGoogleAPI();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        pDialog = new ProgressDialog(context);
        etDestSearch = (EditText) view.findViewById(R.id.et_dest_searchs);
        etPickupSearch = (EditText) view.findViewById(R.id.et_pickup_searchs);
        tvPickupSign = (TextView) view.findViewById(R.id.tv_pickup_signs);
        tvDestSign = (TextView) view.findViewById(R.id.tv_dest_signs);
        ivPickupClear = (ImageView) view.findViewById(R.id.iv_pickup_clears);
        ivDestClear = (ImageView) view.findViewById(R.id.iv_dest_clears);
        llPickupContainer = (LinearLayout) view.findViewById(R.id.ll_pickup_containers);
        llDestContainer = (LinearLayout) view.findViewById(R.id.ll_dest_containers);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_google_place_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        loadLocation();
        return view;
    }

    private void loadLocation() {

        mAdapter = new PlaceAutocompleteAdapter(R.layout.view_place_search, mGoogleApiClient, BOUNDS_BANGLADESH,
                null, new PlaceAutocompleteAdapter.PlaceAutoCompleteInterface() {
            @Override
            public void onPlaceClick(ArrayList<PlaceAutocompleteAdapter.PlaceAutocomplete> mResultList, int position) {
                if (mResultList != null) {
                    try {
                        final String placeId = String.valueOf(mResultList.get(position).placeId);
                        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
                        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(PlaceBuffer places) {
                                if (places.getCount() == 1) {
                                    /*Intent data = new Intent();
                                    data.putExtra("latitude", String.valueOf(places.get(0).getLatLng().latitude));
                                    data.putExtra("longitude", String.valueOf(places.get(0).getLatLng().longitude));
                                    getActivity().setResult(getActivity().RESULT_OK, data);*/
                                    if (etClickNo == 1) {
                                        isPlacesCount = true;
                                        pickupLatLng = places.get(0).getLatLng();
                                        pickupPlace = places.get(0).getAddress().toString();
                                        etPickupSearch.setText(pickupPlace);
                                        etClickNo = 2;
                                        if (mAdapter != null) {
                                            mAdapter.clearList();
                                        }
                                        etDestSearch.requestFocus();
                                    } else if (etClickNo == 2) {
                                        if (pickupPlace != null) {
                                            destLatLng = places.get(0).getLatLng();
                                            destPlace = places.get(0).getAddress().toString();
                                            hideKeyboard();
                                            Commons.startWaitingDialog(pDialog);
                                            etClickNo = 0;
                                            if (mAdapter != null) {
                                                mAdapter.clearList();
                                            }
                                            getDistance();
                                        } else {
                                            etPickupSearch.requestFocus();
                                            Toasty.warning(getActivity().getApplicationContext(), "Pickup location not found", Toast.LENGTH_SHORT, true).show();
                                        }
                                    }
                                } else {
                                    Toasty.warning(getActivity().getApplicationContext(), "something went wrong", Toast.LENGTH_LONG, true).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ivPickupClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPickupSearch.setText(null);
            }
        });

        ivDestClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestSearch.setText(null);
            }
        });

        etPickupSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pcs = 1;
                ivPickupClear.setVisibility(View.VISIBLE);
            }
        });

        etPickupSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean status) {
                etClickNo = 1;
                if (!isPlacesCount) {
                    etPickupSearch.setText(pickupPlace);
                }
                if (status) {
                    tvPickupSign.setBackgroundResource(R.drawable.bg_circle_black);
                    ivDestClear.setVisibility(View.GONE);
                    etDestSearch.getText().clear();
                    llDestContainer.setBackgroundColor(Color.parseColor("#FFF9F6F9"));
                    llPickupContainer.setBackgroundColor(Color.parseColor("#E7ECED"));
                } else {
                    tvPickupSign.setBackgroundResource(R.drawable.bg_circle);
                    isPlacesCount = false;
                }
            }
        });

        etPickupSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // if (!isNetworkAvailable()) {
                //    Toasty.warning(MainActivity.this, "Please check network connection", Toast.LENGTH_SHORT, true).show();
                //  }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    if (pcs == 1) {
                        ivPickupClear.setVisibility(View.VISIBLE);
                    }
                    if (mAdapter != null) {
                        mRecyclerView.setAdapter(mAdapter);
                    }
                } else {
                    ivPickupClear.setVisibility(View.GONE);
                }
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    mAdapter.getFilter().filter(s.toString());
                } else if (!mGoogleApiClient.isConnected()) {
                    Log.e("Main", "NOT CONNECTED");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etDestSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean status) {
                if (status) {
                    tvDestSign.setBackgroundColor(Color.BLACK);
                    etClickNo = 2;
                    ivPickupClear.setVisibility(View.GONE);
                    llPickupContainer.setBackgroundColor(Color.parseColor("#FFF9F6F9"));
                    llDestContainer.setBackgroundColor(Color.parseColor("#E7ECED"));
                } else {
                    tvDestSign.setBackgroundColor(Color.parseColor("#A4A4AC"));
                }
            }
        });

        etDestSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivPickupClear.setVisibility(View.GONE);
                llDestContainer.setBackgroundColor(Color.parseColor("#E7ECED"));
                llPickupContainer.setBackgroundColor(Color.parseColor("#FFF9F6F9"));
                //  if (!isNetworkAvailable()) {
                //    Toasty.warning(MainActivity.this, "Please check network connection", Toast.LENGTH_SHORT, true).show();
                // }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    ivDestClear.setVisibility(View.VISIBLE);
                    if (mAdapter != null) {
                        mRecyclerView.setAdapter(mAdapter);
                    }
                } else {
                    ivDestClear.setVisibility(View.GONE);
                }
                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
                    mAdapter.getFilter().filter(s.toString());
                } else if (!mGoogleApiClient.isConnected()) {
                    Log.e("Main", "NOT CONNECTED");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDistance() {

        try {
            mService.getPath(Commons.directionsApi(pickupLatLng, destLatLng, getActivity().getApplicationContext())).enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");

                        JSONObject object = jsonArray.getJSONObject(0);
                        JSONArray legs = object.getJSONArray("legs");
                        JSONObject legsObject = legs.getJSONObject(0);

                        JSONObject distanceObj = legsObject.getJSONObject("distance");
                        String distanceText = distanceObj.getString("text");
                        String distance = distanceText.replaceAll("[^0-9\\\\.]+", "");
                        getDBPrice(distance);

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

    private void getDBPrice(String distance) {
        DataManager.taskManager(buildMileagePriceUrl(distance), new StringDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(String response) {
                locationParser(response);
            }

            @Override
            public void onDataLoadFailed(String response) {
                Commons.stopWaitingDialog(pDialog);
                Toasty.error(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void locationParser(String response) {
        JSONObject responseObj = null;
        try {
            if (response != null) {
                responseObj = new JSONObject(response);
                String min = String.format("%.2f", Double.valueOf(responseObj.getString("price")));
                Bundle bundle = new Bundle();
                bundle.putString("status", "locationToLocation");
                bundle.putString("pickup_place", pickupPlace);
                bundle.putString("dest_place", destPlace);
                bundle.putString("min_price", min);
                BookingPreviewFragment fragment = new BookingPreviewFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_main_activity, fragment, "BookingPreview")
                        .addToBackStack(null)
                        .commit();
            } else {
                Toasty.error(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT, true).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Commons.stopWaitingDialog(pDialog);
    }

    private String buildMileagePriceUrl(String distance) {
        return MILEAGE_PRICE + distance;
    }

    private void hideKeyboard() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(etDestSearch.getWindowToken(), 0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}