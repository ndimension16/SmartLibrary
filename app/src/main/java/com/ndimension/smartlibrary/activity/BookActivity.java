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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gun0912.tedpermission.PermissionListener;
import com.ndimension.smartlibrary.R;
import com.ndimension.smartlibrary.utility.ConstantClass;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
                book_pdf_link = ConstantClass.DRIVE_URL+getIntent().getStringExtra("book_pdf_link");

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
                new PDFDownloadWithProgressDialog().execute(book_pdf_link);
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
                new PDFDownloadWithProgressDialog().execute(book_pdf_link);
                return true;
            case R.id.download:
                SHARE_PDF_FLAG = 1;
                new PDFDownloadWithProgressDialog().execute(book_pdf_link);
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


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

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
                new ImageDownloadWithProgressDialog().execute(barcode_img);

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

    public class ImageDownloadWithProgressDialog extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            showDialog(Progress_Dialog_Progress);
        }

        @Override
        protected String doInBackground(String... aurl) {

            int count;

            try {
                /*String rootDir = Environment.getExternalStorageDirectory()
                        + File.separator + "WhatsAppStatus/Image";*/
                String rootDir = Environment.getExternalStorageDirectory()
                        + File.separator + "SmartLibrary/barcode";
                File rootFile = new File(rootDir);
                rootFile.mkdir();


                url = new URL(aurl[0]);
                urlconnection = url.openConnection();
                urlconnection.connect();

                FileSize = urlconnection.getContentLength();

                inputstream = new BufferedInputStream(url.openStream(),8192);
                outputstream = new FileOutputStream(new File(rootFile, book_qr_code+".jpg"));


                while ((count = inputstream.read(dataArray)) != -1) {

                    totalSize += count;

                    publishProgress(""+(int)((totalSize*100)/FileSize));

                    outputstream.write(dataArray, 0, count);
                }

                outputstream.flush();
                outputstream.close();
                inputstream.close();

            } catch (Exception e) {}
            return null;

        }
        protected void onProgressUpdate(final String... progress) {
            progressdialog.setProgress(Integer.parseInt(progress[0]));



        }

        @Override
        protected void onPostExecute(String unused) {

            dismissDialog(Progress_Dialog_Progress);

            showAlert("1");
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
                    progressdialog.setMessage("Preparing Pdf to share...");

                }else if (SHARE_PDF_FLAG==1){
                    progressdialog.setMessage("Downloading Pdf From Server...");
                }else if (SHARE_PDF_FLAG==2){

                    progressdialog.setMessage("Preparing Pdf to read...");
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

    private class PDFDownloadWithProgressDialog extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            showDialog(Progress_Dialog_Progress_2);
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                 rootDir = Environment.getExternalStorageDirectory()
                        + File.separator + "SmartLibrary/book";
                File rootFile = new File(rootDir);
                rootFile.mkdir();
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                FileSize = c.getContentLength();

                String fileName = book_title+".pdf";
                outputFile = new File(rootFile, fileName);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
               // InputStream inputstream = c.getInputStream();
                InputStream inputstream = new BufferedInputStream(url.openStream(),8192);
                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = inputstream.read(buffer)) > 0) {
                    totalSize += len1;

                    publishProgress(""+(int)((totalSize*100)/FileSize));
                    outputStream.write(buffer, 0, len1);
                }
                outputStream.flush();
                outputStream.close();
                inputstream.close();

            //    shareVideoFilePath = rootDir+"/"+fileName;
            } catch (IOException e) {
                Log.d("Error....", e.toString());
            }
            return null;

        }

        protected void onProgressUpdate(final String... progress) {
            progressdialog.setProgress(Integer.parseInt(progress[0]));



        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(Progress_Dialog_Progress_2);

            if(SHARE_PDF_FLAG == 0) {
                // Log.d("SoumyaUri", thumbList.get(shareVideoFilePath).getImage());

                sharePdf(outputFile);
            }else if (SHARE_PDF_FLAG == 1){

                showAlert("2");
            }else if (SHARE_PDF_FLAG == 2){

                showPdf(outputFile);
            }
        }

    }

    private void sharePdf(File outputFile){
        Uri uri = Uri.fromFile(outputFile);

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(share);

    }

    public void showPdf(File outputFile)
    {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(outputFile);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }

   
}
