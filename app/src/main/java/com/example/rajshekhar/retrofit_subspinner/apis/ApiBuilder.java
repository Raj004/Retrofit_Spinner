package com.example.rajshekhar.retrofit_subspinner.apis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.xml.transform.OutputKeys;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private static Retrofit retrofit;
    private static String BASE_URL = "http://www.androidbegin.com";
    static ApiEndPoints apiEndPoints;

    public static ApiEndPoints getInstance(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        OkHttpClient okHttpClient= new OkHttpClient();
        if(retrofit!=null){
            apiEndPoints=retrofit.create(ApiEndPoints.class);
            return apiEndPoints;
        }else {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient.newBuilder().connectTimeout(45, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            apiEndPoints=retrofit.create(ApiEndPoints.class);
        }
        return apiEndPoints;

    }

}
