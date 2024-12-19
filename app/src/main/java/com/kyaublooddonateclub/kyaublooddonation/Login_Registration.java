package com.kyaublooddonateclub.kyaublooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.kyaublooddonateclub.kyaublooddonation.api.Api_Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_Registration extends AppCompatActivity {

    TabLayout tabLayout;
    FrameLayout frameLayout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        sharedPreferences = getSharedPreferences("myApp",MODE_PRIVATE);

        tabLayout = findViewById(R.id.tabLayout);
        frameLayout = findViewById(R.id.frameLayout);


        String email = sharedPreferences.getString("myApp","");

        if(email.length()<=0){
            //startActivity(new Intent(this,MainActivity.class));
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, new Log_in_fragment());
            transaction.commit();
        }else {



        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String text = tab.getText().toString();
                if(text.contains("Log In")){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, new Log_in_fragment());
                    transaction.commit();
                }else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, new Sign_Up_fragment());
                    transaction.commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //////////////===============================================/////////////////
//    private void JsobObjectRequestLogin() {
//
//        //String url = "https://pdfviewersite.000webhostapp.com/blood/login.php?mobile="+Phone+"&password="+password;
//        ProgressDialog progressDialog = new ProgressDialog(Login_Registration.this);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        String url = Api_Services.LOGIN_URL;
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("email",sharedPreferences.getString("email",""));
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    String id = response.getString("id");
//                    String name = response.getString("name");
//                    String email = response.getString("email");
//                    String mobile = response.getString("mobile");
//                    String password = response.getString("password");
//                    String profession = response.getString("profession");
//                    String address = response.getString("upozilla");
//                    String department = response.getString("department");
//                    String blood = response.getString("blood");
//                    String gender = response.getString("gender");
//
//                    Toast.makeText(Login_Registration.this, ""+name, Toast.LENGTH_SHORT).show();
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(Login_Registration.this);
//        requestQueue.add(objectRequest);
//    }
}