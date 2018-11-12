package com.ndimension.smartlibrary.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.NetworkConnectionCheck;
import com.ndimension.smartlibrary.utility.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private LinearLayout llSignIn;
    private EditText etEmail,etName,etPhoneNumber,etCreatePassword,etConfirmPassword;
    private Button btnSignUp;
    private NetworkConnectionCheck connectionCheck;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
        performAction();
    }

    private void initialize(){
        llSignIn = (LinearLayout)findViewById(R.id.llSignIn);

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etCreatePassword = findViewById(R.id.etCreatePassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        connectionCheck = new NetworkConnectionCheck(this);

    }

    private void performAction(){
        llSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidationUtils.isValidEmail(etEmail.getText().toString().trim())){
                    if (etEmail.getText().toString().length()>0){
                        if (etName.getText().toString().length()>0){
                            if (etPhoneNumber.getText().toString().length()>0){
                                if (etCreatePassword.getText().toString().length()>0){
                                    if (etConfirmPassword.getText().toString().length()>0){
                                        if (etConfirmPassword.getText().toString().equals(etCreatePassword.getText().toString())){
                                            if (connectionCheck.isNetworkAvailable()) {
                                                callRegistration(etEmail.getText().toString().trim(),etName.getText().toString().trim(),etPhoneNumber.getText().toString().trim(),etCreatePassword.getText().toString().trim());
                                            }else {
                                                connectionCheck.getNetworkActiveAlert().show();
                                            }
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Passwords are not matching",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        etConfirmPassword.setError("Enter Create Password");
                                        etConfirmPassword.requestFocus();
                                    }
                                }else {
                                    etCreatePassword.setError("Enter Create Password");
                                    etCreatePassword.requestFocus();
                                }
                            }else {
                                etPhoneNumber.setError("Enter Number");
                                etPhoneNumber.requestFocus();
                            }
                        }else {
                            etName.setError("Enter Name");
                            etName.requestFocus();
                        }
                    }else {
                        etEmail.setError("Enter Email");
                        etEmail.requestFocus();
                    }
                }else {
                   etEmail.setError("Enter Valid Email");
                   etEmail.requestFocus();
                }
            }
        });
    }

    private void callRegistration(final String email,final String name,final String mobile,final String pass){
        final JSONObject input = new JSONObject();
        try {
            input.put("email",email);
            input.put("password",pass);
            input.put("fullname",name);
            input.put("phone",mobile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputSignup",input.toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"register/user", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseSignup", response.toString());

                progressDialog.hide();

                if (response.optBoolean("status")){
                    String statusCode = response.optString("statusCode");
                    Toast.makeText(getApplicationContext(),response.optString("message"),Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else {
                    String statusCode = response.optString("statusCode");
                    Toast.makeText(getApplicationContext(),response.optString("message"),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();
            }
        });


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjReq);

    }


}
