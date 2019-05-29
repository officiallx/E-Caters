package com.obnoxious.ecatering.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.models.Order;
import com.obnoxious.ecatering.models.Package;
import com.obnoxious.ecatering.utils.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    List<Order> orders;
    Context c;
    String eventType, selectedPackage, date, time, address, venue, orderId, tok;
    TextView txtEventName, txtEventPackage, txtEventDate, txtEventTime, txtEventVenue, txtEventAddress, txtOrderId;
    RelativeLayout rl_orders;
    ImageView imgRemove;
    Long orderid;
    CardView cv_order;

    private OrderAdapter.OnItemClickListener onClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OrderAdapter.OnItemClickListener listener){

        onClickListener = listener;

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements OnItemClickListener {

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
            rl_orders = itemView.findViewById(R.id.rl_order);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            cv_order = itemView.findViewById(R.id.cv_order);

            SharedPreferences use_token = c.getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
            tok = use_token.getString("USER_TOKEN", null);

            rl_orders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onClickListener.onItemClick(position);
                        }
                    }
                }
            });


        }

        public void bindMenu(Order order){

            if(order!=null) {
                eventType = order.getEventName();
                selectedPackage = order.getSelectedService();
                date = order.getEventDateTime().getEventDate();
                time = order.getEventDateTime().getEventTime();
                address = order.getEventAddress();
                venue = order.getEventVenue();
                orderId = order.getOrderId().toString();
                orderid = Long.valueOf(orderId);

                txtOrderId.setText("Order Id: " + orderId);
                txtEventName.setText(eventType);
                txtEventPackage.setText(selectedPackage);
                txtEventDate.setText(date);
                txtEventTime.setText(time);
                txtEventAddress.setText(address);
                txtEventVenue.setText(venue);
            }
            else{
                txtOrderId.setText("");
                txtEventName.setText("");
                txtEventPackage.setText("");
                txtEventDate.setText("");
                txtEventTime.setText("");
                txtEventAddress.setText("");
                txtEventVenue.setText("");
                cv_order.setVisibility(View.INVISIBLE);
                Toast.makeText(c, "No Orders! Why dont you order something first.", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onItemClick(int position) {
            //Toast.makeText(c, "Order Clicked", Toast.LENGTH_LONG).show();
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
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, final int i) {

        orderViewHolder.bindMenu(orders.get(i));

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrder();
                cv_order.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private void removeOrder() {
        try {
            final Call<Order> orderRemove = RetrofitBuilder
                    .getInstance()
                    .orderService()
                    .deleteOrderbyId(orderid, tok);

            orderRemove.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    Toast.makeText(c, "Removed", Toast.LENGTH_LONG).show();
                    cv_order.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            Log.d("Order", "removeOrder: "+e.getMessage());
        }
    }
}
