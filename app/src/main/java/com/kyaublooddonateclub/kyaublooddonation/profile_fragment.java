package com.kyaublooddonateclub.kyaublooddonation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class profile_fragment extends Fragment {
    TextView Blood,donateday,Name,phone,email,location,PROFNAME,editprofile,DonateDay;
    ImageView ProfImg;
    public static String ID = "";
    public static String BIT = "";
    RelativeLayout relative;
    ShimmerFrameLayout shimmer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        Blood = view.findViewById(R.id.Blood);
        donateday = view.findViewById(R.id.donateday);
        Name = view.findViewById(R.id.Name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        location = view.findViewById(R.id.location);
        PROFNAME = view.findViewById(R.id.PROFNAME);
        editprofile = view.findViewById(R.id.editprofile);
        DonateDay = view.findViewById(R.id.DonateDay);
        ProfImg = view.findViewById(R.id.ProfImg);
        relative = view.findViewById(R.id.relative);
        shimmer = view.findViewById(R.id.shimmer);

        String url = "https://pdfviewersite.000webhostapp.com/blood/profile.php?id="+ID;

        VolleyRequest(url);

        /*Name.setText(NAME);
        Blood.setText(BLDGRP);
        DonateDay.setText(DONATEDAY);
        phone.setText(MOBILE);
        email.setText(EMAIL);
        location.setText(LOCATION);
        ProfileName.setText(NAME);*//*
        String urIm = "https://pdfviewersite.000webhostapp.com/blood/Images/"+BIT;
        Picasso.get()
                .load(urIm)
                .into(ProfImg);*/



        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),EditProfile_Layout.class));
            }
        });

        donateday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(donateday.getTag() == "lock"){
                    Toast.makeText(getContext(), "He will availabe after 4 months", Toast.LENGTH_SHORT).show();
                }else{
                    new AlertDialog.Builder(getContext())
                            .setTitle("Alert.......!!")
                            .setMessage("Are you sure you have donated today...??? You will available after 4 month to doanate blood again.")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DonateToday();
                                    donateday.setBackgroundColor(Color.GRAY);
                                    donateday.setTag("lock");
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }




            }
        });


        return  view;
    }

    //Volley library...........
    public void VolleyRequest(String Url){
        relative.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                shimmer.setVisibility(View.GONE);
                relative.setVisibility(View.VISIBLE);
                shimmer.stopShimmer();
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String Email = jsonObject.getString("email");
                        String Mobile = jsonObject.getString("mobile");
                        String Password = jsonObject.getString("password");
                        String dept = jsonObject.getString("department");
                        String bldgrp = jsonObject.getString("blood");
                        String Location = jsonObject.getString("upozilla");
                        String LastDonate= jsonObject.getString("last_donate");
                        String ImgLink = jsonObject.getString("img");
                        PROFNAME.setText(name);
                        Blood.setText(bldgrp);
                         DonateDay.setText(LastDonate);
        phone.setText(Mobile);


        email.setText(Email);
        location.setText(Location);
        Name.setText(name);
        String urIm = "https://pdfviewersite.000webhostapp.com/blood/Images/"+ImgLink;
        Picasso.get()
                .load(urIm)
                .placeholder(R.drawable.baseline_person_24)
                .into(ProfImg);


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    //Blood today button code here............
    private void DonateToday(){
        /*Calendar calendar;
        SimpleDateFormat simpleDateFormat;


        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();

        Toast.makeText(getContext(), ""+dateTime, Toast.LENGTH_SHORT).show();*/

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        Toast.makeText(getContext(), ""+strDate, Toast.LENGTH_SHORT).show();


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://pdfviewersite.000webhostapp.com/blood/donateday.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getContext()," Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("lastdonate", strDate);
                params.put("id",ID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}