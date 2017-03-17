package com.example.seedcommando_6.emspherevms;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by seedcommando_6 on 2/27/2017.
 */

public class ViewDialog {
    JSONObject obj;
    Context mContext;

    public  ViewDialog(Context context)
    {
        this.mContext=context;
    }
    public void showDialog(final Activity activity, String data){
       // System.out.println("@@@@#####@##@#@#"+data);
        final Dialog dialog = new Dialog(activity);
        //Dialog dialog1 = new Dialog(mContext, R.style.FullHeightDialog);
       // Window window = dialog.getWindow();
        //window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        //dialog.getWindow().setLayout(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
       // Window window = dialog.getWindow();
       // window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialogs);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        ImageView imageView=(ImageView) dialog.findViewById(R.id.imageView3);
        TextView textView = (TextView) dialog.findViewById(R.id.textView8);
       // CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.checkBox2);
        TextView name = (TextView) dialog.findViewById(R.id.textView);
        try {
            obj = new JSONObject(data);
            //System.out.println("@@@@#####@##@#@#"+obj);
            //Fields d = (Fields)obj .get("data");
           // String nm=d.getFirstname();
            String nm=obj.getString("name");
            //String lnm=d.getLastname();
            String lnm=obj.getString("lname");

            name.setText(nm+" "+lnm);
            String img_vE = obj.getString("image");
            //retrieve and set to bitmap
            byte[] encodeByte = Base64.decode(img_vE, Base64.DEFAULT);
          Bitmap  bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            RoundedBitmapDrawable conv_bm = RoundImage.getRoundedBitmap(bitmap);
            imageView.setImageDrawable(conv_bm);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

       // checkBox.setChecked(true);
        SimpleDateFormat df1 = new SimpleDateFormat("EEEE,dd-MMM-yyyy");
        String formattedDate1 = df1.format(c.getTime());
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setCancelable(true);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());

        textView.setText(formattedDate1+"\n"+formattedDate);

        TextView text = (TextView) dialog.findViewById(R.id.textView12);
        text.setText("Submitted Succesfully");

        Button dialogButton = (Button) dialog.findViewById(R.id.button2);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity.getApplicationContext(),Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);


                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
