package com.kyaublooddonateclub.kyaublooddonation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.kyaublooddonateclub.kyaublooddonation.api.Api_Services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Sign_Up_fragment extends Fragment {

    MaterialButton signup;
    TextInputEditText name,mail,mobile,password,upozilla;
    String Bld;
    RadioButton teacher,student,staff;

    String RadioButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign__up_fragment, container, false);
        signup = view.findViewById(R.id.signup);
        name = view.findViewById(R.id.name);
        mail = view.findViewById(R.id.mail);
        mobile = view.findViewById(R.id.mobile);
        password = view.findViewById(R.id.password);
        upozilla = view.findViewById(R.id.upozilla);
        teacher = view.findViewById(R.id.teacher);
        student = view.findViewById(R.id.student);
        staff = view.findViewById(R.id.staff);


        //.>>>>>>>Blood group select dropdown menu>>>>>>>>>//
        String[] type = new String[]{"A+","B+","A-","B-","AB+","AB-","O+","O-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                getContext(),R.layout.dropdownitem,type
        );

        AutoCompleteTextView autoComplete2  = view.findViewById(R.id.autoComplete2);
        autoComplete2.setAdapter(adapter1);


        //Department select drowpdown menu here................
        String[] type1 = new String[]{"CSE","EEE","MIS","BCBT","MICROBIOLOGY","Islamic studies","English","BBA"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                getContext(),R.layout.dropdownitem,type1
        );

        AutoCompleteTextView autoComplete1  = view.findViewById(R.id.autoComplete1);
        autoComplete1.setAdapter(adapter2);


        //.>>>>>>>Gender select dropdown menu>>>>>>>>>//
        String[] type3 = new String[]{"Male","Female","Other"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(
                getContext(),R.layout.dropdownitem,type3
        );

        AutoCompleteTextView gender  = view.findViewById(R.id.gender);
        gender.setAdapter(adapter3);


        //Radio button code here......
        teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(teacher.isChecked()){
                    RadioButton = "Teacher";
                }

            }
        });
        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(student.isChecked()){
                    RadioButton = "Student";
                }
            }
        });
        staff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(staff.isChecked()){
                    RadioButton = "Staff";
                }
            }
        });


//Onclick Listener for Sign Up button start here....
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Log_in_fragment.isNetworkAvailable(getContext())){

                        String dept = autoComplete1.getText().toString();
                        String bloodGrp = autoComplete2.getText().toString();
                        String Gender = gender.getText().toString();
                        String Name = name.getText().toString();
                        String Email = mail.getText().toString();
                        String Mobile = mobile.getText().toString();
                        String Password = password.getText().toString();
                        String Upozilla = upozilla.getText().toString();
                        try {
                            bloodGrp = URLEncoder.encode(bloodGrp,"UTF-8");
                           // Bld = bloodGrp;
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }



// Request a string response from the provided URL.


// Add the request to the RequestQueue.
                        if(Name.length()>0 && Email.length()>0 && dept.length()>0 && Mobile.length()>0 &&
                                Password.length()>0 && Upozilla.length()>0 && bloodGrp.length()>0 && Gender.length()>0 && RadioButton!=null){
                            if(isValidEmail(Email)){
                                //JsonObject(Name,Email,Mobile,Password,RadioButton,Upozilla,dept,Bld,Gender);

                                JsonStringRequest(Name,Email,Mobile,Password,RadioButton,Upozilla,dept,bloodGrp,Gender);

                            }else{
                                Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(getContext(), "Please fulfill all the field.", Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(getContext(), "Please Check your connection", Toast.LENGTH_SHORT).show();
                    }


                }
            });//Onclick Listener for Sign Up button start here....

        return view;
    }

    //==================User Define Function=====================//
    //Check Correct email......
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    //===============================================================================================//
    //===============================================================================================//
//    public void JsonObject(String name,String email, String mobile, String password, String profession, String upozilla, String department, String blood, String gender){
//        //String url = "https://shorifulislam377.top/kyau-blood-donation-club/Sign_up.php";
//
//
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//
//            jsonObject.put("name",name);
//            jsonObject.put("email",email);
//            jsonObject.put("mobile",mobile);
//            jsonObject.put("password",password);
//            jsonObject.put("profession",profession);
//            jsonObject.put("upozilla",upozilla);
//            jsonObject.put("department",department);
//            jsonObject.put("blood",blood);
//            jsonObject.put("gender",gender);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                Toast.makeText(getContext(), "Sign up successfully"+response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(jsonObjectRequest);
//    }


    public void JsonStringRequest(String name,String email, String mobile, String password, String profession, String upozilla, String department, String blood, String gender){


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Services.SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("password",password);
                params.put("profession",profession);
                params.put("address",upozilla);
                params.put("department",department);
                params.put("blood",blood);
                params.put("gender",gender);

                return params;
            }

           
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}