package com.softxpert.application.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softxpert.application.Model.car.CarModel;

import com.softxpert.application.Model.reporsitories.Repository;
import com.softxpert.application.Utils.StopLoadingListener;

import java.util.ArrayList;

public class CarActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CarModel>> arrayListMutableLiveData;
    private Repository repository = Repository.getInstance();

    public LiveData<ArrayList<CarModel>> initializeViewModel(Context context, StopLoadingListener listener) {
        if (arrayListMutableLiveData == null) {
            arrayListMutableLiveData = new MutableLiveData<>();
            GetCarDataByPage(context,1,listener);
        }
        return arrayListMutableLiveData;
    }
    public void GetCarDataByPage(Context context,int carPage, StopLoadingListener stopLoadingListener) {
        repository.GetCarsDataByPage(arrayListMutableLiveData, context,carPage, stopLoadingListener);
    }

}
