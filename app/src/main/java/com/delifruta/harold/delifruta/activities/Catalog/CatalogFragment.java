package com.delifruta.harold.delifruta.activities.Catalog;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.activities.Login.LoginActivity;
import com.delifruta.harold.delifruta.activities.MainActivity;
import com.delifruta.harold.delifruta.activities.Orders.OrdersAdapter;
import com.delifruta.harold.delifruta.activities.Shopping.ShoppingCartFragment;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.Session;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;
import com.delifruta.harold.delifruta.models.Order;
import com.delifruta.harold.delifruta.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.delifruta.harold.delifruta.helpers.DbHelper.TAG;

/**
 * Created by harold on 1/4/18.
 */

public class CatalogFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;

    private List<Product> listProduct;

    private String URL_DATA = Constants.BASE_URL_SERVER_API + "products/get_all_products";

    FloatingActionButton btn_show_shopping_cart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, null);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_catalog);

        btn_show_shopping_cart = (FloatingActionButton) view.findViewById(R.id.show_shopping_cart);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        listProduct = new ArrayList<>();

        IResult mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley JSON post" + response);

                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++){
                        //Product product = new Product(("Product"), (i * 3.24));
                        JSONObject item = data.getJSONObject(i);

                        int id = item.getInt("ProdCod");
                        String name = item.getString("ProdNom");
                        String desc = item.getString("ProdDes");
                        Double price = item.getDouble("ProdPre");
                        String imgUrl = item.getString("image_url");

                        Product product = new Product(id, id, name, desc, price);

                        product.setThumbnail(Constants.IMAGE_URL_PRODUCTS + imgUrl);

                        listProduct.add(product);
                    }

                    adapter = new CatalogAdapter(listProduct, getContext());

                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + error.toString());
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
            }
        };

        VolleyService mVolleyService = new VolleyService(mResultCallback, this.getContext());

        mVolleyService.getDataVolley("get", URL_DATA);


        btn_show_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingCartFragment fragment = new ShoppingCartFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.screen_area, fragment).commit();

            }
        });



    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }
//
//    /**
//     * Converting dp to pixel
//     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }
}
