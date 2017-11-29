package com.myapp.adminside.api;


import com.myapp.adminside.model.BaseResponse;
import com.myapp.adminside.model.Site;
import com.myapp.adminside.model.Stuts;
import com.myapp.adminside.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ishan on 27-11-2017.
 */

public interface AppApi {

    @POST("UserLogin.php")
    Call<BaseResponse<User>> getLogin(@Body User user);

    @POST("UserRegister.php")
    Call<BaseResponse<User>> getRegister(@Body User user);

    @POST("AddSite.php")
    Call<BaseResponse<Site>> addSite(@Body Site site);

    @GET("GetSites.php")
    Call<BaseResponse<Site>> getSite();

    @POST("DeleteSite.php")
    Call<BaseResponse<Site>> deleteSite(@Body Site site);

    @GET("GetStuts.php")
    Call<BaseResponse<Stuts>> getStuts();

}
