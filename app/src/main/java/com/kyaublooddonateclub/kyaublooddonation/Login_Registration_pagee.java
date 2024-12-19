package com.kyaublooddonateclub.kyaublooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class Login_Registration_pagee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration_pagee);


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Login_Registration_pagee.this);
        View parentView = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
        MaterialButton signin = parentView.findViewById(R.id.signin);
        MaterialButton signup = parentView.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Registration_pagee.this, Sign_Up_form.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Registration_pagee.this,Sign_In.class));
            }
        });

        bottomSheetDialog.setContentView(parentView);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }
}