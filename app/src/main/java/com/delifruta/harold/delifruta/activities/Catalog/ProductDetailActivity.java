package com.delifruta.harold.delifruta.activities.Catalog;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.DbHelper;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;
import com.delifruta.harold.delifruta.models.OrderDetail;
import com.delifruta.harold.delifruta.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends Activity {

    ElegantNumberButton btn_product_amount;
    FloatingActionButton btn_add_cart_product;
    TextView product_name, product_price, product_description;

    Product prod = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        btn_product_amount = (ElegantNumberButton)findViewById(R.id.amount_product_detail);

        btn_add_cart_product = (FloatingActionButton)findViewById(R.id.add_cart_product_detail);

        product_name = (TextView) findViewById(R.id.product_name_prod_detail);
        product_price = (TextView) findViewById(R.id.product_price_prod_detail);

        final Integer productId = Integer.parseInt(getIntent().getStringExtra("productId"));

        Log.d("ProductId", productId.toString());

        IResult result = new IResult() {
            @Override
            public void notifySuccess(String requestType, JSONObject response) {
                Log.d("Response", response.toString());

                try {

                    JSONObject data = response.getJSONObject("data");

//                    JSONObject item = data.getJSONObject(0);

                    String productName = data.getString("ProdNom");
                    Double productPrice = data.getDouble("ProdPre");

                    prod.setName(productName);
                    prod.setPrice(productPrice);
                    prod.setThumbnail(data.getString("image_url"));

                    product_name.setText(productName);
                    product_price.setText("S/. " + productPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };

        VolleyService service =  new VolleyService(result, this.getApplicationContext());

        String url = Constants.BASE_URL_SERVER_API + "products/get_product_by_id/" + productId;
        service.getDataVolley("Get", url);

        btn_product_amount.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = btn_product_amount.getNumber();
                Log.d("NUM", num);
            }
        });

        btn_add_cart_product.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Msg", "Add Cart");

                Integer quantity = Integer.parseInt(btn_product_amount.getNumber());

                new DbHelper(getApplicationContext()).addToCart(
                        new OrderDetail(1, productId, prod.getName(), quantity, prod.getPrice(), prod.getThumbnail())
                );

                Toast.makeText(getApplicationContext(), "El producto ha sido agregado al carro de compra", Toast.LENGTH_SHORT).show();
                btn_product_amount.setNumber("0");
//                new DbHelper(getApplicationContext()).getCartItems();
            }
        });

    }


}
