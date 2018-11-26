package com.delifruta.harold.delifruta.activities.Orders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.activities.Catalog.ProductDetailActivity;
import com.delifruta.harold.delifruta.models.Order;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by harold on 1/24/18.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> listOrders;
    private Context context;

    public OrdersAdapter(List<Order> listOrders, Context context) {
        this.listOrders = listOrders;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_order_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Order order = listOrders.get(position);

        holder.myOrder = order;

        holder.tvOrderTotalPrice.setText("S/. " + order.getTotalOrder());
        holder.tvOrderNumber.setText("Pedido N - " + position);
        holder.tvOrderCustomerName.setText(order.getShippingName());
        holder.tvOrderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreatedAt()));
    }

    @Override
    public int getItemCount() {

        return listOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOrderNumber;
        public TextView tvOrderTotalPrice;
        public TextView tvOrderDate;
        public TextView tvOrderCustomerName;

        public Order myOrder;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvOrderNumber = (TextView) itemView.findViewById(R.id.text_order_number_item);
            tvOrderTotalPrice = (TextView) itemView.findViewById(R.id.text_order_total_item);
            tvOrderCustomerName = (TextView) itemView.findViewById(R.id.text_order_customer_name);
            tvOrderDate = (TextView) itemView.findViewById(R.id.text_order_date_item);


            itemView.findViewById(R.id.bt_details_order_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent productDetail = new Intent(context.getApplicationContext(), OrderDetailActivity.class);

                    int orderId = myOrder.getId();
                    productDetail.putExtra("order_id", orderId +"");

                    Gson gS = new Gson();
                    String order = gS.toJson(myOrder);

                    productDetail.putExtra("obj_order", order);

                    context.startActivity(productDetail);

                    Toast.makeText(itemView.getContext(), "Load", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
