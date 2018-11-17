package com.ndimension.smartlibrary.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LongDef;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gun0912.tedpermission.PermissionListener;
import com.ndimension.smartlibrary.BuildConfig;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.ndimension.smartlibrary.utility.Pref;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imgQrCode,imgBook;
    private Button btnFeedback,btnRead;
    private AlertDialog alertDialog;
    private TextView tvTitle,tvAuthor,tvPublish,tvCategory;
    private EditText etContent;
    private String flag="";
    private String book_id="";
    private String book_pdf_link = "";
    private String book_qr_code = "";
    String barcode_img="";
    public static final int Progress_Dialog_Progress = 0;
    public static final int Progress_Dialog_Progress_2 = 1;
    URL url;
    URLConnection urlconnection ;
    int FileSize;
    InputStream inputstream;
    OutputStream outputstream;
    byte dataArray[] = new byte[1024];
    long totalSize = 0;
    ProgressDialog progressdialog;
    LinearLayout llMain;
    String text;
    String rootDir;
    String book_title;
    File outputFile;
    int SHARE_PDF_FLAG = 0;
    Pref pref;
    private int progressStatus = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initialize();
        peformAction();
    }

    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("Book Files");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        imgQrCode = (ImageView) findViewById(R.id.imgQrCode);
        imgBook = findViewById(R.id.imgBook);

        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvCategory = findViewById(R.id.tvCategory);
        tvPublish = findViewById(R.id.tvPublish);

        etContent = findViewById(R.id.etContent);

        btnFeedback = (Button)findViewById(R.id.btnFeedback);
        btnRead = (Button)findViewById(R.id.btnRead);

        llMain = (LinearLayout)findViewById(R.id.llMain);

        pref = new Pref(this);

     /*   QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode("NDimensionLabs", BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imgQrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }*/

        if (getIntent().getExtras()!=null){
            flag = getIntent().getStringExtra("flag");
            if (flag.equals("normal")){
                book_id = getIntent().getStringExtra("book_id");
                tvTitle.setText(getIntent().getStringExtra("book_title"));
                book_title = getIntent().getStringExtra("book_title");
                tvCategory.setText("Category: "+getIntent().getStringExtra("category"));
                tvAuthor.setText("Author :"+getIntent().getStringExtra("book_author"));
                tvPublish.setText("Originally published "+getIntent().getStringExtra("book_publish_date"));

                etContent.setText(Html.fromHtml(getIntent().getStringExtra("book_content")).toString());
                if (!getIntent().getStringExtra("book_pdf_link").equals("")) {
                    book_pdf_link = ConstantClass.DRIVE_URL + getIntent().getStringExtra("book_pdf_link");
                }

                barcode_img = getIntent().getStringExtra("book_barcode_img");
                book_qr_code = getIntent().getStringExtra("book_qr_code");
                Log.d("SoumyaQrcode",book_qr_code);


                try {
                    Picasso.with(this)
                            .load(getIntent().getStringExtra("book_img"))
                            .placeholder(R.drawable.no_img)
                            .into(imgBook);
                }
                catch (IllegalArgumentException e){
                    e.printStackTrace();
                    Picasso.with(this).load(R.drawable.no_img).into(imgBook);
                }

                try {
                    Picasso.with(this)
                            .load(getIntent().getStringExtra("book_barcode_img"))
                            .placeholder(R.drawable.no_img)
                            .into(imgQrCode);
                }
                catch (IllegalArgumentException e){
                    e.printStackTrace();
                    Picasso.with(this).load(R.drawable.no_img).into(imgQrCode);
                }
            }
        }
    }

    private void peformAction(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        imgQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBarcodePopup();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHARE_PDF_FLAG = 2;
                if (!book_pdf_link.equals("")) {
                  //  new PDFDownloadWithProgressDialog().execute(book_pdf_link);
                    if (!checkFileExistsOrNot()) {
                        downloadPDF(book_pdf_link);
                    }
                    else {
                        showPdf(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book" + "/"+book_title+".pdf");
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Pdf Link is not found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                SHARE_PDF_FLAG = 0;
                if (!book_pdf_link.equals("")) {
                   // new PDFDownloadWithProgressDialog().execute(book_pdf_link);
                    if (!checkFileExistsOrNot()) {
                        downloadPDF(book_pdf_link);
                    }
                    else {
                        sharePdf(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book" + "/"+book_title+".pdf");
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Pdf Link is not found",Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.download:
                SHARE_PDF_FLAG = 1;

                if (!book_pdf_link.equals("")) {
                    downloadPDF(book_pdf_link);

                }else {
                    Toast.makeText(getApplicationContext(),"Pdf Link is not found",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //respond to menu item selection

    }

    private void showPopup(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_feedback, null);

        TextView tvOk = (TextView) dialogView.findViewById(R.id.tvOk);
        final EditText etFeedbackContent = (EditText)dialogView.findViewById(R.id.etFeedbackContent);


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (etFeedbackContent.getText().toString().length()>0) {
                    callFeedbackMethod(etFeedbackContent.getText().toString().trim());
                }else {
                    etFeedbackContent.setError("Enter Content");
                    etFeedbackContent.requestFocus();
                }

            }
        });


        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void showBarcodePopup(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookActivity.this, R.style.CustomDialogNew);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_barcode, null);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.tvCancel);
        TextView tvShare = (TextView) dialogView.findViewById(R.id.tvShare);
        TextView tvDownload = (TextView) dialogView.findViewById(R.id.tvDownload);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Picasso.with(getApplicationContext()).load(barcode_img).into(new Target() {
                    @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        share(bitmap);

                    }
                    @Override public void onBitmapFailed(Drawable errorDrawable) { }
                    @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
                });

            }
        });

        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                downloadImage(barcode_img);
              //  new ImageDownloadWithProgressDialog().execute(barcode_img);

            }
        });


        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        alertDialog.show();
    }

    private void share(Bitmap bitmap){
        PackageManager pm = getPackageManager();

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
            Uri imageUri = Uri.parse(path);
          //  PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);


            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("*/*");
           // waIntent.setPackage("com.whatsapp");
            waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            waIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(waIntent);

        } catch (Exception e) {
            Log.e("Error on sharing", e + " ");
            Toast.makeText(this, "Sharing App is not Installed", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Progress_Dialog_Progress:

                progressdialog = new ProgressDialog(BookActivity.this);
                progressdialog.setMessage("Downloading Barcode From Server...");
                progressdialog.setMax(100);
                progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressdialog.setCancelable(false);
                progressdialog.show();
                return progressdialog;

            case  Progress_Dialog_Progress_2:
                progressdialog = new ProgressDialog(BookActivity.this);

                if(SHARE_PDF_FLAG==0) {
                    progressdialog.setMessage("Preparing Pdf ...");

                }else if (SHARE_PDF_FLAG==1){
                    progressdialog.setMessage("Preparing Pdf ...");
                }else if (SHARE_PDF_FLAG==2){

                    progressdialog.setMessage("Preparing Pdf ...");
                }
                progressdialog.setMax(100);
                progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressdialog.setCancelable(false);
                progressdialog.show();
                return progressdialog;

            default:

                return null;
        }
    }

    private long generateReferCode() {
        Random random = new Random(System.nanoTime());

        int randomInt = random.nextInt(1000000000);
        return randomInt;
    }

    private void showAlert(String type){
        if(type.equals("1")){
            text = "Barcode Image Downloaded In Your Internal Memory SmartLibrary folder";
        }else {
            text = "Pdf Downloaded In Your Internal Memory SmartLibrary folder";
        }
        Snackbar snackbar = Snackbar.make(llMain, text, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.RED);


        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void sharePdf(String outputFile){
      //  Uri uri = Uri.fromFile(outputFile);
        File file = new File(outputFile);
        Uri uri = FileProvider.getUriForFile(
                BookActivity.this,
                BuildConfig.APPLICATION_ID + ".provider", file);


        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(share);

    }

    public void showPdf(String outputFile)
    {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File file = new File(outputFile);
                Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            } else {
                File file = new File(outputFile);
                Uri uri = Uri.fromFile(file);
                Log.d("SoumyaURI", Uri.parse(outputFile).toString());
                intent.setDataAndType(uri, "application/pdf");
               // intent = Intent.createChooser(intent, "Open File");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

    }

    private void callFeedbackMethod(String feedback_content){
        final JSONObject input = new JSONObject();
        try {
            input.put("user_id",pref.getUserId());
            input.put("book_id",book_id);
            input.put("feedback_content",feedback_content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("inputFeedback",input.toString());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ConstantClass.BASE_URL+"feedback/feedbacksend", input, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("responseFeedback", response.toString());

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

    private void downloadImage(String imageURL) {
        showDialog(Progress_Dialog_Progress);
        AndroidNetworking.download(imageURL, Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/barcode" + "/", book_qr_code + ".jpg")
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress

                            progressdialog.setProgress(Integer.parseInt(String.valueOf((totalBytes * 100) / bytesDownloaded)));



                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        dismissDialog(Progress_Dialog_Progress);

                        // do anything after completion
                        showAlert("1");

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error

                    }


                });
    }

    private void downloadPDF(String pdfURL) {
        showDialog(Progress_Dialog_Progress_2);
        AndroidNetworking.download(pdfURL, Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book" + "/", book_title+".pdf")
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        // do anything with progress
                        progressdialog.setProgress(Integer.parseInt(String.valueOf((totalBytes*100)/bytesDownloaded)));

                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        outputFile = new File(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book/", book_title+".pdf");
                        // do anything after completion
                        dismissDialog(Progress_Dialog_Progress_2);
                        if(SHARE_PDF_FLAG == 0) {

                           // sharePdf(outputFile);
                            sharePdf(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book" + "/"+book_title+".pdf");
                        }else if (SHARE_PDF_FLAG == 1){

                            showAlert("2");
                        }else if (SHARE_PDF_FLAG == 2){

                            showPdf(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book" + "/"+book_title+".pdf");

                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error

                    }


                });
    }
public boolean checkFileExistsOrNot()
{
    File myFile = new File(Environment.getExternalStorageDirectory() + "/" + "SmartLibrary/book/", book_title+".pdf");
    if (myFile.exists()) {
        return true;
    }
    else {
        return false;
    }
}

   
}
