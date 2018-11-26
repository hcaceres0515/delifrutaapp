package com.delifruta.harold.delifruta.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by harold on 3/3/18.
 */

public class SinglentonRequest {

    private static SinglentonRequest mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private SinglentonRequest(Context context) {

        mCtx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized SinglentonRequest getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new SinglentonRequest(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;

    }

    public <T>void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
