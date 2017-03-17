package com.example.seedcommando_6.emspherevms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by seedcommando_6 on 2/24/2017.
 */

public class Login extends AppCompatActivity{
    EditText username , password;
    Button loginBtn;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main1);
        Log.d("after","main");
        username = (EditText) findViewById(R.id.editText_uname);
        username.requestFocus();
        password = (EditText) findViewById(R.id.editText_pass);
        loginBtn = (Button) findViewById(R.id.button_SignIn);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.checkBox_remeber);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

    }
    public  boolean isNetworkAvailable()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public void login(View v){

        if (isNetworkAvailable()) {

            if (username.getText().toString().equals("seed") && password.getText().toString().equals("seed")) {

                Toast.makeText(this, "Welcome , Login Successfully..", Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

               String username1 = username.getText().toString();
                String  password1 = password.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username1);
                    loginPrefsEditor.putString("password", password1);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }



                Intent i = new Intent(this, Home.class);
                startActivity(i);
            } else {

                Toast.makeText(this, "Please enter valid username or password....", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, " Network is Not available please connect....", Toast.LENGTH_LONG).show();
        }
    }
}
