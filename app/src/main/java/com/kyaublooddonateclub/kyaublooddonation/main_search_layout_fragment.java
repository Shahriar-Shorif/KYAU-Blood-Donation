package com.kyaublooddonateclub.kyaublooddonation;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;


public class main_search_layout_fragment extends Fragment {

    MaterialButton search;
    ImageView img;
    TextView name,dept,addrs,lastDnt,Bldgroup,gender;
    public static String NAME,DEPT,ADDRS,LASTDNT,BLDGRP,IMG,GENDER;
    public static String ID = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_search_layout_fragment, container, false);
        search = view.findViewById(R.id.search);
        img = view.findViewById(R.id.IMAGE);
        name = view.findViewById(R.id.Name);
        dept = view.findViewById(R.id.departmnt);
        addrs = view.findViewById(R.id.addrss);
        lastDnt = view.findViewById(R.id.lastDonte);
        Bldgroup = view.findViewById(R.id.Bldgroup);
        gender = view.findViewById(R.id.gender);


        //String url = "https://pdfviewersite.000webhostapp.com/blood/mainprofileidload.php?id="+ID;
        //VolleyRequest();


        //.>>>>>>>Blood group select dropdown menu>>>>>>>>>//
        String[] type = new String[]{"A+","B+","A-","B-","AB+","AB-","O+","O-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getContext(),R.layout.dropdownitem,type
        );

        AutoCompleteTextView autoCompleteTextView1  = view.findViewById(R.id.filled);
        autoCompleteTextView1.setAdapter(adapter1);

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        ///>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        //Department select drowpdown menu here................
        String[] type1 = new String[]{"All","CSE","EEE","MIS","BCBT","MICROBIOLOGY","Islamic studies","English","BBA"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(),R.layout.dropdownitem,type1
        );

        AutoCompleteTextView autoCompleteTextView2  = view.findViewById(R.id.filled1);
        autoCompleteTextView2.setAdapter(adapter2);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BloodGroup = autoCompleteTextView1.getText().toString();
                String Department = autoCompleteTextView2.getText().toString();

                //+(plus) Sign mysql not support. Thats why we have Encode it here and then send to database...
                try {
                    BloodGroup = URLEncoder.encode(BloodGroup,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                //==================================================================

                if(BloodGroup.length()==0 || Department.length()==0)
                {
                    Toast.makeText(getContext(), "Please select all field.", Toast.LENGTH_SHORT).show();
                }else {

                    if(Department.contains("All")){
                        String url = "https://pdfviewersite.000webhostapp.com/blood/retrievealldata.php";
                        Search_List_Donar.URL = url;
                        startActivity(new Intent(getContext(),Search_List_Donar.class));
                    }else{
                        String Url = "https://pdfviewersite.000webhostapp.com/blood/bloodsearch.php?b="+BloodGroup+"&d="+Department;

                        //Pass the url to Search List donor java file.........
                        Search_List_Donar.URL = Url;
                        startActivity(new Intent(getContext(),Search_List_Donar.class));
                    }

                }




            }
        });



        return view;
    }

    //============================*******************==============================//
    //============================*******************==============================//
    //============================*******************==============================//
    public void VolleyRequest(String Url){
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String Name = jsonObject.getString("name");
                        String Email = jsonObject.getString("email");
                        String Mobile = jsonObject.getString("mobile");
                        String Password = jsonObject.getString("password");
                        String Dept = jsonObject.getString("department");
                        String bldgrp = jsonObject.getString("blood");
                        String Location = jsonObject.getString("upozilla");
                        String LastDonate= jsonObject.getString("last_donate");
                        String ImgLink = jsonObject.getString("img");
                        String Gender = jsonObject.getString("gender");



                        //public static String NAME,DEPT,ADDRS,LASTDNT,BLDGRP,IMG;
                        String url = "https://pdfviewersite.000webhostapp.com/blood/Images/"+ImgLink;
                        Picasso.get().load(url).placeholder(R.drawable.baseline_person_24).into(img);
                        name.setText(Name);
                        dept.setText("Department: "+Dept);
                        addrs.setText("Address: "+Location);
                        lastDnt.setText("Last Donate Date: "+LastDonate);
                        Bldgroup.setText("Blood Group: "+bldgrp);
                        gender.setText("Gender: "+Gender);



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
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }





}