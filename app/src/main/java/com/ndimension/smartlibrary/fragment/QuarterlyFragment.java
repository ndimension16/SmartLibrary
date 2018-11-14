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
import com.ndimension.smartlibrary.adapter.ShelfAdapter;
import com.ndimension.smartlibrary.module.BookModule;
import com.ndimension.smartlibrary.utility.ConstantClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class QuarterlyFragment extends Fragment {
    View rootView;
    private ArrayList<BookModule> bookList1=new ArrayList<>();
    private ArrayList<BookModule> bookList2=new ArrayList<>();
    private ArrayList<BookModule> bookList3=new ArrayList<>();
    private ArrayList<BookModule> bookList4=new ArrayList<>();
    private ArrayList<BookModule> bookList5=new ArrayList<>();
    private ArrayList<BookModule> bookList6=new ArrayList<>();
    private ArrayList<BookModule> bookList7=new ArrayList<>();
    private ArrayList<BookModule> bookList8=new ArrayList<>();
    private ArrayList<BookModule> bookList9=new ArrayList<>();
    private ArrayList<BookModule> bookList10 =new ArrayList<>();

    private RecyclerView rv1,rv2,rv3,rv4,rv5,rv6,rv7,rv8,rv9,rv10;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
    private ShelfAdapter shelfAdapter1,shelfAdapter2,shelfAdapter3,shelfAdapter4,shelfAdapter5,shelfAdapter6,shelfAdapter7,shelfAdapter8,shelfAdapter9,shelfAdapter10;
    private LinearLayout ll6,ll7,ll8,ll9,ll10;
    private LinearLayout llMain,llLoader;
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

        rv6 = (RecyclerView)rootView.findViewById(R.id.rv6);
        rv6.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv7 = (RecyclerView)rootView.findViewById(R.id.rv7);
        rv7.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv8 = (RecyclerView)rootView.findViewById(R.id.rv8);
        rv8.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv9 = (RecyclerView)rootView.findViewById(R.id.rv9);
        rv9.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rv10 = (RecyclerView)rootView.findViewById(R.id.rv10);
        rv10.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        tv1 = (TextView)rootView.findViewById(R.id.tv1);
        tv2 = (TextView)rootView.findViewById(R.id.tv2);
        tv3 = (TextView)rootView.findViewById(R.id.tv3);
        tv4 = (TextView)rootView.findViewById(R.id.tv4);
        tv5 = (TextView)rootView.findViewById(R.id.tv5);
        tv6 = (TextView)rootView.findViewById(R.id.tv6);
        tv7 = (TextView)rootView.findViewById(R.id.tv7);
        tv8 = (TextView)rootView.findViewById(R.id.tv8);
        tv9 = (TextView)rootView.findViewById(R.id.tv9);
        tv10 = (TextView)rootView.findViewById(R.id.tv10);

        ll6 = (LinearLayout)rootView.findViewById(R.id.ll6);
        ll7 = (LinearLayout)rootView.findViewById(R.id.ll7);
        ll8 = (LinearLayout)rootView.findViewById(R.id.ll8);
        ll9 = (LinearLayout)rootView.findViewById(R.id.ll9);
        ll10 = (LinearLayout)rootView.findViewById(R.id.ll10);

        llMain = (LinearLayout)rootView.findViewById(R.id.llMain);
        llLoader = (LinearLayout)rootView.findViewById(R.id.llLoader);




    }

    private void getBookList(){
        llLoader.setVisibility(View.VISIBLE);
        llMain.setVisibility(View.GONE);
        final JSONObject input = new JSONObject();
        try {
            input.put("type","quarterly");

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
                    llLoader.setVisibility(View.GONE);
                    llMain.setVisibility(View.VISIBLE);
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
                                    String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                    bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                           }else if (reslistSize==6){
                               ll6.setVisibility(View.VISIBLE);
                               ll7.setVisibility(View.GONE);
                               ll8.setVisibility(View.GONE);
                               ll9.setVisibility(View.GONE);
                               ll10.setVisibility(View.GONE);
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                               Iterator iterator6 = reslist.optJSONObject(5).keys();
                               String key6="";
                               key6 = (String)iterator6.next();

                               JSONObject res6 = reslist.optJSONObject(5);
                               JSONArray jsonArray6 = res6.getJSONArray(key6);
                               for (int i = 0; i < jsonArray6.length(); i++) {
                                   JSONObject res16 = jsonArray6.optJSONObject(i);
                                   String book_id = res16.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res16.optString("book_img");
                                   String book_title = res16.optString("book_title");
                                   String book_content = res16.optString("book_content");
                                   String book_author = res16.optString("book_author");
                                   String book_publish_date = res16.optString("book_publish_date");
                                   String book_pdf_link = res16.optString("book_pdf_link");
                                   String book_qr_code = res16.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res16.optString("book_barcode_img");
                                   bookList6.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key6,book_barcode_img));
                               }
                               shelfAdapter6 = new ShelfAdapter(getActivity(),bookList6);
                               rv6.setAdapter(shelfAdapter6);
                               tv6.setText(key6);

                           }else if (reslistSize==7){
                               ll6.setVisibility(View.VISIBLE);
                               ll7.setVisibility(View.VISIBLE);
                               ll8.setVisibility(View.GONE);
                               ll9.setVisibility(View.GONE);
                               ll10.setVisibility(View.GONE);
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                               Iterator iterator6 = reslist.optJSONObject(5).keys();
                               String key6="";
                               key6 = (String)iterator6.next();

                               JSONObject res6 = reslist.optJSONObject(5);
                               JSONArray jsonArray6 = res6.getJSONArray(key6);
                               for (int i = 0; i < jsonArray6.length(); i++) {
                                   JSONObject res16 = jsonArray6.optJSONObject(i);
                                   String book_id = res16.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res16.optString("book_img");
                                   String book_title = res16.optString("book_title");
                                   String book_content = res16.optString("book_content");
                                   String book_author = res16.optString("book_author");
                                   String book_publish_date = res16.optString("book_publish_date");
                                   String book_pdf_link = res16.optString("book_pdf_link");
                                   String book_qr_code = res16.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res16.optString("book_barcode_img");
                                   bookList6.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key6,book_barcode_img));
                               }
                               shelfAdapter6 = new ShelfAdapter(getActivity(),bookList6);
                               rv6.setAdapter(shelfAdapter6);
                               tv6.setText(key6);

                               Iterator iterator7 = reslist.optJSONObject(6).keys();
                               String key7="";
                               key7 = (String)iterator7.next();

                               JSONObject res7 = reslist.optJSONObject(6);
                               JSONArray jsonArray7= res7.getJSONArray(key7);
                               for (int i = 0; i < jsonArray7.length(); i++) {
                                   JSONObject res17 = jsonArray7.optJSONObject(i);
                                   String book_id = res17.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res17.optString("book_img");
                                   String book_title = res17.optString("book_title");
                                   String book_content = res17.optString("book_content");
                                   String book_author = res17.optString("book_author");
                                   String book_publish_date = res17.optString("book_publish_date");
                                   String book_pdf_link = res17.optString("book_pdf_link");
                                   String book_qr_code = res17.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res17.optString("book_barcode_img");
                                   bookList7.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key7,book_barcode_img));
                               }
                               shelfAdapter7 = new ShelfAdapter(getActivity(),bookList7);
                               rv7.setAdapter(shelfAdapter7);
                               tv7.setText(key7);

                           }else if (reslistSize==8){
                               ll6.setVisibility(View.VISIBLE);
                               ll7.setVisibility(View.VISIBLE);
                               ll8.setVisibility(View.VISIBLE);
                               ll9.setVisibility(View.GONE);
                               ll10.setVisibility(View.GONE);
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                               Iterator iterator6 = reslist.optJSONObject(5).keys();
                               String key6="";
                               key6 = (String)iterator6.next();

                               JSONObject res6 = reslist.optJSONObject(5);
                               JSONArray jsonArray6 = res6.getJSONArray(key6);
                               for (int i = 0; i < jsonArray6.length(); i++) {
                                   JSONObject res16 = jsonArray6.optJSONObject(i);
                                   String book_id = res16.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res16.optString("book_img");
                                   String book_title = res16.optString("book_title");
                                   String book_content = res16.optString("book_content");
                                   String book_author = res16.optString("book_author");
                                   String book_publish_date = res16.optString("book_publish_date");
                                   String book_pdf_link = res16.optString("book_pdf_link");
                                   String book_qr_code = res16.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res16.optString("book_barcode_img");
                                   bookList6.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key6,book_barcode_img));
                               }
                               shelfAdapter6 = new ShelfAdapter(getActivity(),bookList6);
                               rv6.setAdapter(shelfAdapter6);
                               tv6.setText(key6);

                               Iterator iterator7 = reslist.optJSONObject(6).keys();
                               String key7="";
                               key7 = (String)iterator7.next();

                               JSONObject res7 = reslist.optJSONObject(6);
                               JSONArray jsonArray7= res7.getJSONArray(key7);
                               for (int i = 0; i < jsonArray7.length(); i++) {
                                   JSONObject res17 = jsonArray7.optJSONObject(i);
                                   String book_id = res17.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res17.optString("book_img");
                                   String book_title = res17.optString("book_title");
                                   String book_content = res17.optString("book_content");
                                   String book_author = res17.optString("book_author");
                                   String book_publish_date = res17.optString("book_publish_date");
                                   String book_pdf_link = res17.optString("book_pdf_link");
                                   String book_qr_code = res17.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res17.optString("book_barcode_img");
                                   bookList7.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key7,book_barcode_img));
                               }
                               shelfAdapter7 = new ShelfAdapter(getActivity(),bookList7);
                               rv7.setAdapter(shelfAdapter7);
                               tv7.setText(key7);

                               Iterator iterator8 = reslist.optJSONObject(7).keys();
                               String key8="";
                               key8 = (String)iterator8.next();

                               JSONObject res8 = reslist.optJSONObject(7);
                               JSONArray jsonArray8= res8.getJSONArray(key8);
                               for (int i = 0; i < jsonArray8.length(); i++) {
                                   JSONObject res18 = jsonArray8.optJSONObject(i);
                                   String book_id = res18.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res18.optString("book_img");
                                   String book_title = res18.optString("book_title");
                                   String book_content = res18.optString("book_content");
                                   String book_author = res18.optString("book_author");
                                   String book_publish_date = res18.optString("book_publish_date");
                                   String book_pdf_link = res18.optString("book_pdf_link");
                                   String book_qr_code = res18.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res18.optString("book_barcode_img");
                                   bookList8.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key8,book_barcode_img));
                               }
                               shelfAdapter8 = new ShelfAdapter(getActivity(),bookList8);
                               rv8.setAdapter(shelfAdapter8);
                               tv8.setText(key8);

                           }else if (reslistSize==9){
                               ll6.setVisibility(View.VISIBLE);
                               ll7.setVisibility(View.VISIBLE);
                               ll8.setVisibility(View.VISIBLE);
                               ll9.setVisibility(View.VISIBLE);
                               ll10.setVisibility(View.GONE);
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                               Iterator iterator6 = reslist.optJSONObject(5).keys();
                               String key6="";
                               key6 = (String)iterator6.next();

                               JSONObject res6 = reslist.optJSONObject(5);
                               JSONArray jsonArray6 = res6.getJSONArray(key6);
                               for (int i = 0; i < jsonArray6.length(); i++) {
                                   JSONObject res16 = jsonArray6.optJSONObject(i);
                                   String book_id = res16.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res16.optString("book_img");
                                   String book_title = res16.optString("book_title");
                                   String book_content = res16.optString("book_content");
                                   String book_author = res16.optString("book_author");
                                   String book_publish_date = res16.optString("book_publish_date");
                                   String book_pdf_link = res16.optString("book_pdf_link");
                                   String book_qr_code = res16.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res16.optString("book_barcode_img");
                                   bookList6.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key6,book_barcode_img));
                               }
                               shelfAdapter6 = new ShelfAdapter(getActivity(),bookList6);
                               rv6.setAdapter(shelfAdapter6);
                               tv6.setText(key6);

                               Iterator iterator7 = reslist.optJSONObject(6).keys();
                               String key7="";
                               key7 = (String)iterator7.next();

                               JSONObject res7 = reslist.optJSONObject(6);
                               JSONArray jsonArray7= res7.getJSONArray(key7);
                               for (int i = 0; i < jsonArray7.length(); i++) {
                                   JSONObject res17 = jsonArray7.optJSONObject(i);
                                   String book_id = res17.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res17.optString("book_img");
                                   String book_title = res17.optString("book_title");
                                   String book_content = res17.optString("book_content");
                                   String book_author = res17.optString("book_author");
                                   String book_publish_date = res17.optString("book_publish_date");
                                   String book_pdf_link = res17.optString("book_pdf_link");
                                   String book_qr_code = res17.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res17.optString("book_barcode_img");
                                   bookList7.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key7,book_barcode_img));
                               }
                               shelfAdapter7 = new ShelfAdapter(getActivity(),bookList7);
                               rv7.setAdapter(shelfAdapter7);
                               tv7.setText(key7);

                               Iterator iterator8 = reslist.optJSONObject(7).keys();
                               String key8="";
                               key8 = (String)iterator8.next();

                               JSONObject res8 = reslist.optJSONObject(7);
                               JSONArray jsonArray8= res8.getJSONArray(key8);
                               for (int i = 0; i < jsonArray8.length(); i++) {
                                   JSONObject res18 = jsonArray8.optJSONObject(i);
                                   String book_id = res18.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res18.optString("book_img");
                                   String book_title = res18.optString("book_title");
                                   String book_content = res18.optString("book_content");
                                   String book_author = res18.optString("book_author");
                                   String book_publish_date = res18.optString("book_publish_date");
                                   String book_pdf_link = res18.optString("book_pdf_link");
                                   String book_qr_code = res18.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res18.optString("book_barcode_img");
                                   bookList8.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key8,book_barcode_img));
                               }
                               shelfAdapter8 = new ShelfAdapter(getActivity(),bookList8);
                               rv8.setAdapter(shelfAdapter8);
                               tv8.setText(key8);

                               Iterator iterator9 = reslist.optJSONObject(8).keys();
                               String key9="";
                               key9 = (String)iterator9.next();

                               JSONObject res9 = reslist.optJSONObject(8);
                               JSONArray jsonArray9= res9.getJSONArray(key9);
                               for (int i = 0; i < jsonArray9.length(); i++) {
                                   JSONObject res19 = jsonArray9.optJSONObject(i);
                                   String book_id = res19.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res19.optString("book_img");
                                   String book_title = res19.optString("book_title");
                                   String book_content = res19.optString("book_content");
                                   String book_author = res19.optString("book_author");
                                   String book_publish_date = res19.optString("book_publish_date");
                                   String book_pdf_link = res19.optString("book_pdf_link");
                                   String book_qr_code = res19.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res19.optString("book_barcode_img");
                                   bookList9.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key9,book_barcode_img));
                               }
                               shelfAdapter9 = new ShelfAdapter(getActivity(),bookList9);
                               rv9.setAdapter(shelfAdapter9);
                               tv9.setText(key9);

                           }else if (reslistSize==10){
                               ll6.setVisibility(View.VISIBLE);
                               ll7.setVisibility(View.VISIBLE);
                               ll8.setVisibility(View.VISIBLE);
                               ll9.setVisibility(View.VISIBLE);
                               ll10.setVisibility(View.VISIBLE);
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res11.optString("book_barcode_img");
                                   bookList1.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key1,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res12.optString("book_barcode_img");
                                   bookList2.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key2,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res13.optString("book_barcode_img");
                                   bookList3.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key3,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res14.optString("book_barcode_img");
                                   bookList4.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key4,book_barcode_img));
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
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res15.optString("book_barcode_img");
                                   bookList5.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key5,book_barcode_img));
                               }
                               shelfAdapter5 = new ShelfAdapter(getActivity(),bookList5);
                               rv5.setAdapter(shelfAdapter5);
                               tv5.setText(key5);

                               Iterator iterator6 = reslist.optJSONObject(5).keys();
                               String key6="";
                               key6 = (String)iterator6.next();

                               JSONObject res6 = reslist.optJSONObject(5);
                               JSONArray jsonArray6 = res6.getJSONArray(key6);
                               for (int i = 0; i < jsonArray6.length(); i++) {
                                   JSONObject res16 = jsonArray6.optJSONObject(i);
                                   String book_id = res16.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res16.optString("book_img");
                                   String book_title = res16.optString("book_title");
                                   String book_content = res16.optString("book_content");
                                   String book_author = res16.optString("book_author");
                                   String book_publish_date = res16.optString("book_publish_date");
                                   String book_pdf_link = res16.optString("book_pdf_link");
                                   String book_qr_code = res16.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res16.optString("book_barcode_img");
                                   bookList6.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key6,book_barcode_img));
                               }
                               shelfAdapter6 = new ShelfAdapter(getActivity(),bookList6);
                               rv6.setAdapter(shelfAdapter6);
                               tv6.setText(key6);

                               Iterator iterator7 = reslist.optJSONObject(6).keys();
                               String key7="";
                               key7 = (String)iterator7.next();

                               JSONObject res7 = reslist.optJSONObject(6);
                               JSONArray jsonArray7= res7.getJSONArray(key7);
                               for (int i = 0; i < jsonArray7.length(); i++) {
                                   JSONObject res17 = jsonArray7.optJSONObject(i);
                                   String book_id = res17.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res17.optString("book_img");
                                   String book_title = res17.optString("book_title");
                                   String book_content = res17.optString("book_content");
                                   String book_author = res17.optString("book_author");
                                   String book_publish_date = res17.optString("book_publish_date");
                                   String book_pdf_link = res17.optString("book_pdf_link");
                                   String book_qr_code = res17.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res17.optString("book_barcode_img");
                                   bookList7.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key7,book_barcode_img));
                               }
                               shelfAdapter7 = new ShelfAdapter(getActivity(),bookList7);
                               rv7.setAdapter(shelfAdapter7);
                               tv7.setText(key7);

                               Iterator iterator8 = reslist.optJSONObject(7).keys();
                               String key8="";
                               key8 = (String)iterator8.next();

                               JSONObject res8 = reslist.optJSONObject(7);
                               JSONArray jsonArray8= res8.getJSONArray(key8);
                               for (int i = 0; i < jsonArray8.length(); i++) {
                                   JSONObject res18 = jsonArray8.optJSONObject(i);
                                   String book_id = res18.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res18.optString("book_img");
                                   String book_title = res18.optString("book_title");
                                   String book_content = res18.optString("book_content");
                                   String book_author = res18.optString("book_author");
                                   String book_publish_date = res18.optString("book_publish_date");
                                   String book_pdf_link = res18.optString("book_pdf_link");
                                   String book_qr_code = res18.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res18.optString("book_barcode_img");
                                   bookList8.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key8,book_barcode_img));
                               }
                               shelfAdapter8 = new ShelfAdapter(getActivity(),bookList8);
                               rv8.setAdapter(shelfAdapter8);
                               tv8.setText(key8);

                               Iterator iterator9 = reslist.optJSONObject(8).keys();
                               String key9="";
                               key9 = (String)iterator9.next();

                               JSONObject res9 = reslist.optJSONObject(8);
                               JSONArray jsonArray9= res9.getJSONArray(key9);
                               for (int i = 0; i < jsonArray9.length(); i++) {
                                   JSONObject res19 = jsonArray9.optJSONObject(i);
                                   String book_id = res19.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res19.optString("book_img");
                                   String book_title = res19.optString("book_title");
                                   String book_content = res19.optString("book_content");
                                   String book_author = res19.optString("book_author");
                                   String book_publish_date = res19.optString("book_publish_date");
                                   String book_pdf_link = res19.optString("book_pdf_link");
                                   String book_qr_code = res19.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res19.optString("book_barcode_img");
                                   bookList9.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key9,book_barcode_img));
                               }
                               shelfAdapter9 = new ShelfAdapter(getActivity(),bookList9);
                               rv9.setAdapter(shelfAdapter9);
                               tv9.setText(key9);

                               Iterator iterator10 = reslist.optJSONObject(9).keys();
                               String key10="";
                               key10 = (String)iterator10.next();

                               JSONObject res10 = reslist.optJSONObject(9);
                               JSONArray jsonArray10= res10.getJSONArray(key10);
                               for (int i = 0; i < jsonArray10.length(); i++) {
                                   JSONObject res20 = jsonArray10.optJSONObject(i);
                                   String book_id = res20.optString("book_id");
                                   String book_img =  ConstantClass.IMAGE_URL+res20.optString("book_img");
                                   String book_title = res20.optString("book_title");
                                   String book_content = res20.optString("book_content");
                                   String book_author = res20.optString("book_author");
                                   String book_publish_date = res20.optString("book_publish_date");
                                   String book_pdf_link = res20.optString("book_pdf_link");
                                   String book_qr_code = res20.optString("book_qr_code");
                                   String book_barcode_img = ConstantClass.BARCODE_URL+res20.optString("book_barcode_img");
                                   bookList10.add(new BookModule(book_id,book_title,book_img,book_content,book_author,book_publish_date,book_pdf_link,book_qr_code,key10,book_barcode_img));
                               }
                               shelfAdapter10 = new ShelfAdapter(getActivity(),bookList10);
                               rv10.setAdapter(shelfAdapter10);
                               tv10.setText(key10);

                           }


                        }



                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } else {
                    llLoader.setVisibility(View.VISIBLE);
                    llMain.setVisibility(View.GONE);
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
