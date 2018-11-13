package com.ndimension.smartlibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ndimension.smartlibrary.adapter.ShelfAdapter;
import com.ndimension.smartlibrary.module.BookModule;
import com.ndimension.smartlibrary.utility.ConstantClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MonthlyFragment extends Fragment {
    View rootView;
    private ArrayList<BookModule> bookList1=new ArrayList<>();
    private ArrayList<BookModule> bookList2=new ArrayList<>();
    private ArrayList<BookModule> bookList3=new ArrayList<>();
    private ArrayList<BookModule> bookList4=new ArrayList<>();
    private ArrayList<BookModule> bookList5=new ArrayList<>();

    private RecyclerView rv1,rv2,rv3,rv4,rv5;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private ShelfAdapter shelfAdapter1,shelfAdapter2,shelfAdapter3,shelfAdapter4,shelfAdapter5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monthly, container, false);
        this.rootView = rootView;
        initialize();
        getBookList();


        return rootView;

    }

    private void initialize(){
        rv1 = (RecyclerView)rootView.findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv2 = (RecyclerView)rootView.findViewById(R.id.rv2);
        rv2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv3 = (RecyclerView)rootView.findViewById(R.id.rv3);
        rv3.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv4 = (RecyclerView)rootView.findViewById(R.id.rv4);
        rv4.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv5 = (RecyclerView)rootView.findViewById(R.id.rv5);
        rv5.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        tv1 = (TextView)rootView.findViewById(R.id.tv1);
        tv2 = (TextView)rootView.findViewById(R.id.tv2);
        tv3 = (TextView)rootView.findViewById(R.id.tv3);
        tv4 = (TextView)rootView.findViewById(R.id.tv4);
        tv5 = (TextView)rootView.findViewById(R.id.tv5);




    }

    private void getBookList(){
        final JSONObject input = new JSONObject();
        try {
            input.put("type","monthly");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputBookList1",input.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL + "book/booklist", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseBookList1", response.toString());

                if (response.optBoolean("status")) {
                    try {
                        JSONObject jsonObject = response.getJSONObject("result");
                        JSONArray reslist = jsonObject.getJSONArray("season");
                        int reslistSize = reslist.length();
                        if (reslistSize>0){
                           if (reslistSize==1){
                               Iterator iterator = reslist.optJSONObject(0).keys();
                               String key1="";
                               key1 = (String)iterator.next();

                               JSONObject res1 = reslist.optJSONObject(0);
                               JSONArray jsonArray1 = res1.getJSONArray(key1);
                               for (int i = 0; i < jsonArray1.length(); i++) {
                                   JSONObject res11 = jsonArray1.optJSONObject(i);
                                    String book_id = res11.optString("book_id");
                                    String book_img = ConstantClass.IMAGE_URL+res11.optString("book_img");
                                    String book_title = res11.optString("book_title");
                                    String book_content = res11.optString("book_content");
                                    String book_author = res11.optString("book_author");
                                    String book_publish_date = res11.optString("book_publish_date");
                                    String book_pdf_link = res11.optString("book_pdf_link");
                                    String book_qr_code = res11.optString("book_qr_code");
                                    bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1));
                               }

                               shelfAdapter1 = new ShelfAdapter(getActivity(),bookList1);
                               rv1.setAdapter(shelfAdapter1);
                               tv1.setText(key1);

                           }else if (reslistSize==2){
                               Iterator iterator = reslist.optJSONObject(0).keys();
                               String key1="";
                               key1 = (String)iterator.next();

                               JSONObject res1 = reslist.optJSONObject(0);
                               JSONArray jsonArray1 = res1.getJSONArray(key1);
                               for (int i = 0; i < jsonArray1.length(); i++) {
                                   JSONObject res11 = jsonArray1.optJSONObject(i);
                                   String book_id = res11.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res11.optString("book_img");
                                   String book_title = res11.optString("book_title");
                                   String book_content = res11.optString("book_content");
                                   String book_author = res11.optString("book_author");
                                   String book_publish_date = res11.optString("book_publish_date");
                                   String book_pdf_link = res11.optString("book_pdf_link");
                                   String book_qr_code = res11.optString("book_qr_code");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1));
                               }
                               shelfAdapter1 = new ShelfAdapter(getActivity(),bookList1);
                               rv1.setAdapter(shelfAdapter1);
                               tv1.setText(key1);

                               Iterator iterator2 = reslist.optJSONObject(1).keys();
                               String key2="";
                               key2 = (String)iterator2.next();


                               JSONObject res2 = reslist.optJSONObject(1);
                               JSONArray jsonArray2 = res2.getJSONArray(key2);
                               for (int i = 0; i < jsonArray2.length(); i++) {
                                   JSONObject res12 = jsonArray2.optJSONObject(i);
                                   String book_id = res12.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res12.optString("book_img");
                                   String book_title = res12.optString("book_title");
                                   String book_content = res12.optString("book_content");
                                   String book_author = res12.optString("book_author");
                                   String book_publish_date = res12.optString("book_publish_date");
                                   String book_pdf_link = res12.optString("book_pdf_link");
                                   String book_qr_code = res12.optString("book_qr_code");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2));
                               }
                               shelfAdapter2 = new ShelfAdapter(getActivity(),bookList2);
                               rv2.setAdapter(shelfAdapter2);
                               tv2.setText(key2);

                           }else if (reslistSize==3){
                               Iterator iterator = reslist.optJSONObject(0).keys();
                               String key1="";
                               key1 = (String)iterator.next();
                               Log.d("SoumyaKey1",key1);

                               JSONObject res1 = reslist.optJSONObject(0);
                               JSONArray jsonArray1 = res1.getJSONArray(key1);
                               for (int i = 0; i < jsonArray1.length(); i++) {
                                   JSONObject res11 = jsonArray1.optJSONObject(i);
                                   String book_id = res11.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res11.optString("book_img");
                                   String book_title = res11.optString("book_title");
                                   String book_content = res11.optString("book_content");
                                   String book_author = res11.optString("book_author");
                                   String book_publish_date = res11.optString("book_publish_date");
                                   String book_pdf_link = res11.optString("book_pdf_link");
                                   String book_qr_code = res11.optString("book_qr_code");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1));
                               }
                               shelfAdapter1 = new ShelfAdapter(getActivity(),bookList1);
                               rv1.setAdapter(shelfAdapter1);
                               tv1.setText(key1);

                               Iterator iterator2 = reslist.optJSONObject(1).keys();
                               String key2="";
                               key2 = (String)iterator2.next();
                               Log.d("SoumyaKey2",key2);

                               JSONObject res2 = reslist.optJSONObject(1);
                               JSONArray jsonArray2 = res2.getJSONArray(key2);
                               for (int i = 0; i < jsonArray2.length(); i++) {
                                   JSONObject res12 = jsonArray2.optJSONObject(i);
                                   String book_id = res12.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res12.optString("book_img");
                                   String book_title = res12.optString("book_title");
                                   String book_content = res12.optString("book_content");
                                   String book_author = res12.optString("book_author");
                                   String book_publish_date = res12.optString("book_publish_date");
                                   String book_pdf_link = res12.optString("book_pdf_link");
                                   String book_qr_code = res12.optString("book_qr_code");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2));
                               }
                               shelfAdapter2 = new ShelfAdapter(getActivity(),bookList2);
                               rv2.setAdapter(shelfAdapter2);
                               tv2.setText(key2);


                               Iterator iterator3 = reslist.optJSONObject(2).keys();
                               String key3="";
                               key3 = (String)iterator3.next();


                               JSONObject res3 = reslist.optJSONObject(2);
                               JSONArray jsonArray3 = res3.getJSONArray(key3);
                               for (int i = 0; i < jsonArray3.length(); i++) {
                                   JSONObject res13 = jsonArray3.optJSONObject(i);
                                   String book_id = res13.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res13.optString("book_img");
                                   String book_title = res13.optString("book_title");
                                   String book_content = res13.optString("book_content");
                                   String book_author = res13.optString("book_author");
                                   String book_publish_date = res13.optString("book_publish_date");
                                   String book_pdf_link = res13.optString("book_pdf_link");
                                   String book_qr_code = res13.optString("book_qr_code");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3));
                               }
                               shelfAdapter3 = new ShelfAdapter(getActivity(),bookList3);
                               rv3.setAdapter(shelfAdapter3);
                               tv3.setText(key3);

                           }else if (reslistSize==4){
                               Iterator iterator = reslist.optJSONObject(0).keys();
                               String key1="";
                               key1 = (String)iterator.next();
                               Log.d("SoumyaKey1",key1);

                               JSONObject res1 = reslist.optJSONObject(0);
                               JSONArray jsonArray1 = res1.getJSONArray(key1);
                               for (int i = 0; i < jsonArray1.length(); i++) {
                                   JSONObject res11 = jsonArray1.optJSONObject(i);
                                   String book_id = res11.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res11.optString("book_img");
                                   String book_title = res11.optString("book_title");
                                   String book_content = res11.optString("book_content");
                                   String book_author = res11.optString("book_author");
                                   String book_publish_date = res11.optString("book_publish_date");
                                   String book_pdf_link = res11.optString("book_pdf_link");
                                   String book_qr_code = res11.optString("book_qr_code");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1));
                               }
                               shelfAdapter1 = new ShelfAdapter(getActivity(),bookList1);
                               rv1.setAdapter(shelfAdapter1);
                               tv1.setText(key1);

                               Iterator iterator2 = reslist.optJSONObject(1).keys();
                               String key2="";
                               key2 = (String)iterator2.next();
                               Log.d("SoumyaKey2",key2);

                               JSONObject res2 = reslist.optJSONObject(1);
                               JSONArray jsonArray2 = res2.getJSONArray(key2);
                               for (int i = 0; i < jsonArray2.length(); i++) {
                                   JSONObject res12 = jsonArray2.optJSONObject(i);
                                   String book_id = res12.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res12.optString("book_img");
                                   String book_title = res12.optString("book_title");
                                   String book_content = res12.optString("book_content");
                                   String book_author = res12.optString("book_author");
                                   String book_publish_date = res12.optString("book_publish_date");
                                   String book_pdf_link = res12.optString("book_pdf_link");
                                   String book_qr_code = res12.optString("book_qr_code");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2));
                               }
                               shelfAdapter2 = new ShelfAdapter(getActivity(),bookList2);
                               rv2.setAdapter(shelfAdapter2);
                               tv2.setText(key2);


                               Iterator iterator3 = reslist.optJSONObject(2).keys();
                               String key3="";
                               key3 = (String)iterator3.next();

                               JSONObject res3 = reslist.optJSONObject(2);
                               JSONArray jsonArray3 = res3.getJSONArray(key3);
                               for (int i = 0; i < jsonArray3.length(); i++) {
                                   JSONObject res13 = jsonArray3.optJSONObject(i);
                                   String book_id = res13.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res13.optString("book_img");
                                   String book_title = res13.optString("book_title");
                                   String book_content = res13.optString("book_content");
                                   String book_author = res13.optString("book_author");
                                   String book_publish_date = res13.optString("book_publish_date");
                                   String book_pdf_link = res13.optString("book_pdf_link");
                                   String book_qr_code = res13.optString("book_qr_code");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3));
                               }
                               shelfAdapter3 = new ShelfAdapter(getActivity(),bookList3);
                               rv3.setAdapter(shelfAdapter3);
                               tv3.setText(key3);

                               Iterator iterator4 = reslist.optJSONObject(3).keys();
                               String key4="";
                               key4 = (String)iterator4.next();

                               JSONObject res4 = reslist.optJSONObject(3);
                               JSONArray jsonArray4 = res4.getJSONArray(key4);
                               for (int i = 0; i < jsonArray4.length(); i++) {
                                   JSONObject res14 = jsonArray4.optJSONObject(i);
                                   String book_id = res14.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res14.optString("book_img");
                                   String book_title = res14.optString("book_title");
                                   String book_content = res14.optString("book_content");
                                   String book_author = res14.optString("book_author");
                                   String book_publish_date = res14.optString("book_publish_date");
                                   String book_pdf_link = res14.optString("book_pdf_link");
                                   String book_qr_code = res14.optString("book_qr_code");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4));
                               }
                               shelfAdapter4 = new ShelfAdapter(getActivity(),bookList4);
                               rv4.setAdapter(shelfAdapter4);
                               tv4.setText(key4);

                           }else if (reslistSize==5){
                               Iterator iterator = reslist.optJSONObject(0).keys();
                               String key1="";
                               key1 = (String)iterator.next();
                               Log.d("SoumyaKey1",key1);

                               JSONObject res1 = reslist.optJSONObject(0);
                               JSONArray jsonArray1 = res1.getJSONArray(key1);
                               for (int i = 0; i < jsonArray1.length(); i++) {
                                   JSONObject res11 = jsonArray1.optJSONObject(i);
                                   String book_id = res11.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res11.optString("book_img");
                                   String book_title = res11.optString("book_title");
                                   String book_content = res11.optString("book_content");
                                   String book_author = res11.optString("book_author");
                                   String book_publish_date = res11.optString("book_publish_date");
                                   String book_pdf_link = res11.optString("book_pdf_link");
                                   String book_qr_code = res11.optString("book_qr_code");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1));
                               }
                               shelfAdapter1 = new ShelfAdapter(getActivity(),bookList1);
                               rv1.setAdapter(shelfAdapter1);
                               tv1.setText(key1);

                               Iterator iterator2 = reslist.optJSONObject(1).keys();
                               String key2="";
                               key2 = (String)iterator2.next();
                               Log.d("SoumyaKey2",key2);

                               JSONObject res2 = reslist.optJSONObject(1);
                               JSONArray jsonArray2 = res2.getJSONArray(key2);
                               for (int i = 0; i < jsonArray2.length(); i++) {
                                   JSONObject res12 = jsonArray2.optJSONObject(i);
                                   String book_id = res12.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res12.optString("book_img");
                                   String book_title = res12.optString("book_title");
                                   String book_content = res12.optString("book_content");
                                   String book_author = res12.optString("book_author");
                                   String book_publish_date = res12.optString("book_publish_date");
                                   String book_pdf_link = res12.optString("book_pdf_link");
                                   String book_qr_code = res12.optString("book_qr_code");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2));
                               }
                               shelfAdapter2 = new ShelfAdapter(getActivity(),bookList2);
                               rv2.setAdapter(shelfAdapter2);
                               tv2.setText(key2);


                               Iterator iterator3 = reslist.optJSONObject(2).keys();
                               String key3="";
                               key3 = (String)iterator3.next();

                               JSONObject res3 = reslist.optJSONObject(2);
                               JSONArray jsonArray3 = res3.getJSONArray(key3);
                               for (int i = 0; i < jsonArray3.length(); i++) {
                                   JSONObject res13 = jsonArray3.optJSONObject(i);
                                   String book_id = res13.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res13.optString("book_img");
                                   String book_title = res13.optString("book_title");
                                   String book_content = res13.optString("book_content");
                                   String book_author = res13.optString("book_author");
                                   String book_publish_date = res13.optString("book_publish_date");
                                   String book_pdf_link = res13.optString("book_pdf_link");
                                   String book_qr_code = res13.optString("book_qr_code");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3));
                               }
                               shelfAdapter3 = new ShelfAdapter(getActivity(),bookList3);
                               rv3.setAdapter(shelfAdapter3);
                               tv3.setText(key3);

                               Iterator iterator4 = reslist.optJSONObject(3).keys();
                               String key4="";
                               key4 = (String)iterator4.next();

                               JSONObject res4 = reslist.optJSONObject(3);
                               JSONArray jsonArray4 = res4.getJSONArray(key4);
                               for (int i = 0; i < jsonArray4.length(); i++) {
                                   JSONObject res14 = jsonArray4.optJSONObject(i);
                                   String book_id = res14.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res14.optString("book_img");
                                   String book_title = res14.optString("book_title");
                                   String book_content = res14.optString("book_content");
                                   String book_author = res14.optString("book_author");
                                   String book_publish_date = res14.optString("book_publish_date");
                                   String book_pdf_link = res14.optString("book_pdf_link");
                                   String book_qr_code = res14.optString("book_qr_code");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4));
                               }
                               shelfAdapter4 = new ShelfAdapter(getActivity(),bookList4);
                               rv4.setAdapter(shelfAdapter4);
                               tv4.setText(key4);

                               Iterator iterator5 = reslist.optJSONObject(4).keys();
                               String key5="";
                               key5 = (String)iterator5.next();

                               JSONObject res5 = reslist.optJSONObject(4);
                               JSONArray jsonArray5 = res5.getJSONArray(key5);
                               for (int i = 0; i < jsonArray5.length(); i++) {
                                   JSONObject res15 = jsonArray5.optJSONObject(i);
                                   String book_id = res15.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res15.optString("book_img");
                                   String book_title = res15.optString("book_title");
                                   String book_content = res15.optString("book_content");
                                   String book_author = res15.optString("book_author");
                                   String book_publish_date = res15.optString("book_publish_date");
                                   String book_pdf_link = res15.optString("book_pdf_link");
                                   String book_qr_code = res15.optString("book_qr_code");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                           }


                        }



                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();

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
