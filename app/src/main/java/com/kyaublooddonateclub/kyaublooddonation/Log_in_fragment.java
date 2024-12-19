package com.kyaublooddonateclub.kyaublooddonation;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Log_in_fragment extends Fragment{

    TextInputEditText emailphone,pass;
    Button signinbtn;
    TextView forgotpassword;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in_fragment, container, false);
        emailphone = view.findViewById(R.id.emailphone);
        pass = view.findViewById(R.id.pass);
        signinbtn = view.findViewById(R.id.signinbtn);
        forgotpassword = view.findViewById(R.id.forgotpassword);



        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable(getContext())){
                    String Phone = emailphone.getText().toString().trim();
                    String Password = pass.getText().toString().trim();


                    if(emailphone.length() == 0){
                        emailphone.setError("Wrong info");
                    } else if (pass.length() == 0) {
                        pass.setError("Wrong info");
                    }else {
                        //Login(Phone, Password);
                    }
                }else {
                    Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Forgot password click code here.............
        forgotpassword.setOnClickListener(v->{
            Calendar c = Calendar.getInstance();
            Date Today = c.getTime();
            c.add(Calendar.DATE, 0);
            c.add(Calendar.MONTH, 4);
            c.add(Calendar.YEAR, 0);
            Date future = c.getTime();


            Toast.makeText(getContext(), ""+future, Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(getContext(),Forgot_Password.class));
        });

        return view;
    }



    ///////Check internet connection.....
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}