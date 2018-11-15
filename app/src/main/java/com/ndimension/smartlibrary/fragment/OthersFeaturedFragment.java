package com.ndimension.smartlibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.ndimension.smartlibrary.adapter.MonthlyFeaturedAdapter;
import com.ndimension.smartlibrary.module.BookModule;
import com.ndimension.smartlibrary.utility.ConstantClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class OthersFeaturedFragment extends Fragment {
    View rootView;
    private RecyclerView rvMonthlyFeatured;
    private LinearLayoutManager layoutManager;
    private LinearLayout llLoader;
    private LinearLayout llNothing;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MonthlyFeaturedAdapter monthlyFeaturedAdapter;
    private ArrayList<BookModule> arrayList = new ArrayList<>();
    private EditText etSearch;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monthly_featured, container, false);
        this.rootView = rootView;
        initialize();
        getFeatureList();
        peformAction();



        return rootView;

    }

    private void initialize(){
        rvMonthlyFeatured = (RecyclerView)rootView.findViewById(R.id.rvMonthlyFeatured);
        layoutManager = new GridLayoutManager(getActivity(),3);
        rvMonthlyFeatured.setLayoutManager(layoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.all_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
        llLoader = (LinearLayout)rootView.findViewById(R.id.llLoader);
        llNothing = (LinearLayout)rootView.findViewById(R.id.llNothing);
        etSearch = (EditText)rootView.findViewById(R.id.etSearch);
    }

    private void peformAction(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFeatureList();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (arrayList.size()>0) {
                    filter(text);
                }else {
                    Toast.makeText(getActivity(),"No Item Found",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void filter(String text){
        ArrayList<BookModule> temp = new ArrayList();
        for(BookModule d: arrayList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase(Locale.getDefault()).contains(text)){

                temp.add(d);
            }
        }
        //update recyclerview
        monthlyFeaturedAdapter.updateList(temp);
    }

    private void getFeatureList(){
        arrayList.clear();
        llLoader.setVisibility(View.VISIBLE);
        rvMonthlyFeatured.setVisibility(View.GONE);
        llNothing.setVisibility(View.GONE);
        final JSONObject input = new JSONObject();
        try {
            input.put("category_type","Featured Books");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputFeaturedList",input.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL + "book/featuredbooklist", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseFeaturedList", response.toString());

                if (response.optBoolean("status")) {
                    //   Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray reslist = response.getJSONArray("result");
                        int reslistSize = reslist.length();
                        if (reslistSize>0){
                            for (int i = 0; i < reslistSize; i++) {
                                JSONObject res = reslist.optJSONObject(i);


                                String book_id=res.optString("book_id");
                                String book_title=res.optString("book_title");
                                String book_content=res.optString("book_content");
                                String book_author=res.optString("book_author");
                                String book_publish_date = res.optString("book_publish_date");
                                String book_img=ConstantClass.IMAGE_URL+res.optString("book_img");
                                String book_pdf_link = res.optString("book_pdf_link");
                                String book_qr_code = res.optString("book_qr_code");
                                String book_barcode_img = ConstantClass.BARCODE_URL+res.optString("book_barcode_img");
                                String season_type = res.optString("season_type");

                                if (season_type.equalsIgnoreCase("others")){
                                    arrayList.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,"Featured Books",book_barcode_img));
                                }


                            }

                            monthlyFeaturedAdapter = new MonthlyFeaturedAdapter(getActivity(),arrayList);
                            rvMonthlyFeatured.setAdapter(monthlyFeaturedAdapter);
                            llLoader.setVisibility(View.GONE);
                            rvMonthlyFeatured.setVisibility(View.VISIBLE);
                            llNothing.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);




                        }else {
                            llLoader.setVisibility(View.GONE);
                            rvMonthlyFeatured.setVisibility(View.GONE);
                            llNothing.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }



                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();
                    llLoader.setVisibility(View.GONE);
                    rvMonthlyFeatured.setVisibility(View.GONE);
                    llNothing.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);
    }
}
