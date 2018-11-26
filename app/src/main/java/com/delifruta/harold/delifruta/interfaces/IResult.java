package com.delifruta.harold.delifruta.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by harold on 3/6/18.
 */

public interface IResult {

    public void notifySuccess(String requestType, JSONObject response);
    public void notifyError(String requestType, VolleyError error);

}
