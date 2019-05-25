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
import com.intense.yolo.entity.VehicleInfo;
import com.intense.yolo.helper.OnItemClickListener;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {

    private Context context;
    private OnItemClickListener itemClickListener;
    private List<VehicleInfo> vehicleInfoList;

    public VehicleAdapter(List<VehicleInfo> vehicleInfoList, OnItemClickListener itemClickListener) {
        this.vehicleInfoList = vehicleInfoList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vehicle_adapter, viewGroup, false);
        return new VehicleHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        if (vehicleInfoList.size() != 0) {
            holder.tvPassenger.setText("X " + vehicleInfoList.get(position).getPassenger());
            holder.tvBaggage.setText("X " + vehicleInfoList.get(position).getBaggage());
            holder.tvLuggage.setText("X " + vehicleInfoList.get(position).getLuggage());
            holder.tvVehicleType.setText(vehicleInfoList.get(position).getVehicleType());
            holder.tvEstimateFare.setText("" + vehicleInfoList.get(position).getEstimateFare());
            Glide.with(context)
                    .load(vehicleInfoList.get(position).getImage())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .circleCrop()
                            .fitCenter())
                    .into(holder.ivVehicleImage);
        }
    }

    @Override
    public int getItemCount() {
        return vehicleInfoList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VehicleHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VehicleHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    class VehicleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivVehicleImage;
        private TextView tvPassenger, tvBaggage, tvLuggage, tvVehicleType, tvEstimateFare, tvBookingSubmit;

        VehicleHolder(@NonNull View itemView) {
            super(itemView);
            ivVehicleImage = (ImageView) itemView.findViewById(R.id.iv_vehicle_image);
            tvPassenger = (TextView) itemView.findViewById(R.id.tv_passenger);
            tvBaggage = (TextView) itemView.findViewById(R.id.tv_baggage);
            tvLuggage = (TextView) itemView.findViewById(R.id.tv_luggage);
            tvVehicleType = (TextView) itemView.findViewById(R.id.tv_vehicle_type);
            tvEstimateFare = (TextView) itemView.findViewById(R.id.tv_estimate_fare);
            tvBookingSubmit = (TextView) itemView.findViewById(R.id.tv_booking_submit);
            tvBookingSubmit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}