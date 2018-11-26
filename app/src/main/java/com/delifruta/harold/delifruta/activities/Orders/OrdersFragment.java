package com.delifruta.harold.delifruta.activities.Orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;
import com.delifruta.harold.delifruta.models.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by harold on 1/19/18.
 */

public class OrdersFragment  extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Order> listOrders;

    private String URL_DATA = Constants.BASE_URL_SERVER_API + "orders/get_orders_by_user/6";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listOrders = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        IResult request = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d("Response", response.toString());

                try {

                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++){

                        JSONObject item = data.getJSONObject(i);
                        int orderId = item.getInt("id");
                        double totalOrder = item.getDouble("total_order");
                        String createdAt = item.getString("created_at");
                        String shippingName = item.getString("shipping_name");
                        String shippingPhone = item.getString("shipping_phone");
                        String shippingAddress = item.getString("shipping_address");
                        String addressReference = item.getString("address_reference");
                        double shippingPrice = item.getDouble("shipping_price");
                        String deliveryDateStr = item.getString("delivery_date");
                        String comments = item.getString("comments");
                        String createdDateStr = item.getString("created_at");

                        String  shipping = item.getString("shipping");
                        int distrito = item.getInt("IdDistrito");

                        boolean isShipping = shipping.equals("1") ? true : false;
                        Date deliveryDate = formatter.parse(deliveryDateStr);
                        Date createdDate = formatter.parse(createdDateStr);

                        Order newOrder = new Order(orderId, isShipping, distrito, shippingName, shippingPhone, shippingAddress, addressReference, shippingPrice, deliveryDate, totalOrder, comments, createdDate);

                        listOrders.add(newOrder);
                    }

                    adapter = new OrdersAdapter(listOrders, getContext());

                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };

        VolleyService volleyService = new VolleyService(request, this.getContext());
        volleyService.getDataVolley("GET", URL_DATA);





    }
}
