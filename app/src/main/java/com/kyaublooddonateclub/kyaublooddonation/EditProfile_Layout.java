package com.kyaublooddonateclub.kyaublooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfile_Layout extends AppCompatActivity {

    ImageView EditedImage;
    TextInputEditText EditedName,EditedNumber,EditedEmail;
    MaterialButton EditedButton;
    Bitmap bitmap;
    String encodedImg;
    public static ArrayList<HashMap<String,String>>arrayList;
    public static HashMap<String,String>hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_layout);
        EditedImage = findViewById(R.id.EditedImage);
        EditedName = findViewById(R.id.EditedName);
        EditedNumber = findViewById(R.id.EditedNumber);
        EditedEmail = findViewById(R.id.EditedEmail);
        EditedButton = findViewById(R.id.EditedButton);



        EditedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(EditProfile_Layout.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });

        EditedButton.setOnClickListener(v -> {
            String name = EditedName.getText().toString();
            String Number = EditedNumber.getText().toString();
            String Email = EditedEmail.getText().toString();
            ProgressDialog progressDialog = new ProgressDialog(EditProfile_Layout.this);
            progressDialog.setMessage("Updating..");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://pdfviewersite.000webhostapp.com/blood/profileupdate.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfile_Layout.this, profile_fragment.ID+""+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditProfile_Layout.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>params = new HashMap<>();
                    params.put("img",encodedImg);
                    params.put("name",name);
                    params.put("email",Email);
                    params.put("mobile",Number);
                    params.put("id",profile_fragment.ID);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(EditProfile_Layout.this);
            requestQueue.add(stringRequest);

        });
    }

    //Image pick up from gallery and camera and set on imageView here.........................
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri filepath = data.getData();
            EditedImage.setImageURI(filepath);

            *//*try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                EditedImage.setImageBitmap(bitmap);
                encodedBitmapImg(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }*//*
        }*/
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri filepath = data.getData();
            EditedImage.setImageURI(filepath);

            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                EditedImage.setImageBitmap(bitmap);
                encodedBitmapImg(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void encodedBitmapImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimg = byteArrayOutputStream.toByteArray();
        encodedImg = android.util.Base64.encodeToString(bytesofimg, Base64.DEFAULT);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//



    public void VolleyRequest(String Url){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(EditProfile_Layout.this, ""+response.toString(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String Mobile = jsonObject.getString("mobile");
                        String Password = jsonObject.getString("password");
                        String dept = jsonObject.getString("department");
                        String bldgrp = jsonObject.getString("blood");
                        String location = jsonObject.getString("upozilla");



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfile_Layout.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile_Layout.this);
        requestQueue.add(jsonArrayRequest);
    }
}