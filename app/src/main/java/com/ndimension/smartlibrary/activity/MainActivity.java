package com.ndimension.smartlibrary.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.adapter.HomePagerAdapter;
import com.ndimension.smartlibrary.fragment.MonthlyFragment;
import com.ndimension.smartlibrary.fragment.OthersFragment;
import com.ndimension.smartlibrary.fragment.QuarterlyFragment;
import com.ndimension.smartlibrary.fragment.YearlyFragment;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.Pref;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout dlMain;
    private TextView tvHome,tvFeatured,tvScanner,tvFeedback,tvShare,tvSettings,tvLogout;
    private ImageView imgHome,imgFeatured,imgScanner,imgFeedback,imgShare,imgSettings,imgLogout;
    private LinearLayout llHome,llFeatured,llScanner,llFeedback,llShare,llSettings,llLogout;
    private LinearLayout llMonthly,llQuarterly,llYearly,llOthers;
    private TextView tvMonthly,tvQuarterly,tvYearly,tvOthers;
    private View vMonthly,vQuarterly,vYearly,vOthers;
    FragmentManager fragmentmanager;

    private TextView tvName,tvEmail;
    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        peformAction();
        loadState();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Book Shelves");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);

        pref = new Pref(this);

        dlMain = findViewById(R.id.dlMain);

        llHome = (LinearLayout)findViewById(R.id.llHome);
        llFeatured = (LinearLayout)findViewById(R.id.llFeatured);
        llScanner = (LinearLayout)findViewById(R.id.llScanner);
        llFeedback = (LinearLayout)findViewById(R.id.llFeedback) ;
        llShare = (LinearLayout)findViewById(R.id.llShare);
        llSettings = (LinearLayout)findViewById(R.id.llSettings);
        llLogout = (LinearLayout)findViewById(R.id.llLogout);

        tvHome = (TextView)findViewById(R.id.tvHome);
        tvFeatured = (TextView)findViewById(R.id.tvFeatured);
        tvScanner = (TextView)findViewById(R.id.tvScanner);
        tvFeedback = (TextView)findViewById(R.id.tvFeedback) ;
        tvShare = (TextView)findViewById(R.id.tvShare);
        tvSettings = (TextView)findViewById(R.id.tvSettings);
        tvLogout = (TextView)findViewById(R.id.tvLogout);

        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgFeatured = (ImageView)findViewById(R.id.imgFeatured);
        imgScanner = (ImageView)findViewById(R.id.imgScanner);
        imgFeedback = (ImageView) findViewById(R.id.imgFeedback) ;
        imgShare = (ImageView)findViewById(R.id.imgShare);
        imgSettings = (ImageView) findViewById(R.id.imgSettings);
        imgLogout = (ImageView)findViewById(R.id.imgLogout);

        llMonthly = findViewById(R.id.llMonthly);
        llQuarterly = findViewById(R.id.llQuarterly);
        llYearly = findViewById(R.id.llYearly);
        llOthers = findViewById(R.id.llOthers);

        tvMonthly = findViewById(R.id.tvMonthly);
        tvQuarterly = findViewById(R.id.tvQuarterly);
        tvYearly = findViewById(R.id.tvYearly);
        tvOthers = findViewById(R.id.tvOthers);

        vMonthly = findViewById(R.id.vMonthly);
        vQuarterly = findViewById(R.id.vQuarterly);
        vYearly = findViewById(R.id.vYearly);
        vOthers = findViewById(R.id.vOthers);

        fragmentmanager= getSupportFragmentManager();

        tvName = (TextView)findViewById(R.id.tvName);
        tvEmail = (TextView)findViewById(R.id.tvEmail);

        tvName.setText(pref.getName());
        tvEmail.setText(pref.getEmail());


    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dlMain.isDrawerOpen(Gravity.START)) {
                    dlMain.openDrawer(Gravity.START);
                } else {
                    dlMain.closeDrawer(Gravity.START);
                }
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.colorAccent);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.colorAccent);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);


            }
        });

        llScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.colorAccent);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);

                startScanning();
            }
        });

        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.grey));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.colorAccent);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.colorAccent));

                dlMain.closeDrawer(Gravity.START);

                startActivity(new Intent(MainActivity.this,FeedbackActivity.class));


            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.colorAccent);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);

                startSharing();
            }
        });

        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.colorAccent);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor6 = getResources().getColor(R.color.grey);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int newColor = getResources().getColor(R.color.grey);
                imgHome.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                tvHome.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor2 = getResources().getColor(R.color.grey);
                imgFeatured.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                tvFeatured.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor3 = getResources().getColor(R.color.grey);
                imgScanner.setColorFilter(newColor3, PorterDuff.Mode.SRC_ATOP);
                tvScanner.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor4 = getResources().getColor(R.color.grey);
                imgShare.setColorFilter(newColor4, PorterDuff.Mode.SRC_ATOP);
                tvShare.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor5 = getResources().getColor(R.color.grey);
                imgSettings.setColorFilter(newColor5, PorterDuff.Mode.SRC_ATOP);
                tvSettings.setTextColor(getResources().getColor(R.color.text_color));

                final int newColor6 = getResources().getColor(R.color.colorAccent);
                imgLogout.setColorFilter(newColor6, PorterDuff.Mode.SRC_ATOP);
                tvLogout.setTextColor(getResources().getColor(R.color.colorAccent));

                final int newColor7 = getResources().getColor(R.color.grey);
                imgFeedback.setColorFilter(newColor7, PorterDuff.Mode.SRC_ATOP);
                tvFeedback.setTextColor(getResources().getColor(R.color.text_color));

                dlMain.closeDrawer(Gravity.START);

                pref.saveUserId("");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        llMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#0076fe"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.VISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState();


            }
        });

        llQuarterly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#0076fe"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.VISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState2();


            }
        });

        llYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#0076fe"));
                tvOthers.setTextColor(Color.parseColor("#807f80"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.VISIBLE);
                vOthers.setVisibility(View.INVISIBLE);

                loadState3();


            }
        });

        llOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMonthly.setTextColor(Color.parseColor("#807f80"));
                tvQuarterly.setTextColor(Color.parseColor("#807f80"));
                tvYearly.setTextColor(Color.parseColor("#807f80"));
                tvOthers.setTextColor(Color.parseColor("#0076fe"));

                vMonthly.setVisibility(View.INVISIBLE);
                vQuarterly.setVisibility(View.INVISIBLE);
                vYearly.setVisibility(View.INVISIBLE);
                vOthers.setVisibility(View.VISIBLE);

                loadState4();


            }
        });


    }

    private void loadState(){
        FragmentTransaction fragmenttransaction= fragmentmanager.beginTransaction();
        MonthlyFragment cf=new MonthlyFragment();
        fragmenttransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_right);
        fragmenttransaction.replace(R.id.flMain,cf,"Monthly Fragment");
        fragmenttransaction.commit();
    }

    private void loadState2(){
        FragmentTransaction fragmenttransaction= fragmentmanager.beginTransaction();
        QuarterlyFragment cf=new QuarterlyFragment();
        fragmenttransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_right);
        fragmenttransaction.replace(R.id.flMain,cf,"Quarterly Fragment");
        fragmenttransaction.commit();
    }

    private void loadState3(){
        FragmentTransaction fragmenttransaction= fragmentmanager.beginTransaction();
        YearlyFragment cf=new YearlyFragment();
        fragmenttransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_right);
        fragmenttransaction.replace(R.id.flMain,cf,"Yearly Fragment");
        fragmenttransaction.commit();
    }

    private void loadState4(){
        FragmentTransaction fragmenttransaction= fragmentmanager.beginTransaction();
        OthersFragment cf=new OthersFragment();
        fragmenttransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_to_right);
        fragmenttransaction.replace(R.id.flMain,cf,"Others Fragment");
        fragmenttransaction.commit();
    }

    private void startSharing(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "SmartLibrary");
            //  String sAux = "\nDownload and earn Money\n\n";
            //  sAux = sAux + "https://play.google.com/store/apps/details?id=com.deusexmachina.spintowin \n\n";
            String sAux = "https://play.google.com/store/apps/details?id=com.ndimension.smartlibrary\n"+"SmartLibrary: Reader App";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share Via"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void startScanning(){
        IntentIntegrator integrator=new IntentIntegrator(MainActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You Cancelled The Scanning", Toast.LENGTH_LONG).show();
            } else {
                String resultNew = result.getContents();
                callScanningMethod(resultNew);
                //startScanning();

            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void callScanningMethod(String qr_code){
        final JSONObject input = new JSONObject();
        try {
            input.put("qr_code",qr_code);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputCallScanning",input.toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"book/bookscandetails", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseCallScanning", response.toString());

                progressDialog.hide();

                if (response.optBoolean("status")){
                    String statusCode = response.optString("statusCode");
                    Toast.makeText(getApplicationContext(),response.optString("message"),Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = response.getJSONObject("result");
                        String book_id = jsonObject.optString("book_id");
                        String book_title = jsonObject.optString("book_title");
                        String book_content = jsonObject.optString("book_content");
                        String book_author = jsonObject.optString("book_author");
                        String book_publish_date = jsonObject.optString("book_publish_date");
                        String book_pdf_link = jsonObject.optString("book_pdf_link");
                        String book_qr_code = jsonObject.optString("book_qr_code");
                        String book_img = ConstantClass.IMAGE_URL+jsonObject.optString("book_img");
                        String book_barcode_img = ConstantClass.BARCODE_URL+jsonObject.optString("book_barcode_img");
                        String book_category_name = jsonObject.optString("book_category_name");




                        Intent intent = new Intent(MainActivity.this,BookActivity.class);
                        intent.putExtra("flag","normal");
                        intent.putExtra("book_id",book_id);
                        intent.putExtra("book_title",book_title);
                        intent.putExtra("book_content",book_content);
                        intent.putExtra("book_author",book_author);
                        intent.putExtra("book_publish_date",book_publish_date);
                        intent.putExtra("book_pdf_link",book_pdf_link);
                        intent.putExtra("book_qr_code",book_qr_code);
                        intent.putExtra("book_img",book_img);
                        intent.putExtra("category",book_category_name);
                        intent.putExtra("book_barcode_img",book_barcode_img);
                        startActivity(intent);


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


}
