package com.suncode.yesorno;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Generator {
    public static final String BASE_URL = "https://yesno.wtf/";

    public static Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        return retrofit;
    }
}
