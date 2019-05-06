package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.models.Package;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    List<Order> orders;
    Context c;
    String eventType, selectedPackage, date, time, address, venue, orderId;
    TextView txtEventName, txtEventPackage, txtEventDate, txtEventTime, txtEventVenue, txtEventAddress, txtOrderId;

    private PackageAdapter.OnItemClickListener onClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(PackageAdapter.OnItemClickListener listener){

        onClickListener = listener;

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();

            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtEventName = itemView.findViewById(R.id.txtEvent);
            txtEventPackage = itemView.findViewById(R.id.txtSelectedPackage);
            txtEventDate = itemView.findViewById(R.id.txtEventDate);
            txtEventTime = itemView.findViewById(R.id.txtEventTime);
            txtEventVenue = itemView.findViewById(R.id.txtEventVenue);
            txtEventAddress = itemView.findViewById(R.id.txtEventAddress);
        }

        public void bindMenu(Order order){
            eventType = order.getEventName();
            selectedPackage = order.getSelectedService();
            date = order.getEventDateTime().getEventDate();
            time = order.getEventDateTime().getEventTime();
            address = order.getEventAddress();
            venue = order.getEventVenue();
            orderId = order.getOrderId().toString();

            txtOrderId.setText("Order Id: "+orderId);
            txtEventName.setText(eventType);
            txtEventPackage.setText(selectedPackage);
            txtEventDate.setText(date);
            txtEventTime.setText(time);
            txtEventAddress.setText(address);
            txtEventVenue.setText(venue);

        }
    }

    public OrderAdapter(List<Order> orders){
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_row,viewGroup,false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {

        orderViewHolder.bindMenu(orders.get(i));

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
