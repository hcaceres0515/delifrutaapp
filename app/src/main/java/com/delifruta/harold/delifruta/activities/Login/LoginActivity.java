package com.delifruta.harold.delifruta.activities.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.delifruta.harold.delifruta.activities.MainActivity;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.activities.Register.RegisterActivity;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.Session;
import com.delifruta.harold.delifruta.helpers.SinglentonRequest;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;
import com.delifruta.harold.delifruta.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    private Button btn_login;
    private EditText et_email;
    private EditText et_password;
    private String email, password;
    private Session session;

    public static final String LOGIN_URL = Constants.BASE_URL_SERVER_API + "auth/login";

    final String TAG = "messages";

    public LoginActivity() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        session = new Session(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.input_email_login);
        et_password = (EditText) findViewById(R.id.input_password_login);

        if (session.isLoggedin()) {
            startActivity(new Intent(this, MainActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                initialize();
//
//                if (validateData()) {
//
//                    Toast.makeText(getBaseContext(), "Error!!!", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                    Map<String, String> params = new HashMap<String, String>();
//
//                    params.put("email", "harold@gmail.com");
//                    params.put("password", "145236");
//
//                    JSONObject jsonObj = new JSONObject(params);
//
//                    //Log.d("response", jsonObj.toString());
//
//                    JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                            (Request.Method.POST, URL_DATA, jsonObj, new Response.Listener<JSONObject>() {
//
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    //mTxtDisplay.setText("Response: " + response.toString());
//
//                                    Log.d("response", response.toString());
//                                }
//                            }, new Response.ErrorListener() {
//
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    // TODO Auto-generated method stub
//                                    Log.d("Errorrrr", error.toString());
//                                }
//                            }){
//
//                                @Override
//                                public Map<String, String> getHeaders() throws AuthFailureError {
//                                    Map<String, String>  params = new HashMap<String, String>();
//                                    params.put("Content-Type", "application/json; charset=utf-8");
//                                    params.put("Client-Service", "frontend-client");
//                                    params.put("Auth-Key", "simplerestapi");
//
//                                    return params;
//                                }
//                            };
//
//
//                    SinglentonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
//
//                }

                initialize();

                if (!validateData()) {

                    Toast.makeText(getBaseContext(), "Error!!!", Toast.LENGTH_SHORT).show();

                } else {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", email);
                    params.put("password", password);

                    JSONObject jsonObj = new JSONObject(params);

                    Log.d("params", jsonObj.toString());

                    IResult mResultCallback = new IResult() {
                        @Override
                        public void notifySuccess(String requestType,JSONObject response) {
                            Log.d(TAG, "Volley requester " + requestType);
                            Log.d(TAG, "Volley JSON post" + response);
                            try {

                                int responseCode = response.getInt("status");

                                if (responseCode == HttpURLConnection.HTTP_OK) {

                                    String message = response.getString("message");
                                    String token = response.getString("token");
                                    int userId = response.getInt("user_id");

                                    Session session = new Session(getApplicationContext());

                                    session.setLoggedin(true, token, userId);

                                    User user = new User();
                                    user.setName("Harold Caceres");

                                    Log.d(TAG, "Message" + session.getToken());

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }

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

                    VolleyService mVolleyService = new VolleyService(mResultCallback, getApplicationContext());

                    //mVolleyService.getDataVolley("GETCALL","http://192.168.1.38/hospitalappointment/index.php?d=api&c=speciality&m=speciality_all_data");
                    mVolleyService.postDataVolley("POST", LOGIN_URL, jsonObj);

                }

            }

        });
    }

    public void registerActivity(View view) {

        Log.d("Log", "Go Register Activity");
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        registerIntent.putExtra("key", "12345678"); //Optional parameters
        LoginActivity.this.startActivity(registerIntent);
    }


    private void initialize() {

        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

    }

    private boolean validateData() {

        boolean valid = true;

        if (email.isEmpty()) {
            et_email.setError("Por favor, ingrese un email valido");
            valid = false;
        }

        if (password.isEmpty()) {
            et_password.setError("Por favor, ingrese una contraseña");
            valid = false;
        }

        return valid;
    }
}
