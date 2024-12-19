package com.kyaublooddonateclub.kyaublooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Show_Profile extends AppCompatActivity {

    ImageView IMG,calling;
    TextView Nam,BldGrp,dept,address,mail,num,donateDate;
    public static String IMGG = "";
    public static String CALLING = "";
    public static String NAM = "";
    public static String BLDGRP = "";
    public static String DEPT = "";
    public static String ADDRESS = "";
    public static String MAIL = "";
    public static String NUM = "";
    public static String LASTDOANTEDATE = "";
    public static Bitmap BITMAP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        IMG = findViewById(R.id.IMG);
        BldGrp = findViewById(R.id.BldGrp);
        Nam = findViewById(R.id.Nam);
        dept = findViewById(R.id.dept);
        address = findViewById(R.id.address);
        mail = findViewById(R.id.mail);
        num = findViewById(R.id.num);
        calling = findViewById(R.id.calling);
        donateDate = findViewById(R.id.donateDate);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BldGrp.setText(BLDGRP);
        Nam.setText(NAM);
        dept.setText(DEPT);
        address.setText(ADDRESS);
        mail.setText(MAIL);
        num.setText(NUM);
        donateDate.setText(LASTDOANTEDATE);
        //Loading image by picasso library.................
        String url = "https://pdfviewersite.000webhostapp.com/blood/Images/"+IMGG;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.baseline_person_24)
                .into(IMG);
        //Last donate date set here............

        //Long click on mail and copied it........
        mail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CopyText(MAIL);
                Toast.makeText(Show_Profile.this, "Email copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });//Finish mail copied....

        //Calling method here...........
        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String temp = "tel:" + NUM;
                intent.setData(Uri.parse(temp));
                startActivity(intent);
            }
        });


    }

    private void CopyText(String text){ //Copy text method here..........
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        clipboard.setPrimaryClip(clip);
    }
}