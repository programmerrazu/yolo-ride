package com.intense.yolo.fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intense.yolo.R;
import com.intense.yolo.adapter.BookingAdapter;
import com.intense.yolo.entity.BookingInfo;
import com.intense.yolo.helper.BackToMain;
import com.intense.yolo.localdb.BookingDB;

import java.io.File;
import java.util.List;

public class BookingHistoryFragment extends Fragment {

    private BackToMain backToMain;

    public BookingHistoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);
        TextView tvNoHistoryMsg = (TextView) view.findViewById(R.id.tv_no_history_msg);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_booking_history);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backToMain.onBackInMain();

                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();*/

                /* Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("BookingHistory");
                if (fragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }*/
            }
        });

        RecyclerView rvBookingHistory = (RecyclerView) view.findViewById(R.id.recycler_view_booking_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvBookingHistory.setLayoutManager(layoutManager);
        rvBookingHistory.setHasFixedSize(true);
        rvBookingHistory.setNestedScrollingEnabled(false);

        File dbFilePath = getActivity().getApplicationContext().getDatabasePath("db_booking_info");
        if (dbFilePath.exists()) {
            List<BookingInfo> bookingInfoList = getDBInstance().bookingDao().getBookingInfo();
            BookingAdapter bookingAdapter = new BookingAdapter(bookingInfoList);
            rvBookingHistory.setAdapter(bookingAdapter);
        } else {
            tvNoHistoryMsg.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public BookingDB getDBInstance() {
        return Room.databaseBuilder(getActivity().getApplicationContext(), BookingDB.class, "db_booking_info").allowMainThreadQueries().build();
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