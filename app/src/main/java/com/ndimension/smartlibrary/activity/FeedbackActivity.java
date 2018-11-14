package com.ndimension.smartlibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.adapter.FeedbackAdapter;
import com.ndimension.smartlibrary.module.BookModule;
import com.ndimension.smartlibrary.module.FeedbackModule;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.Pref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<FeedbackModule> arrayList=new ArrayList<>();
    private RecyclerView rvFeedback;
    private LinearLayoutManager layoutManager;
    private LinearLayout llLoader;
    private LinearLayout llNothing;
    private SwipeRefreshLayout swipeRefreshLayout;
    FeedbackAdapter feedbackAdapter;
    Pref pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initialize();
        peformAction();
        getFeedbackList();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Feedback List");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        rvFeedback = (RecyclerView)findViewById(R.id.rvFeedback);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvFeedback.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.all_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
        llLoader = (LinearLayout)findViewById(R.id.llLoader);
        llNothing = (LinearLayout)findViewById(R.id.llNothing);

        pref = new Pref(this);
    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeedbackList();
            }
        });
    }

    private void getFeedbackList(){
        arrayList.clear();
        llLoader.setVisibility(View.VISIBLE);
        rvFeedback.setVisibility(View.GONE);
        llNothing.setVisibility(View.GONE);
        final JSONObject input = new JSONObject();
        try {
            input.put("user_id",pref.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputFeedbackList",input.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL + "feedback/feedbackretrieve", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseFeedbackList", response.toString());

                if (response.optBoolean("status")) {
                    //   Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray reslist = response.getJSONArray("result");
                        int reslistSize = reslist.length();
                        if (reslistSize>0){
                            for (int i = 0; i < reslistSize; i++) {
                                JSONObject res = reslist.optJSONObject(i);


                                String feedback_content=res.optString("feedback_content");
                                String feedback_created_date=res.optString("feedback_created_date");
                                String book_title=res.optString("book_title");
                                String book_author=res.optString("book_author");
                                String book_img=res.optString("book_img");



                                FeedbackModule feedbackModule = new FeedbackModule();
                                feedbackModule.setFeedback_content(feedback_content);
                                feedbackModule.setFeedback_created_date(feedback_created_date);
                                feedbackModule.setBook_title(book_title);
                                feedbackModule.setBook_author(book_author);
                                feedbackModule.setBook_img(book_img);


                                arrayList.add(feedbackModule);

                            }



                            feedbackAdapter = new FeedbackAdapter(FeedbackActivity.this,arrayList);
                            rvFeedback.setAdapter(feedbackAdapter);
                            llLoader.setVisibility(View.GONE);
                            rvFeedback.setVisibility(View.VISIBLE);
                            llNothing.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);




                        }else {
                            llLoader.setVisibility(View.GONE);
                            rvFeedback.setVisibility(View.GONE);
                            llNothing.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }



                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), response.optString("message"), Toast.LENGTH_SHORT).show();
                    llLoader.setVisibility(View.GONE);
                    rvFeedback.setVisibility(View.GONE);
                    llNothing.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjReq);
    }
}
