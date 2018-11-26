package com.delifruta.harold.delifruta.activities.Orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.activities.Catalog.CatalogAdapter;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;
import com.delifruta.harold.delifruta.models.Order;
import com.delifruta.harold.delifruta.models.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.delifruta.harold.delifruta.helpers.DbHelper.TAG;

public class OrderDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    LinearLayout containerDeliveryInfo;
    TextView tv_delivery, tv_shipping_name, tv_shipping_address, tv_shipping_date, tv_total_order, tv_date_order;

    private Order order;
    private List<Product> listProducts;

    private String URL_DATA = Constants.BASE_URL_SERVER_API + "orders/get_order_detail_by_id/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        listProducts = new ArrayList<>();

        String orderId = getIntent().getStringExtra("order_id");
        String orderStr = getIntent().getStringExtra("obj_order");

        recyclerView = (RecyclerView) findViewById(R.id.productsOrderDetailRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        tv_delivery = (TextView) findViewById(R.id.delivery_order_detail);
        tv_shipping_name = (TextView) findViewById(R.id.shipping_name_order_detail);
        tv_shipping_address = (TextView) findViewById(R.id.shipping_address_order_detail);
        tv_shipping_date = (TextView) findViewById(R.id.shipping_date_order_detail);
        tv_total_order = (TextView) findViewById(R.id.total_order_detail);
        tv_date_order = (TextView) findViewById(R.id.date_order_detail);

        URL_DATA = URL_DATA + orderId;

        Log.d("Order id", orderId);

        Gson gS = new Gson();
        order = gS.fromJson(orderStr, Order.class);

        Log.d("OrderObjj", order.getShippingName());

        containerDeliveryInfo = (LinearLayout) findViewById(R.id.container_delivery_info_order_detail);

        containerDeliveryInfo.setVisibility(View.GONE);

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley JSON post" + response);

                try {
                    JSONArray data = response.getJSONArray("data");

                    boolean shipping = order.isShipping();

                    if (shipping) {
                        tv_delivery.setText("SI");
                        tv_shipping_name.setText(order.getShippingName());
                        tv_shipping_address.setText(order.getShippingAddress());
                        tv_shipping_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getDeliveryDate()));


                        containerDeliveryInfo.setVisibility(View.VISIBLE);
                    }

                    tv_total_order.setText("S/. " + order.getTotalOrder() + "");
                    tv_date_order.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreatedAt()));

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject item = data.getJSONObject(i);

                        int id = item.getInt("id");
                        String name = item.getString("ProdNom");
                        double price = item.getDouble("pricePro");
                        String imageUrl = item.getString("image_url");

                        imageUrl = Constants.IMAGE_URL_PRODUCTS + imageUrl;

                        Product prod = new Product(id, name, price);
                        prod.setThumbnail(imageUrl);

                        listProducts.add(prod);
                    }

                    Log.d("Details", data.toString());

                    adapter = new OrderDetailAdapter(listProducts, getApplicationContext());

                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + error.toString());
            }
        };

        VolleyService mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

        mVolleyService.getDataVolley("get", URL_DATA );


    }
}
