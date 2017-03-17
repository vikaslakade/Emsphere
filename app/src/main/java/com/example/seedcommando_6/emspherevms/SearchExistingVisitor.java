package com.example.seedcommando_6.emspherevms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by seedcommando_10 on 1/21/2017.
 */

public class SearchExistingVisitor extends AppCompatActivity {
    //public static final String URL_GET_ALL = "http://125.99.68.24/mywebservices/WebService/GetFeeds";

    public static final String URL_GET_ALL = "http://125.99.68.240/mywebservices/WebService/GetFeeds";
    // public static final String URL_GET_ALL = "http://172.16.50.10:8080/mywebservices/WebService/GetFeeds";
    EditText mobileno1,firstname;
    ImageView imagev;
    TextView name, mobno;
    Button addlist, search;
    ProgressDialog pd;
    String mobino1,nme;
    HttpHandler sh = null;
    String name_f, mno, img;
    String jsonStr;
    Fields fields1;
    Bundle b;
    HashMap<String,Object> hm1;
    JSONObject jsonObject;
    JSONObject  jsonObject1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.search_existing_visitor);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar = (Toolbar) findViewById(R.id.toolbar_search);
            //setActionBar(toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setContentView(R.layout.search_existing_visitor);
            TextView name1=(TextView) findViewById(R.id.textView_fname);
            String first = "First Name";
            String next = "<font color='#EE0000'>*</font>";
            name1.setText(Html.fromHtml(next+" "+first));

            //font set
            // Font path
            String fontPath = "fonts/helvetica.ttf";
            String fontPatharial = "fonts/arial.ttf";
            Typeface fontRobo = Typeface.createFromAsset(getAssets(), fontPatharial);

            name1.setTypeface(fontRobo );
            //madtory fields
            TextView mbno=(TextView) findViewById(R.id.textView_mnumber);
            String mobtext = "Mobile Number";
            String next1 = "<font color='#EE0000'>*</font>";
            mbno.setText(Html.fromHtml(next1+" "+mobtext));
            mbno.setTypeface(fontRobo );
        imagev = (ImageView) findViewById(R.id.imageView_search);
        name = (TextView) findViewById(R.id.textView3);
        mobno = (TextView) findViewById(R.id.textView_phone);
        firstname=(EditText) findViewById(R.id.editText_fname);
       // mobileno1 = (EditText) findViewById(R.id.editText_mnumbe);
        mobileno1=(EditText) findViewById(R.id.editText_mnumbe);
        addlist = (Button) findViewById(R.id.button_addvisit);
        search = (Button) findViewById(R.id.button_search);
            //buttom make unvisible
            //addlist.setVisibility(View.INVISIBLE);
            /*name.setVisibility(View.GONE);
            imagev.setVisibility(View.GONE);
            imagev.setVisibility(View.GONE);
            mobno.setVisibility(View.GONE);*/

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    public void searchvisitor1(View v) {
        mobino1=mobileno1.getText().toString();
        nme=firstname.getText().toString();
       // System.out.println("@@@@@@####on click data@@@@@@#######"+mobino);
        if (!nme.isEmpty()) {
            if (mobino1.length() == 10) {
                new MyJsonTask().execute(mobino1);
                //addlist.setEnabled(true);
            } else {
                mobileno1.requestFocus();
                mobileno1.setError("Please Enter valid number");
                //Toast.makeText(getApplicationContext(), "Please Enter valid number", Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {

                firstname.requestFocus();
            firstname.setError("Please enter first name ");

            //Toast.makeText(getApplicationContext(), "Please enter first name ", Toast.LENGTH_LONG).show();
        }
    }
    private class MyJsonTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SearchExistingVisitor.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn;
           // String response = null;
            String jsonStr;
            try {
                int timeout=15000;
                boolean connection=ConnectionCheck.isConnectedToServer(URL_GET_ALL,timeout);
                // System.out.println("response code"+conn.getResponseCode());
                Log.d("connection@@#@@@@",""+connection);
               // ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
               // boolean networkAvailable=ConnectionCheck.isNetworkAvailable(cm);
                if(connection !=false) {
                    URL url = new URL(URL_GET_ALL); // here is your URL path
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.connect();

                    try {

                        JSONObject postDataParams = new JSONObject();
                        //postDataParams.put("name", d.getFirstname());
                        postDataParams.put("mobile", mobino1);
                        postDataParams.put("fname", nme);
                        Log.e("params", postDataParams.toString());
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(postDataParams.toString());
                        System.out.println("Writer data" + writer);
                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            sh = new HttpHandler();
                            jsonStr = sh.makeServiceCall(conn);
                            System.out.println(jsonStr);
                            //System.out.println("@@@@@##########"+conn.getResponseCode());
                            if (!jsonStr.isEmpty()) {
                                try {
                                    // JSONObject jsonObj = new JSONObject(jsonStr);
                                    JSONArray jsonArray = new JSONArray(jsonStr);
                                    // System.out.println(jsonArray);
                                    if (!jsonArray.isNull(0)) {
                                        try {
                                            //System.out.println(jsonArray);
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                jsonObject = jsonArray.getJSONObject(i);
                                                //System.out.println("sddddd"+jsonObject);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        // System.out.println("toast called"+jsonArray);
                                        Toast.makeText(SearchExistingVisitor.this, "Visitor not found", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        } else {
                            return new String("false : " + responseCode);

                        }

                    } catch (IOException e) {
                        System.out.println("////// called" + conn.getResponseCode());
                        e.printStackTrace();
                    }
                }else {
                    return "false";
                }
            } catch (Exception e) {
                return new String("null");
            }
                return jsonObject.toString();

    }
        @Override
        protected void onPostExecute (String result) {

            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (!result.equalsIgnoreCase("false")) {
                if (!result.equalsIgnoreCase("null")) {
                    try {
                        if (!result.isEmpty()) {
                       /* addlist.setVisibility(View.VISIBLE);
                        name.setVisibility(View.VISIBLE);
                        imagev.setVisibility(View.VISIBLE);
                        imagev.setVisibility(View.VISIBLE);
                        mobno.setVisibility(View.VISIBLE);*/

                            System.out.println("jhftythjhjhhh@#$$%%%^^&&" + result);
                            jsonObject1 = new JSONObject(result);
                            // fields1 =new Fields();
                            name_f = jsonObject1.getString("fname");
                           // String l_name=jsonObject1.getString("Lastname");
                            //fields1.setFirstname(name_f);
                            //System.out.println("my name"+fields1.getFirstname());
                           // System.out.println("name" + name_f);
                            name.setText(name_f);
                            mno = jsonObject1.getString("mobile");
                            mobno.setText(mno);
                            //System.out.println("phone" + mno);
                            String my_img = jsonObject1.getString("image");
                           // System.out.println("image" +my_img);
                            //retrieve and set to bitmap
                            byte[] encodeByte = Base64.decode(my_img, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            //return bitmap;
                            RoundedBitmapDrawable conv_bm = RoundImage.getRoundedBitmap(bitmap);
                            imagev.setImageDrawable(conv_bm);

                        } else {
                            System.out.println("toast called");
                            pd.dismiss();
                            Toast.makeText(SearchExistingVisitor.this, "Visitor data not found", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                 Toast toast1= Toast.makeText(SearchExistingVisitor.this, "Visitor data not found", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }

            }else {
                Toast toast=Toast.makeText(SearchExistingVisitor.this, "server not Running,Please try after some time", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }

    }
    public  void searchgetdata()
    {
        try {
            fields1 =new Fields();
            fields1.setFirstname(name_f);
           // System.out.println("my name /////"+fields1.getFirstname());
            fields1.setLastname(jsonObject1.getString("Lastname"));
            fields1.setMobile(jsonObject1.getString("mobile"));
            fields1.setEmailId(jsonObject1.getString("EmailId"));
            fields1.setRepresenting(jsonObject1.getString("visittype"));
            fields1.setRemark(jsonObject1.getString("Remark"));
            fields1.setAddress(jsonObject1.getString("Idcardtype"));
            fields1.setIdcardnumber(jsonObject1.getString("Address"));
            fields1.setIdcardtype(jsonObject1.getString("Idcardnumber"));
            fields1.setVisittype(jsonObject1.getString("Representing"));
            fields1.setImage(jsonObject1.getString("image"));
            // Toast.makeText(this," next",Toast.LENGTH_LONG).show();
            hm1=new HashMap<String,Object>();
            hm1.put("data", fields1);

            //Bundle object is created
            b = new Bundle();
            b.putSerializable("bundleobj", hm1);
           // b.putSerializable("bundleobj", hm1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void goVisitorEntry(View v) {

        try {
                searchgetdata();
                Intent intentsearch = new Intent(getApplicationContext(), SearchVisitor_details.class);
                intentsearch.putExtras(b);
                startActivity(intentsearch);

            } catch (Exception e) {
                e.printStackTrace();
        }

    }

    }




