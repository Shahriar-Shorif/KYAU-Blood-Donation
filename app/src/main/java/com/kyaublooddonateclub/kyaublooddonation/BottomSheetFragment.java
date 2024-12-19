package com.kyaublooddonateclub.kyaublooddonation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class BottomSheetFragment extends Fragment {

    MaterialButton signin,signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        signin = view.findViewById(R.id.signin);
        signup = view.findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "sdfsdf", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}