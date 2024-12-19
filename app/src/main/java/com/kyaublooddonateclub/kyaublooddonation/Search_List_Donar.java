package com.kyaublooddonateclub.kyaublooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Search_List_Donar extends AppCompatActivity {

    RecyclerView recyclerView;
    public static String URL = "";
    ShimmerFrameLayout shimmerEffect;
    int month = 0;

    public static ArrayList<HashMap<String,String>>RootArray = new ArrayList<>();
    public static HashMap<String,String>hashMap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_donar);
        recyclerView = findViewById(R.id.recyclerview);
        shimmerEffect = findViewById(R.id.shimmerEffect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Results");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //String u = "https://pdfviewersite.000webhostapp.com/blood/retrievealldata.php"
        RootArray = new ArrayList<>();
        VolleyRequest(URL);

           /* new AlertDialog.Builder(Search_List_Donar.this)
                    .setTitle("Oops...!")
                    .setMessage("There no one available for this Search")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();*/


        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Search_List_Donar.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(Search_List_Donar.this).inflate(R.layout.smart_donor_item_list,parent,false);
        //TextView available = myview.findViewById(R.id.available);

        return new MyViewHolder(myview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        hashMap1 = RootArray.get(position);
        String id = hashMap1.get("id");
        String Name = hashMap1.get("name");
        String Email= hashMap1.get("email");
        String Mobile = hashMap1.get("mobile");
        String dept = hashMap1.get("department");
        String BloodGrp = hashMap1.get("bloodgroup");
        String Address= hashMap1.get("location");
        String lastDonate= hashMap1.get("lastdonate");
        String Img= hashMap1.get("img");

        String TimeAgo = covertTimeToText(lastDonate);

        holder.name.setText(Name);
        holder.department.setText("Department: "+dept);
        holder.add.setText("Address: "+Address);
        holder.bloodgroup.setText(BloodGrp);


        //pass the data from here to show profile file..............
        holder.click.setOnClickListener(v->{

            Show_Profile.NAM = Name;
            Show_Profile.BLDGRP = BloodGrp;
            Show_Profile.DEPT = dept;
            Show_Profile.ADDRESS = Address;
            Show_Profile.MAIL = Email;
            Show_Profile.NUM = Mobile;
            Show_Profile.LASTDOANTEDATE = lastDonate;
            Show_Profile.IMGG = Img;

            startActivity(new Intent(Search_List_Donar.this,Show_Profile.class));
        });


        //Loading image by picasso library.................
        String url = "https://pdfviewersite.000webhostapp.com/blood/Images/"+Img;
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.baseline_person_24)
                .into(holder.imageView1);
        //Last donate date set here............

        holder.lastDonate.setText("Last Donate: "+ TimeAgo);



        //calling button set here............
        holder.call.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + Mobile;
            intent.setData(Uri.parse(temp));
            startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return RootArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,department, bloodgroup,add,lastDonate,available,Unavailable,halfavailable;
        ImageView imageView1;
        CardView call,click;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        department = itemView.findViewById(R.id.department);
        bloodgroup = itemView.findViewById(R.id.bloodgroup);
        add = itemView.findViewById(R.id.add);
        imageView1 = itemView.findViewById(R.id.imageView1);
        call = itemView.findViewById(R.id.call);
        lastDonate = itemView.findViewById(R.id.lastDonate);
        available = itemView.findViewById(R.id.available);
        click = itemView.findViewById(R.id.click);
        Unavailable = itemView.findViewById(R.id.Unavailable);
        halfavailable = itemView.findViewById(R.id.halfavailable);
    }
}
}

   /* private String calculateTimeAgo(String lastDonate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse(lastDonate).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }*/

    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes "+suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                    if(convTime.contains("3 Months")){
                        month = 3;
                    } else if (convTime.contains("4 Months")) {
                        month = 4;
                    }
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" Days "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }


    //////==========================////////
public void VolleyRequest(String Url){
    shimmerEffect.setVisibility(View.VISIBLE);
    shimmerEffect.startShimmer();
    recyclerView.setVisibility(View.GONE);
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            shimmerEffect.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            shimmerEffect.stopShimmer();
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
                    String LastDonate= jsonObject.getString("last_donate");
                    String ImgLink = jsonObject.getString("img");


                   hashMap1 = new HashMap<>();
                    hashMap1.put("id",id);
                    hashMap1.put("name",name);
                    hashMap1.put("email",email);
                    hashMap1.put("mobile",Mobile);
                    hashMap1.put("password",Password);
                    hashMap1.put("department",dept);
                    hashMap1.put("bloodgroup",bldgrp);
                    hashMap1.put("location",location);
                    hashMap1.put("lastdonate",LastDonate);
                    hashMap1.put("img",ImgLink);
                    RootArray.add(hashMap1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(Search_List_Donar.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error",error.toString());
        }
    });

    RequestQueue requestQueue = Volley.newRequestQueue(Search_List_Donar.this);
    requestQueue.add(jsonArrayRequest);

}

    //1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Toast.makeText(this, ""+elapsedDays, Toast.LENGTH_SHORT).show();


    }

}