package com.kyaublooddonateclub.kyaublooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Sign_Up_form extends AppCompatActivity {

    MaterialButton signup;
    EditText name,mail,mobile,password,upozilla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);

        signup = findViewById(R.id.signup);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        upozilla = findViewById(R.id.upozilla);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //.>>>>>>>Blood group select dropdown menu>>>>>>>>>//
        String[] type = new String[]{"A+","B+","A-","B-","AB+","AB-","O+","O-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,R.layout.dropdownitem,type
        );

        AutoCompleteTextView autoComplete2  = findViewById(R.id.autoComplete2);
        autoComplete2.setAdapter(adapter1);





        //Department select drowpdown menu here................
        String[] type1 = new String[]{"CSE","EEE","MIS","BCBT","MICROBIOLOGY","Islamic studies","English","BBA"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,R.layout.dropdownitem,type1
        );

        AutoCompleteTextView autoComplete1  = findViewById(R.id.autoComplete1);
        autoComplete1.setAdapter(adapter2);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dept = autoComplete1.getText().toString();
                String bloodGrp = autoComplete2.getText().toString();
                String Name = name.getText().toString();
                String Email = mail.getText().toString();
                String Mobile = mobile.getText().toString();
                String Password = password.getText().toString();
                String Upozilla = upozilla.getText().toString();
                try {
                    bloodGrp = URLEncoder.encode(bloodGrp,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String url = "https://pdfviewersite.000webhostapp.com/blood/connectdatabase.php?name="+Name+"&mail="+Email+"&mobile="
                        +Mobile+"&password="+Password+"&upozilla="+Upozilla+"&department="+dept+"&blood="+bloodGrp;




                ProgressDialog progressDialog = new ProgressDialog(Sign_Up_form.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Sign_Up_form.this);

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                              progressDialog.dismiss();
                              Intent intent = new Intent(Sign_Up_form.this,MainActivity.class);
                              startActivity(intent);
                              finish();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}