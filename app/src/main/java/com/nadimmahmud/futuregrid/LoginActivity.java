package com.nadimmahmud.futuregrid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nadimmahmud.futuregrid.Api.Service;
import com.nadimmahmud.futuregrid.Model.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText userID, userPass;
    private Button loginButton;
    ProgressDialog progressDialog;
    String userNameID,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initialize();
        if (isConnected()){
            if(sharedPreferences.contains("userID") && sharedPreferences.contains("password")){
                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }

            userPass.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    final int Right = 2;
                    if (event.getAction()==MotionEvent.ACTION_UP){
                        if (event.getRawX()>=userPass.getRight()-userPass.getCompoundDrawables()[Right].getBounds().width()){
                            int selection = userPass.getSelectionEnd();
                            if (passwordVisible){
                                userPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                                userPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                passwordVisible = false;
                            }else{
                                userPass.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                                userPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                passwordVisible = true;
                            }
                            userPass.setSelection(selection);
                            return true;
                        }
                    }
                    return false;
                }
            });


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginValidation();
                }
            });


        }
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
    private void initialize() {
        userID = findViewById(R.id.userID);
        userPass = findViewById(R.id.userPass);
        loginButton = findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void loginValidation() {
        userNameID= userID.getText().toString().trim();;
        password= userPass.getText().toString().trim();;

        if (userNameID.isEmpty()) {
            userID.setError("Please provide User ID");
            userID.requestFocus();
        }else if(password.isEmpty()){
            userPass.setError("Please provide password");
            userPass.requestFocus();
        }else{
            loginPost();
        }
    }
    private void loginPost() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://us.infrmtx.com/iot/fuelmatix/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Service service = retrofit.create(Service.class);
        Call<LoginModel> call = service.getLogin(userNameID,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()){
                    LoginModel loginModel = response.body();
                    int userId = loginModel.getUser_id();

                    editor.putString("userID",userNameID);
                    editor.putString("password",password);
                    editor.putInt("userId",userId);
                    /*editor.apply();*/
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        });

    }
}