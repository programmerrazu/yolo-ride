package com.intense.yolo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.intense.yolo.R;
import com.intense.yolo.entity.BookingInfo;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {

    private List<BookingInfo> bookingInfoList;
    private Context context;

    public BookingAdapter(List<BookingInfo> bookingInfoList) {
        this.bookingInfoList = bookingInfoList;
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_history_adapter, viewGroup, false);
        return new BookingHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        if (bookingInfoList.size() != 0) {
            holder.tvPickupPlaces.setText(bookingInfoList.get(position).getPickupPlace());
            holder.tvDestPlaces.setText(bookingInfoList.get(position).getDestPlace());
            holder.tvJourneyDate.setText("Journey Date " + bookingInfoList.get(position).getJourneyDate());
            holder.tvJourneyTime.setText("Journey Time " + bookingInfoList.get(position).getJourneyTime());
            holder.tvBookingPrice.setText(bookingInfoList.get(position).getMinPrice());
            holder.tvVehicleName.setText(bookingInfoList.get(position).getVehicleName());
            holder.tvVehicleTypes.setText(bookingInfoList.get(position).getVehicleType());
            Glide.with(context)
                    .load(bookingInfoList.get(position).getVehicleImage())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .circleCrop()
                            .fitCenter())
                    .into(holder.ivVehicleImages);
        }
    }

    @Override
    public int getItemCount() {
        return bookingInfoList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BookingHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BookingHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    class BookingHolder extends RecyclerView.ViewHolder {

        private TextView tvPickupPlaces, tvDestPlaces, tvJourneyDate, tvJourneyTime, tvBookingPrice, tvVehicleName, tvVehicleTypes;
        private ImageView ivVehicleImages;

        BookingHolder(@NonNull View itemView) {
            super(itemView);
            tvPickupPlaces = itemView.findViewById(R.id.tv_pickup_places);
            tvDestPlaces = itemView.findViewById(R.id.tv_dest_places);
            tvJourneyDate = itemView.findViewById(R.id.tv_journey_date);
            tvJourneyTime = itemView.findViewById(R.id.tv_journey_time);
            tvBookingPrice = itemView.findViewById(R.id.tv_booking_price);
            tvVehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            tvVehicleTypes = itemView.findViewById(R.id.tv_vehicle_types);
            ivVehicleImages = itemView.findViewById(R.id.iv_vehicle_images);
        }
    }
}