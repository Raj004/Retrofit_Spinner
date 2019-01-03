package com.example.rajshekhar.retrofit_subspinner.apis;

import com.example.rajshekhar.retrofit_subspinner.bean.PopulationBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoints {
    @GET("/tutorial/jsonparsetutorial.txt")
    Call<PopulationBean>getCountryDetails();
    
}
