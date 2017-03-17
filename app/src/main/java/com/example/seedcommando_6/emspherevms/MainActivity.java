package com.example.seedcommando_6.emspherevms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           try {


               setContentView(R.layout.slider);
               progressDialog = new ProgressDialog(MainActivity.this);
               progressDialog.setCancelable(false);
               //WindowManager.LayoutParams lp = progressDialog.getWindow().getAttributes();
               progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
              // progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
               progressDialog.getWindow().setGravity(Gravity.CENTER);
               progressDialog.show();

        /*Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);*/
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       if (progressDialog.isShowing()) {
                           progressDialog.dismiss();

                       }

                       Intent intent = new Intent(MainActivity.this, Login.class);
                       startActivity(intent);
                   }
               }, 5000);
           }catch (Exception e){
               e.printStackTrace();
           }


    }

    }

