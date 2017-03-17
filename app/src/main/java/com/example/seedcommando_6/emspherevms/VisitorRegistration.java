package com.example.seedcommando_6.emspherevms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.Base64;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.HashMap;

/**
 * Created by seedcommando_10 on 1/18/2017.
 */

public class VisitorRegistration extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    EditText f_name, l_name, mobile_no, email_id, idno, addrees, representing, remark;/*visitortype, idproof;*/
    Spinner visitortype, idproof;
    Button btn_next;
    Fields fields;
    HashMap<String, Object> hm;
    Bundle b;
    android.support.v7.widget.Toolbar t1;
    Toolbar toolbar;
    ImageView image,cam;
    Bitmap photo;
    String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.visit_registration);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar = (Toolbar) findViewById(R.id.toolbar3);
            //setActionBar(toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //toolbar.setNavigationIcon(R.drawable.ic_action_name);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //visitortype = (Spinner) findViewById(R.id.spinner_visitortype);
       // idproof = (Spinner) findViewById(R.id.spinner_IDProof);
        TextView email=(TextView) findViewById(R.id.textView_emailid);
        String first = "Email ID";
        String next = "<font color='#EE0000'>*</font>";
        email.setText(Html.fromHtml(next+" "+first));

        TextView mbno=(TextView) findViewById(R.id.textView_mobileno);
        String mobtext = "Mobile Number";
        String next1 = "<font color='#EE0000'>*</font>";
        mbno.setText(Html.fromHtml(next1+" "+mobtext));
        image=(ImageView) findViewById(R.id.imageView_reg);
       // cam=(ImageView) findViewById(R.id.imageView_camera);
        f_name = (EditText) findViewById(R.id.editText_name);
        l_name = (EditText) findViewById(R.id.editText_lname);
        mobile_no = (EditText) findViewById(R.id.editText_mobileno);
        email_id = (EditText) findViewById(R.id.editText_emailid);
        addrees = (EditText) findViewById(R.id.editText1_address);
        representing = (EditText) findViewById(R.id.editTextreprenting);
        remark = (EditText) findViewById(R.id.editText_remark);
        idno = (EditText) findViewById(R.id.editText_idcardnumber);
       // visitortype = (EditText) findViewById(R.id.editText_visittype);
        visitortype = (Spinner) findViewById(R.id.editText_visittype);


        String[] visits = getResources().getStringArray(R.array.visittype);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, visits) {

            @Override
            public int getCount() {
                int c = super.getCount();
                return c > 0 ? c - 1 : c;
                //if (visitortype.getSelectedItemPosition() < c - 1) return c;
               // return c > 0 ? c - 1 : c;
                //return count > 0 ? count - 1 : count;
            }
        };
        //visitortype.setPrompt("Select your favorite Planet!");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        visitortype.setAdapter(adapter);
        visitortype.setSelection(adapter.getCount());
        //idproof = (EditText) findViewById(R.id.editText_idcardtype);
        idproof = (Spinner) findViewById(R.id.editText_idcardtype);
        String[] visitid = getResources().getStringArray(R.array.visitid);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, visitid) {

            @Override
            public int getCount() {
                int c = super.getCount();
                //if (idproof.getSelectedItemPosition() < c - 1) return c;
                return c > 0 ? c - 1 : c;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idproof.setAdapter(adapter1);
        idproof.setSelection(adapter1.getCount());
        btn_next = (Button) findViewById(R.id.button_next);
        // btnnext.setOnClickListener(this);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v ) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT",1);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == CAMERA_REQUEST) {
                photo = (Bitmap) data.getExtras().get("data");
                RoundedBitmapDrawable conv_bm1 = RoundImage.getRounded(photo);
                //photo=(Bitmap) data.getExtras().get("data");
                image.setImageDrawable(conv_bm1);
               // image.setImageBitmap(photo);

            } else {
                Toast.makeText(VisitorRegistration.this, "please take photo again", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void passdata(View view) {
        getdata();
    }
    public void getdata() {
        String name = f_name.getText().toString();
        String last = l_name.getText().toString();
        String mno = mobile_no.getText().toString();
       String email=email_id.getText().toString();
        String vistype=visitortype.getSelectedItem().toString();
         String idprof=idproof.getSelectedItem().toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try {
                if(photo !=null) {
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                }else {

                  Toast toastphoto=  Toast.makeText(VisitorRegistration.this,"please take a photo",Toast.LENGTH_LONG);
                    toastphoto.setGravity(Gravity.CENTER, 0, 0);
                    toastphoto.show();
                }
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
      // String vistype=visitortype.getSelectedItem().toString();
       // String idprof=idproof.getSelectedItem().toString();
        String idnumber=idno.getText().toString();
    if(Utility.isNotNull(name)&&Utility.isNotNull(last)&&Utility.isNotNull(mno)&&Utility.isNotNull(email)
            &&Utility.isNotNull(vistype)&&Utility.spinnervalid(vistype)&&Utility.isNotNull(idprof)&&Utility.spinnervalid(idprof)&&Utility.isNotNull(idnumber)&&Utility.isNotNull(encodedImage))
    {
        if (Utility.validate(email)&&mno.length()==10) {
        try {
            //data set to feild class
            fields = new Fields();
            fields.setFirstname(name);
            fields.setLastname(last);
            fields.setMobile(mno);
            fields.setEmailId(email);
            fields.setRepresenting(representing.getText().toString());
            fields.setRemark(remark.getText().toString());
            fields.setAddress(addrees.getText().toString());
            fields.setIdcardnumber(idnumber);
            fields.setIdcardtype(idprof);
            fields.setVisittype(vistype);
            // send Image as string
            fields.setImage(encodedImage);
           }catch (Exception e){
               e.printStackTrace();
           }
            // Toast.makeText(this," next",Toast.LENGTH_LONG).show();
            hm = new HashMap<String, Object>();
            hm.put("data", fields);
            //Bundle object is created
            b = new Bundle();
            b.putSerializable("bundleobj", hm);
            Intent inew = new Intent(getApplicationContext(), VisitorEntry.class);
            inew.putExtras(b);
            // System.out.println("data is"+inew);
            startActivity(inew);

        } else {
          Toast toastmb=  Toast.makeText(getApplicationContext(), "Please enter valid email or mobile number", Toast.LENGTH_LONG);
            toastmb.setGravity(Gravity.CENTER, 0, 0);
            toastmb.show();
        }
    }
    else{
       Toast toast= Toast.makeText(getApplicationContext(), "Please fill all field, don't leave any field blank", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}

}

