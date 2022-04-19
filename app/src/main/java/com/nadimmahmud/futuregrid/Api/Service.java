package com.nadimmahmud.futuregrid.Api;

import com.nadimmahmud.futuregrid.Model.LoginModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("login.php")
    Call<LoginModel> getLogin(@Query("user") String userName,
                              @Query("pass") String userPassword);


}
