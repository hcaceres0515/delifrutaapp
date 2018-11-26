package com.delifruta.harold.delifruta.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.delifruta.harold.delifruta.models.OrderDetail;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harold on 3/14/18.
 */

public class DbHelper extends SQLiteAssetHelper {

    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "Delifruta.db";
    public static final int DB_VERSION = 2;

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";

    public DbHelper(Context context) {

        super(context,DB_NAME, null, DB_VERSION);
        Log.d(TAG,"Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<OrderDetail> getCartItems() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"id", "product_id", "product_name", "quantity", "price", "img_url"};
        String sqlTable = "orders_detail";

        qb.setTables(sqlTable);

        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<OrderDetail> result = new ArrayList<>();

        if (c.moveToFirst()) {
            do {

                result.add(new OrderDetail(
                        c.getInt(c.getColumnIndex("id")),
                        c.getInt(c.getColumnIndex("product_id")),
                        c.getString(c.getColumnIndex("product_name")),
                        c.getInt(c.getColumnIndex("quantity")),
                        c.getDouble(c.getColumnIndex("price")),
                        c.getString(c.getColumnIndex("img_url"))
                        ));

            } while (c.moveToNext());
        }

        Log.d("OrderDetails Size", result.size() + "");

        return result;
    }

    public void addToCart(OrderDetail item) {

        SQLiteDatabase db = this.getReadableDatabase();
//        String query = String.format("INSERT INTO orders_detail(id, product_id, product_name, quantity, price, img_url) VALUES('%s', '%s', '%s', '%s', '%s', '%s');" ,
//                item.getId(),
//                item.getProdCod(),
//                item.getProdNom(),
//                item.getCantPro(),
//                item.getPricePro(),
//                item.getImgUrl()
//        );

        String query = "INSERT INTO orders_detail(product_id, product_name, quantity, price, img_url) VALUES(" +
                        item.getProdCod() + ", \"" +
                        item.getProdNom() + "\", " +
                        item.getCantPro() + ", " +
                        item.getPricePro() + ", \"" +
                        item.getImgUrl() + "\");";

        Log.d(TAG, query);

        db.execSQL(query);
        db.close();

//        ContentValues values =  new ContentValues();
//
//        values.put("product_id", item.getProdCod());
//        values.put("product_name", item.getProdNom());
//        values.put("quantity", item.getCantPro());
//        values.put("price", item.getPricePro());
//        values.put("img_url", item.getImgUrl());
//
//        db.insert("orders_detail", null, values);
//        db.close();

    }

    public void cleanCart() {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM orders_detail");

        db.execSQL(query);

    }

    public void deleteCartItem(int id) {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM orders_detail WHERE id="+id);

        db.execSQL(query);
    }
}
