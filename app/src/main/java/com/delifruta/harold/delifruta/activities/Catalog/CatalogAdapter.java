package com.delifruta.harold.delifruta.activities.Catalog;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.models.Product;

import java.util.List;

/**
 * Created by harold on 2/1/18.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder>{

    private List<Product> listCatalog;
    private Context context;

    public CatalogAdapter(List<Product> catalog, Context context) {
        this.listCatalog = catalog;
        this.context = context;
    }

    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_product_item, parent, false);

        return new CatalogAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, int position) {

        Product product = listCatalog.get(position);

        holder.tvProductTitle.setText(product.getName());
        holder.tvProductPrice.setText("" + product.getPrice());
        holder.productId = product.getId();

        Glide.with(context).load(product.getThumbnail()).override(200, 200).into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return listCatalog.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductTitle;
        private TextView tvProductPrice;
        private ImageView ivThumbnail;

        public Integer productId;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvProductTitle = (TextView) itemView.findViewById(R.id.text_title_product);
            tvProductPrice = (TextView) itemView.findViewById(R.id.text_price_product);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail_product);

            itemView.findViewById(R.id.card_view_catalog).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent productDetail = new Intent(context.getApplicationContext(), ProductDetailActivity.class);
                    productDetail.putExtra("productId", productId + "");
                    context.startActivity(productDetail);

                    Toast.makeText(itemView.getContext(), "Load", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }
}
