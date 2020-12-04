package com.softxpert.application.Model.data;


import com.softxpert.application.Model.car.CarModel;
import com.softxpert.application.Model.car.MainResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/v1/cars")
    Call<MainResponse<List<CarModel>>> getCars(@Query("page") int page);
}
