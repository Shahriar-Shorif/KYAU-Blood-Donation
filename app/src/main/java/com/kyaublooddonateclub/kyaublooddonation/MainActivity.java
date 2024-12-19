package com.kyaublooddonateclub.kyaublooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        frameLayout = findViewById(R.id.frameLayout);
        drawerLayout = findViewById(R.id.drawelayout);
        navigationView = findViewById(R.id.navigationView);
        materialToolbar = findViewById(R.id.materialToolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout, materialToolbar,R.string.close,R.string.open);
        drawerLayout.addDrawerListener(toggle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, new main_search_layout_fragment());
        transaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.profile){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, new profile_fragment());
                    transaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(item.getItemId() == R.id.home){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, new main_search_layout_fragment());
                    transaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if(item.getItemId() == R.id.logout){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Do you want to Log out ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    /*transaction.add(android.R.id.content, new Log_in_fragment ()).commit();
                                    startActivity(new Intent(MainActivity.this,Log_in_fragment.class));*/
                                    Toast.makeText(MainActivity.this, "Log out successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
                return false;
            }

        });


    }

    // OnBackpressed method here...........................
///====================================================
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired
    private long mBackPressed;

    // When user click bakpress button this method is called
    @Override
    public void onBackPressed() {
        // When user press back button

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {

            Toast.makeText(getBaseContext(), "Press again to exit",
                    Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();



    } // end of onBackpressed method

    //#############################################################################################
}