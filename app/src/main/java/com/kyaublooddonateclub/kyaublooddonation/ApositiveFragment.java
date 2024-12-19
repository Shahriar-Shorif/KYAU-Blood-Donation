package com.kyaublooddonateclub.kyaublooddonation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ApositiveFragment extends Fragment {

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_apositive, container, false);
        recyclerView = myview.findViewById(R.id.recyclerview);
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return myview;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.donator_details_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            //holder.Img.setImageDrawable(R.drawable.baseline_person_24);
            holder.profName.setText("Shahriar Shorif");
            holder.bldgrp.setText("Blood Group: A+");
            holder.dept.setText("Dept: CSE");
            holder.batch.setText("Batch: 15");

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView Img;
            TextView profName,dept,bldgrp,batch;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                profName = itemView.findViewById(R.id.profName);
                dept = itemView.findViewById(R.id.dept);
                bldgrp = itemView.findViewById(R.id.blgrp);
                batch = itemView.findViewById(R.id.batch);
                Img = itemView.findViewById(R.id.img);
            }
        }
    }
}