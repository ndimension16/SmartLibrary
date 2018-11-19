package com.ndimension.smartlibrary.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.NetworkConnectionCheck;
import com.ndimension.smartlibrary.utility.Pref;
import com.ndimension.smartlibrary.utility.ValidationUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp;
    private Button btnLogin;
    private EditText etEmail,etPassword;
    private Pref pref;
    private NetworkConnectionCheck connectionCheck;
    private TextView tvForgetPass;
    private Dialog dialog;
    private EditText etDialogEmail,etDialogPass;
    private LinearLayout llDialogSubmit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
        peformAction();
    }

    private void initialize(){
        tvSignUp = (TextView)findViewById(R.id.tvSignUp);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        pref = new Pref(this);
        tvForgetPass = findViewById(R.id.tvForgetPass);

        connectionCheck = new NetworkConnectionCheck(this);
    }

    private void peformAction(){
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (ValidationUtils.isValidEmail(etEmail.getText().toString())){
                   if (etEmail.getText().toString().length()>0){
                       if (etPassword.getText().toString().length()>0){
                           if (connectionCheck.isNetworkAvailable()) {
                              callLoginMethod();
                           }else {
                               connectionCheck.getNetworkActiveAlert().show();
                           }
                       }else {
                           etEmail.setError("Enter Password");
                           etEmail.requestFocus();
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

        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });
    }

    private void callLoginMethod(){
        final JSONObject input = new JSONObject();
        try {
            input.put("email",etEmail.getText().toString().trim());
            input.put("password",etPassword.getText().toString().trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputLogin",input.toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"login/user", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseLogin", response.toString());

                progressDialog.hide();

                if (response.optBoolean("status")){
                    String statusCode = response.optString("statusCode");
                    Toast.makeText(getApplicationContext(),response.optString("message"),Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = response.getJSONObject("result");

                        String user_id = jsonObject.optString("user_id");
                        pref.saveUserId(user_id);


                        String name = jsonObject.optString("fullname");
                        pref.saveName(name);

                        String email = jsonObject.optString("email");
                        pref.saveEmail(email);

                        String phone = jsonObject.optString("phone");
                        pref.saveMobile(phone);


                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
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

    private void showPopup(){
        dialog = new Dialog(LoginActivity.this,R.style.CustomDialogNew);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation2;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LinearLayout llCancel = (LinearLayout) dialog.findViewById(R.id.llCancel);
        etDialogEmail=(EditText)dialog.findViewById(R.id.etDialogEmail);
        etDialogPass = (EditText) dialog.findViewById(R.id.etDialogPass);
        llDialogSubmit=(LinearLayout)dialog.findViewById(R.id.llDialogSubmit);
        llDialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etDialogEmail.getText().toString().length()>0){
                    if (etDialogPass.getText().toString().length()>0){
                        if (connectionCheck.isNetworkAvailable()) {
                              callForgetPassword();
                        }else {
                            connectionCheck.getNetworkActiveAlert().show();
                        }
                        dialog.dismiss();
                    }else {
                        etDialogPass.setError("Enter New Password");
                        etDialogPass.requestFocus();
                    }

                }else {
                    etDialogEmail.setError("Enter Mobile");
                    etDialogEmail.requestFocus();
                }
            }
        });


        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                dialog.dismiss();


            }
        });
        dialog.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void callForgetPassword(){
        final JSONObject input = new JSONObject();
        try {
            input.put("email",etDialogEmail.getText().toString().trim());
            input.put("password",etDialogPass.getText().toString().trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputForgetPass",input.toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"password/forget", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseForgetPass", response.toString());

                progressDialog.hide();

                if (response.optBoolean("status")){
                    String statusCode = response.optString("statusCode");
                    Toast.makeText(getApplicationContext(),response.optString("message"),Toast.LENGTH_SHORT).show();
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
