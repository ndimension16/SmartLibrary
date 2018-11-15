package com.ndimension.smartlibrary.FCM;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.Pref;


import org.json.JSONObject;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    Pref pref;

    @Override
    public void onTokenRefresh() {
         Log.d("SoumyaMessi","RS");
        //Getting registration token
        pref = new Pref(this);
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       Log.d("Soumyatoken1",refreshedToken);

        final JSONObject input = new JSONObject();
        try {
            input.put("user_id",pref.getUserId());
            input.put("user_token",refreshedToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputUpdateToken",input.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"book/updatetoken", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseUpdateToken", response.toString());

                if (response.optBoolean("status")){
                    String statusCode = response.optString("statusCode");


                }else {
                    String statusCode = response.optString("statusCode");

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjReq);

    }


    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
