package com.suncode.yesorno;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/api")
    public Call<Model> getData();
}
