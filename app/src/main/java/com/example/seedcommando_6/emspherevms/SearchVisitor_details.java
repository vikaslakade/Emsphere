package com.example.seedcommando_6.emspherevms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by seedcommando_10 on 1/19/2017.
 */

public class SearchVisitor_details extends AppCompatActivity {

    TextView visitor_name, mobilenumber;
    ImageView iv;
    EditText personmeet, time, duration, material1, material2, area, serialno1, serialno2; /*visitpurpose;*/
     Spinner visitpurpose;
    Button submit;
    // Progress Dialog Object
    ProgressDialog prgDialog;
    Fields fields;
    Bundle bn;
    HashMap<String, Object> getobj;
    Fields d;
    String fname, mbno, img_vE, lname;
    android.support.v7.widget.Toolbar t4;
    Spinner spinner;
    RelativeLayout relativeLayout;
    Bitmap bitmap;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.visit_details);
            //visitpurpose = (Spinner)findViewById(R.id.dynamic_spinner);
            // relativeLayout=(RelativeLayout) findViewById(R.id.visitor_entry);
            // Instantiate Progress Dialog object
            prgDialog = new ProgressDialog(this);
            // Set Progress Dialog Text
            prgDialog.setMessage("Please wait...");
            // Set Cancelable as False
            prgDialog.setCancelable(false);
            personmeet = (EditText) findViewById(R.id.persontomeet);
            duration = (EditText) findViewById(R.id.editText_duration);
            mobilenumber = (TextView) findViewById(R.id.mbn);
            material1 = (EditText) findViewById(R.id.editText_materialno1);
            material2 = (EditText) findViewById(R.id.editText_materialno2);
            area = (EditText) findViewById(R.id.editText_area);
            serialno1 = (EditText) findViewById(R.id.editText_serialno1);
            serialno2 = (EditText) findViewById(R.id.editText_serialno2);
            submit = (Button) findViewById(R.id.visitor_entry_submit);
            visitor_name = (TextView) findViewById(R.id.textView5);
            iv = (ImageView) findViewById(R.id.imageView3);
            time = (EditText) findViewById(R.id.editText_time);
           // visitpurpose = (EditText) findViewById(R.id.visit_purpose);
            visitpurpose = (Spinner) findViewById(R.id.visit_purpose);
            String[] visits = getResources().getStringArray(R.array.visittype);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, visits) {

                @Override
                public int getCount() {
                    int c = super.getCount();
                   // if (visitpurpose.getSelectedItemPosition() < c - 1) return c;
                    return c > 0 ? c - 1 : c;
                }
            };
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            visitpurpose.setAdapter(adapter2);
            visitpurpose.setSelection(adapter2.getCount());
            //set date to text
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            // textView is the TextView view that should display it
            time.setText(currentDateTimeString);
           /* spinner = (Spinner)findViewById(R.id.spinner);
            String[] items = new String[]{"Visit Purpose", "Interview", "Employee","Security"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
            spinner.setAdapter(adapter);*/
            t4 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar3);
            setSupportActionBar(t4);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            bn = new Bundle();
            bn = getIntent().getExtras();
            getobj = new HashMap<String, Object>();
            getobj = (HashMap<String, Object>) bn.getSerializable("bundleobj");
            d = (Fields) getobj.get("data");
            fname = d.getFirstname();
            System.out.println(fname);
            lname = d.getLastname();
            visitor_name.setText(fname+" "+lname);

            mbno = d.getMobile();
            System.out.println(mbno);
            //set data to the text view
            mobilenumber.setText(mbno);
            img_vE = d.getImage();
            //retrieve and set to bitmap
            byte[] encodeByte = Base64.decode(img_vE, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            RoundedBitmapDrawable conv_bm = RoundImage.getRoundedBitmap(bitmap);
            iv.setImageDrawable(conv_bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getdata() {
        fields = new Fields();
        fields.setDuration(duration.getText().toString());
        fields.setPersontomeet(personmeet.getText().toString());
        fields.setMaterial1(material1.getText().toString());
        fields.setMaterial2(material2.getText().toString());
        fields.setArea(area.getText().toString());
        fields.setSerialNo1(serialno1.getText().toString());
        fields.setSerialNo2(serialno2.getText().toString());
        fields.setVisitpurpose(visitpurpose.getSelectedItem().toString());
        fields.setTime(time.getText().toString());
        // Toast.makeText(this," next",Toast.LENGTH_LONG).show();
    }

    public void visitorEntrySubmit(View v) {
        getdata();
        new SendPostRequest().execute();
       /* Toast.makeText(this,"Visit is Confirm",Toast.LENGTH_LONG).show();*/
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            pd = new ProgressDialog(SearchVisitor_details.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... arg0) {
            try {
                 URL url = new URL("http://125.99.68.240/mywebservices/WebService/add");
                //URL url = new URL("http://172.16.50.10:8080/mywebservices/WebService/add"); // here is your URL path
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();
                // System.out.println(conn.getContentType());
                JSONObject postDataParams = new JSONObject();
                //postDataParams.put("name", d.getFirstname());
                postDataParams.put("fname", d.getFirstname());
                postDataParams.put("Lastname", d.getLastname());
                postDataParams.put("mobile", d.getMobile());
                postDataParams.put("image", d.getImage());
                postDataParams.put("EmailId", d.getEmailId());
                postDataParams.put("visittype", d.getVisittype());
                postDataParams.put("Idcardtype", d.getIdcardtype());
                postDataParams.put("Idcardnumber", d.getIdcardnumber());
                postDataParams.put("Address", d.getAddress());
                postDataParams.put("Representing", d.getRepresenting());
                postDataParams.put("Remark", d.getRemark());
                postDataParams.put("duration", fields.getDuration());
                postDataParams.put("material1", fields.getMaterial1());
                postDataParams.put("material2", fields.getMaterial2());
                postDataParams.put("persontomeet", fields.getPersontomeet());
                postDataParams.put("serialNo1", fields.getSerialNo1());
                postDataParams.put("serialNo2", fields.getSerialNo2());
                postDataParams.put("Area", fields.getArea());
                postDataParams.put("time", fields.getTime());
                postDataParams.put("Visitpurpose", fields.getVisitpurpose());
                //postDataParams.put("name",d.getFirstname() );
                // postDataParams.put("email", "abc@gmail.com");
                Log.e("params", postDataParams.toString());
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                //writer.write(getPostDataString(postDataParams));
                //os.write(postDataParams.getByt);
                writer.write(postDataParams.toString());
                System.out.println("Writer data" + writer);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();
                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
            //System.out.println(result);
            // Snackbar snackbar;
            if (result.equals("success")) {
                JSONObject data = null;
                //System.out.println(result);
                Snackbar snackbar;
                if (result.equals("success")) {
                    try {
                        try {
                            data = new JSONObject();
                            data.put("name", fname);
                            data.put("lname", lname);
                            data.put("image", img_vE);
                            // data.put("name", fname);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ViewDialog alert = new ViewDialog(getApplicationContext());
                        alert.showDialog(SearchVisitor_details.this, data.toString());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Initializing snackbar using Snacbar.make() method
                    snackbar = Snackbar.make(relativeLayout, "Data not added ", Snackbar.LENGTH_LONG);
                    //Displaying the snackbar using the show method()
                    snackbar.show();
                }
            }
        }
    }
}
