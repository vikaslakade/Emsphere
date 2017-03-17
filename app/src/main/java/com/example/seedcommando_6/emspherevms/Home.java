package com.example.seedcommando_6.emspherevms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by seedcommando_10 on 1/16/2017.
 */

public class Home extends AppCompatActivity {
   RelativeLayout add , serarch;
   // ImageButton add , serarch;

     Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        try {
            setContentView(R.layout.home);
           /* toolbar = (Toolbar) findViewById(R.id.toolbar_home);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            add = (RelativeLayout) findViewById(R.id.add_button);


            serarch = (RelativeLayout) findViewById(R.id.search_button);
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public void OnAddbuttonclick(View v) {

       Intent intent = new Intent(getApplicationContext(),VisitorRegistration.class);
        startActivity(intent);
    }


    public void OnSearchbuttonclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),SearchExistingVisitor.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
