package com.kyaublooddonateclub.kyaublooddonation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends AppCompatActivity {

    TextInputEditText Femail,Fnumber;
    Button Fbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Femail = findViewById(R.id.Femail);
        Fnumber = findViewById(R.id.Fnumber);
        Fbutton = findViewById(R.id.Fbutton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Forgot password");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Fbutton.setOnClickListener(v->{

            ProgressDialog progressDialog = new ProgressDialog(Forgot_Password.this);
            progressDialog.setTitle("Please wait..");
            progressDialog.show();
            String url = "https://pdfviewersite.000webhostapp.com/blood/forgot.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.toString().contains("success")){
                        Toast.makeText(Forgot_Password.this, "Password successfully sent.Please check your email", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(Forgot_Password.this, "Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Forgot_Password.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }){

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>param = new HashMap<>();
                    param.put("email",Femail.getText().toString());
                    param.put("number",Fnumber.getText().toString());
                    return param;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Forgot_Password.this);
            requestQueue.add(stringRequest);
        });


    }
}