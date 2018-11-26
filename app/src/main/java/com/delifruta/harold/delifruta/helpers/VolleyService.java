package com.delifruta.harold.delifruta.helpers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.delifruta.harold.delifruta.interfaces.IResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by harold on 3/6/18.
 * https://stackoverflow.com/questions/35628142/how-to-make-separate-class-for-volley-library-and-call-all-method-of-volley-from
 */

public class VolleyService {


    IResult mResultCallback = null;
    Context mContext;
    Session session;

    public static String CLIENT_SERVICE = "frontend-client";
    public static String AUTH_KEY = "simplerestapi";
    public static String AUTHORIZATION = "";
    public static String USER_ID = "";

    public VolleyService(IResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;

        session = new Session(mContext);
        AUTHORIZATION = session.getToken();
        USER_ID = session.getUserId().toString();
    }

    public void postDataVolley(final String requestType, String url,JSONObject sendObj){
        try {

            //RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url,sendObj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=utf-8");
                    params.put("Client-Service", CLIENT_SERVICE);
                    params.put("Auth-Key", AUTH_KEY);
                    params.put("Authorization", AUTHORIZATION);
                    params.put("User-ID", USER_ID);

                    return params;
                }
            };

            SinglentonRequest.getInstance(mContext).addToRequestQueue(jsonObj);

        }catch(Exception e){

        }
    }

    public void getDataVolley(final String requestType, String url){
        try {
            //RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=utf-8");
                    params.put("Client-Service", CLIENT_SERVICE);
                    params.put("Auth-Key", AUTH_KEY);
                    params.put("Authorization", AUTHORIZATION);
                    params.put("User-ID", USER_ID);

                    return params;
                }
            };

            SinglentonRequest.getInstance(mContext).addToRequestQueue(jsonObj);

        }catch(Exception e){

        }
    }

}
