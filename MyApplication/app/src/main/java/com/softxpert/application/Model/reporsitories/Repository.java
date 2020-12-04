package com.softxpert.application.Model.reporsitories;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import com.softxpert.application.Model.car.CarModel;
import com.softxpert.application.Model.car.MainResponse;
import com.softxpert.application.Model.data.Api;
import com.softxpert.application.Utils.NetworkUtil;
import com.softxpert.application.Utils.StopLoadingListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository instance;

    private Repository() {

    }
    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void GetCarsDataByPage(final MutableLiveData<ArrayList<CarModel>> mCarModel, final Context context, int carPage, final StopLoadingListener stopLoadingListener) {
        try {

            Api.getClient().getCars(carPage).enqueue(new Callback <MainResponse<List<CarModel>>>() {
                @Override
                public void onResponse(Call<MainResponse<List<CarModel>>> call, Response<MainResponse<List<CarModel>>> response) {
                    if (!response.isSuccessful()) {
//                        showError(NetworkUtil.onGitHubResponseError(response));
                        stopLoadingListener.endingLoading("response not successful");
                        return;
                    }
                    if (response.body() == null || response.body().getData() == null) {
                        stopLoadingListener.endingLoading("No More Data");
                        return;
                    }
                    mCarModel.postValue((ArrayList<CarModel>) response.body().getData());
//                    stopLoadingListener.endingLoading("Successfully Added");
                }

                @Override
                public void onFailure(Call<MainResponse<List<CarModel>>> call, Throwable t) {
                    if(!NetworkUtil.isNetworkConnected(context)){
                        stopLoadingListener.endingLoading("Check Internet");
                    }else {
                        stopLoadingListener.endingLoading(t.getLocalizedMessage());
                    }

                }
            });
        } catch (Exception e) {
            stopLoadingListener.endingLoading(e.getLocalizedMessage());
        }
    }
}
