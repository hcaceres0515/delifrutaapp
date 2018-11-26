package com.delifruta.harold.delifruta.activities.Shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.activities.Catalog.CatalogAdapter;
import com.delifruta.harold.delifruta.helpers.DbHelper;
import com.delifruta.harold.delifruta.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harold on 4/23/18.
 */

public class ShoppingCartFragment extends Fragment {

    TextView tv_cart_total_products;
    FloatingActionButton btn_clean_cart;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<OrderDetail> listCart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shopping_cart, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_clean_cart = (FloatingActionButton)view.findViewById(R.id.btn_clean_cart);

        tv_cart_total_products = (TextView) view.findViewById(R.id.cart_total_products);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listCart = new ArrayList<>();

        listCart = new DbHelper(getContext()).getCartItems();

        Log.d("Num Orders", listCart.size() + "");

        adapter = new ShoppingCartAdapter(listCart, getContext(), ShoppingCartFragment.this);

        recyclerView.setAdapter(adapter);

        loadTotalCart();

        btn_clean_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", "Clean Cart");
                new DbHelper(getContext()).cleanCart();
            }
        });

    }

    public void loadTotalCart() {

        double total = 0;

        for (int i = 0; i < listCart.size(); i++) {
            total = total + (listCart.get(i).getPricePro() * listCart.get(i).getCantPro());
        }

        tv_cart_total_products.setText("S/. " + total + "");

    }

    public void reloadFragment() {

    }

}
