package com.delifruta.harold.delifruta.activities.Orders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.models.Product;


import java.util.List;

/**
 * Created by harold on 4/22/18.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private List<Product> listProducts;
    private Context context;

    public OrderDetailAdapter(List<Product> listProducts, Context context) {
        this.listProducts = listProducts;
        this.context = context;
    }


    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_order_detail_item, parent, false);

        return new OrderDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderDetailAdapter.ViewHolder holder, int position) {

        Product product = listProducts.get(position);

        holder.tvNameItem.setText(product.getName());
        holder.tvPriceItem.setText("S/. " + product.getPrice());

        Glide.with(context).load(product.getThumbnail()).override(120, 120).into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameItem;
        public TextView tvPriceItem;
        private ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameItem = (TextView) itemView.findViewById(R.id.name_order_detail_item);
            tvPriceItem = (TextView) itemView.findViewById(R.id.price_order_detail_item);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.image_order_detail_item);
        }


    }
}
