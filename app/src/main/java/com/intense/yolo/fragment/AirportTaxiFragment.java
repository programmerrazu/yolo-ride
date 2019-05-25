package com.intense.yolo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.intense.yolo.R;
import com.intense.yolo.adapter.DBLocationAdapter;
import com.intense.yolo.entity.DBLocationInfo;
import com.intense.yolo.helper.Commons;
import com.intense.yolo.networks.listener.StringDataParserListener;
import com.intense.yolo.networks.manager.data.DataManager;
import com.intense.yolo.presenter.DBLocationPresenter;
import com.intense.yolo.presenter.DBLocationPresenterImpl;
import com.intense.yolo.view.DBLocationView;

import org.json.JSONObject;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.intense.yolo.networks.AppsApi.PRICE_RANGE;

public class AirportTaxiFragment extends Fragment implements DBLocationView {

    private Context context;
    private ListView listViewDBLocation;
    private DBLocationAdapter locationAdapter;
    private EditText etDestSearch, etPickupSearch;
    private TextView tvPickupSign, tvDestSign;
    private ImageView ivPickupClear, ivDestClear;
    private static int pcs = 0, etClickNo = 0;
    private Boolean isPlacesCount = false;
    private LinearLayout llPickupContainer, llDestContainer;
    private String pickupPlace, destPlace;
    private InputMethodManager imm;
    private ProgressDialog pDialog;
    private DBLocationPresenter dbLocationPresenter;

    public AirportTaxiFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airport_taxi, container, false);
        dbLocationPresenter = new DBLocationPresenterImpl(this);
        dbLocationPresenter.getValidateLocation();
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        pDialog = new ProgressDialog(context);
        etDestSearch = (EditText) view.findViewById(R.id.et_dest_search);
        etPickupSearch = (EditText) view.findViewById(R.id.et_pickup_search);
        tvPickupSign = (TextView) view.findViewById(R.id.tv_pickup_sign);
        tvDestSign = (TextView) view.findViewById(R.id.tv_dest_sign);
        ivPickupClear = (ImageView) view.findViewById(R.id.iv_pickup_clear);
        ivDestClear = (ImageView) view.findViewById(R.id.iv_dest_clear);
        llPickupContainer = (LinearLayout) view.findViewById(R.id.ll_pickup_container);
        llDestContainer = (LinearLayout) view.findViewById(R.id.ll_dest_container);
        listViewDBLocation = (ListView) view.findViewById(R.id.list_view_db_location);
        return view;
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDBLocationError() {

    }

    @Override
    public void listDBLocation(List<DBLocationInfo> infoList) {
        locationAdapter = new DBLocationAdapter(context, infoList);
        listViewDBLocation.setAdapter(locationAdapter);
        searchDBLocation();
    }


    @Override
    public void showAlert(String message) {

    }

    private void searchDBLocation() {

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
                    /*if (locationAdapter != null) {
                        listViewDBLocation.setAdapter(locationAdapter);
                    }*/
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
                   /* if (locationAdapter != null) {
                        listViewDBLocation.setAdapter(locationAdapter);
                    } */
                } else {
                    ivPickupClear.setVisibility(View.GONE);
                }
                if (!s.toString().equals("")) {
                    locationAdapter.getFilter().filter(s);
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
                    /* if (locationAdapter != null) {
                        listViewDBLocation.setAdapter(locationAdapter);
                    } */
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
                    /* if (locationAdapter != null) {
                        listViewDBLocation.setAdapter(locationAdapter);
                    } */
                } else {
                    ivDestClear.setVisibility(View.GONE);
                }
                if (!s.toString().equals("")) {
                    locationAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listViewDBLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DBLocationInfo info = (DBLocationInfo) adapterView.getItemAtPosition(position);
                String dbLocation = info.getName();
                if (!dbLocation.equals("")) {
                    if (etClickNo == 1) {
                        isPlacesCount = true;
                        pickupPlace = dbLocation;
                        etPickupSearch.setText(pickupPlace);
                        etClickNo = 2;
                        /* if (locationAdapter != null) {
                            locationAdapter.clear();
                        } */
                        etDestSearch.requestFocus();
                        locationAdapter.getFilter().filter("");
                    } else if (etClickNo == 2) {
                        if (pickupPlace != null) {
                            destPlace = dbLocation;
                            hideKeyboard();
                            Commons.startWaitingDialog(pDialog);
                            etClickNo = 0;
                            if (locationAdapter != null) {
                                locationAdapter.clear();
                            }
                            getDBPrice();
                        } else {
                            etPickupSearch.requestFocus();
                            Toasty.warning(getActivity().getApplicationContext(), "Pickup location not found", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                } else {
                    Toasty.warning(getActivity().getApplicationContext(), "Location not found", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        etPickupSearch.requestFocus();
    }

    private void getDBPrice() {
        DataManager.taskManager(buildPriceRangeUrl(pickupPlace, destPlace), new StringDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(String response) {
                locationParser(response);
            }

            @Override
            public void onDataLoadFailed(String response) {
                Toasty.error(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void locationParser(String response) {
        JSONObject responseObj = null;
        try {
            if (response != null) {
                responseObj = new JSONObject(response);
                String min = responseObj.getString("min");
                String max = responseObj.getString("max");
                Bundle bundle = new Bundle();
                bundle.putString("status", "airport_booking");
                bundle.putString("pickup_place", pickupPlace);
                bundle.putString("dest_place", destPlace);
                bundle.putString("min_price", min);
                bundle.putString("max_price", max);
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

    private String buildPriceRangeUrl(String start, String end) {
        return PRICE_RANGE + "start=" + start.replace(" ", "+") + "&end=" + end.replace(" ", "+");
    }

    private void hideKeyboard() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(etDestSearch.getWindowToken(), 0);
        }
    }
}