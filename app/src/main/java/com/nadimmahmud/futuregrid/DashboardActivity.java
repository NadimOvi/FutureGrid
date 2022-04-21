package com.nadimmahmud.futuregrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.nadimmahmud.futuregrid.Fragments.HomeFragment;

public class DashboardActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    int userPostId;
    BottomNavigationView bottomNavigationView;

    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        String value = "asdsd";
        initialise();
        progressBar = new ProgressDialog(DashboardActivity.this);
        progressBar.setMessage("Please Wait...");
        progressBar.setCancelable(false);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        float radius = getResources().getDimension(R.dimen.space_item_icon_default_size);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomappbar);

        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(
                bottomBarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
                        .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                        .build());

        bottomNavigationView.setBackground(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(value)).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Bundle bundle = new Bundle();
                Bundle params = new Bundle();
                Bundle newbundle = new Bundle();
                params.putInt("ButtonID", item.getItemId());
                switch (item.getItemId())
                {
                    case R.id.home :
                        selectedFragment = new HomeFragment(value);

                        break;
                    case R.id.history: selectedFragment = new HomeFragment(value);

                        break;
                    case R.id.list:
                        selectedFragment = new HomeFragment(value);


                        break;
                    /*case R.id.profile:

                        selectedFragment = new LoginFragment();
                        break;*/
                    case R.id.filter:

                        selectedFragment = new HomeFragment(value);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });

        /*if (isConnected()){



        }*/


    }
    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;

    }

    private void initialise() {

        progressDialog = new ProgressDialog(DashboardActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String userID= sharedPreferences.getString("userID",null);
        String password= sharedPreferences.getString("password",null);
        userPostId = sharedPreferences.getInt("userId",0);


    }
}