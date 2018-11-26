package com.delifruta.harold.delifruta.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by harold on 3/13/18.
 * https://github.com/mistermayne/android-login/blob/master/app/src/main/java/com/techobbyist/signuplogin/DbHelper.java
 */

public class Session {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {

        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("delifruta", Context.MODE_PRIVATE);
        editor = prefs.edit();

    }

    public void setLoggedin(boolean logggedin, String token, Integer userId){
        editor.putBoolean("loggedInmode",logggedin);
        editor.putString("token", token);
        editor.putInt("user_id", userId);
        editor.commit();
    }

    public boolean isLoggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

    public String getToken() {
        return prefs.getString("token", null);
    }

    public Integer getUserId() {
        return prefs.getInt("user_id", 0);
    }

}
