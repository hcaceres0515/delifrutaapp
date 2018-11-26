package com.delifruta.harold.delifruta.activities.Shopping;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.helpers.DbHelper;
import com.delifruta.harold.delifruta.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harold on 5/31/18.
 */

public class ShoppingCartAdapter  extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>{

    private List<OrderDetail> listCart;
    private Context context;
    private Fragment fragment;

    public ShoppingCartAdapter(List<OrderDetail> listCart, Context context, Fragment fragment) {
        this.listCart = listCart;
        this.context = context;
        this.fragment = fragment;

        Log.d("Num Orders Adapter", this.listCart.size() + "");
    }

    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_cart_item, parent, false);

        return new ShoppingCartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShoppingCartAdapter.ViewHolder holder, int position) {

        OrderDetail orderDetail = listCart.get(position);
        holder.tvCartName.setText(orderDetail.getProdNom());
        holder.tvPrice.setText(orderDetail.getPricePro() + "");
        holder.productId = listCart.get(position).getId();

        Log.d("Detail", orderDetail.getProdNom() + " " + orderDetail.getPricePro());

        final int id = listCart.get(position).getId();

        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DbHelper(context).deleteCartItem(id);

                listCart.remove(0);
                notifyItemRemoved(0);

//                fragment.reloadFragment();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCartName, tvPrice;
        public ImageView imgCartQuantity;
        public Button btnDeleteItem;

        public int productId;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCartName = (TextView) itemView.findViewById(R.id.cart_item_name);
            tvPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
//            imgCartQuantity = itemView.findViewById(R.id.cart_item_quantity);
            btnDeleteItem = (Button) itemView.findViewById(R.id.btn_cart_delete_item);



        }
    }
}
