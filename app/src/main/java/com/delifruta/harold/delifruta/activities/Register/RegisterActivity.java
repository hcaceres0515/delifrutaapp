package com.delifruta.harold.delifruta.activities.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.delifruta.harold.delifruta.activities.MainActivity;
import com.delifruta.harold.delifruta.R;
import com.delifruta.harold.delifruta.config.Constants;
import com.delifruta.harold.delifruta.helpers.Session;
import com.delifruta.harold.delifruta.helpers.VolleyService;
import com.delifruta.harold.delifruta.interfaces.IResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private EditText et_name, et_email, et_phone, et_password;

    private String name, email, phone, password;

    private static String REGISTER_URL = Constants.BASE_URL_SERVER_API + "register/create_user";
    private final String TAG = "messages";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText) findViewById(R.id.input_name_register);
        et_email = (EditText) findViewById(R.id.input_email_register);
        et_password = (EditText) findViewById(R.id.input_password_register);
        et_phone = (EditText) findViewById(R.id.input_phone_register);
        btn_register = (Button) findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initialize();

                if (!validateData()) {

                    Toast.makeText(getBaseContext(), "Error en el formulario", Toast.LENGTH_SHORT).show();

                } else {

                    Map<String, String> params = new HashMap<String, String>();

//                    params.put("name", name);
//                    params.put("email", email);
//                    params.put("phone", phone);
//                    params.put("password", password);

                    params.put("name", "claudio");
                    params.put("email", "claudio@gmail.com");
                    params.put("phone", "979720947");
                    params.put("password", "145236");

                    JSONObject jsonObj = new JSONObject(params);

                    Log.d("params", jsonObj.toString());


                    IResult requestCallback = new IResult() {

                        @Override
                        public void notifySuccess(String requestType, JSONObject response) {

                            try {
                                int responseCode = response.getInt("status");

                                if (responseCode == HttpURLConnection.HTTP_OK) {

                                    String message = response.getString("message");
                                    String token = response.getString("token");
                                    int userId = response.getInt("user_id");

                                    Session session = new Session(getApplicationContext());

                                    session.setLoggedin(true, token, userId);

                                    Log.d(TAG, "Message" + session.getToken());
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent MainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(MainIntent);

                        }

                        @Override
                        public void notifyError(String requestType, VolleyError error) {
                            Log.d(TAG, "Volley requester " + error.toString());
                            Log.d(TAG, "Volley JSON post" + "That didn't work!");
                        }
                    };

                    VolleyService mVolleyService = new VolleyService(requestCallback, getApplicationContext());

                    mVolleyService.postDataVolley("POST", REGISTER_URL, jsonObj);


                }


            }
        });
    }

    public void initialize() {

        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        password = et_password.getText().toString().trim();

    }

    public boolean validateData() {

        boolean valid = true;

        if (name.isEmpty()) {
            et_name.setError("Por favor, ingrese un nombre valido");
            valid = false;
        }

        if (email.isEmpty()) {
            et_email.setError("Por favor, ingrese un email valido");
            valid = false;
        }

        if (phone.isEmpty()) {
            et_phone.setError("Por favor, ingrese un telefono valido");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 5) {
            et_password.setError("Por favor, ingrese una contraseña valido");
            valid = false;
        }

        return valid;

    }
}
