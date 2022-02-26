package com.app.template.connection;



import com.app.template.model.req.ReqAuth;
import com.app.template.model.res.ResAuth;
import com.app.template.model.res.ResDelete;
import com.app.template.model.res.ResInsert;
import com.app.template.model.res.ResListKontak;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @FormUrlEncoded
    @POST("Loginact")
    Call<ResAuth> authRequest(@Field("username") String username,
                              @Field("password") String password,
                              @Field("token") String token);

    @GET("kontak/")
    Call<ResListKontak> listRequest();


    @FormUrlEncoded
    @POST("kontak/")
    Call<ResInsert> insertRequest(@Field("nama") String nama,
                                  @Field("nomor") String nomor);

    @FormUrlEncoded
    @PUT("kontak/")
    Call<ResInsert> updateRequest(@Field("id") String id,
                                  @Field("nama") String nama,
                                  @Field("nomor") String nomor);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "kontak/", hasBody = true)
    Call<ResDelete> deleteRequest(@Field("id") int id);

    @POST("Loginact/logout")
    Call<ResAuth> logoutRequest();
}
