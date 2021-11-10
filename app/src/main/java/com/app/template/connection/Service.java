package com.app.template.connection;



import com.app.template.model.req.ReqAuth;
import com.app.template.model.res.ResAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    /*@Headers({
            "Content-Type:application/json"
    }*/
    @FormUrlEncoded
    @POST("index.php/auth/")
    Call<ResAuth> authRequest(@Body ReqAuth reqAuth);
//
//    @POST("index.php/delivery/")
//    Call<ResDelivery> deliveryRequest(@Body ReqDelivery reqDelivery);

}
