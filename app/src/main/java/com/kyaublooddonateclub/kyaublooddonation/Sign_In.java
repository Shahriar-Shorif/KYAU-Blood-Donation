package com.kyaublooddonateclub.kyaublooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Sign_In extends AppCompatActivity {

    EditText emailphone,pass;
    Button signinbtn;
    public static ArrayList<HashMap<String,String>>arrayList = new ArrayList<>();
    public static HashMap<String,String>hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailphone = findViewById(R.id.emailphone);
        pass = findViewById(R.id.pass);
        signinbtn = findViewById(R.id.signinbtn);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Phone = emailphone.getText().toString().trim();
                String Password = pass.getText().toString().trim();
                if(emailphone.length() == 0){
                    emailphone.setError("Wrong info");
                } else if (pass.length() == 0) {
                    pass.setError("Wrong info");
                }else {
                    Login(Phone, Password);
                }


            }
        });
    }

    private void Login(String Phone, String password) {

        String url = "https://pdfviewersite.000webhostapp.com/blood/retrievealldata.php";
        ProgressDialog progressDialog = new ProgressDialog(Sign_In.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length(); i++){
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

                        if((Phone.contains(Mobile) || Phone.contains(email)) && password.contains(Password)){
                            Intent intent = new Intent(Sign_In.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sign_In.this, "Server Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Sign_In.this);
        requestQueue.add(jsonArrayRequest);
    }


}