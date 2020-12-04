package com.softxpert.application.Views.CarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.softxpert.application.Model.car.CarModel;
import com.softxpert.application.ViewModel.CarActivityViewModel;
import com.softxpert.application.R;
import com.softxpert.application.Utils.StopLoadingListener;
import com.softxpert.application.databinding.ActivityMainCarBinding;

import java.util.ArrayList;

public class CarActivity extends AppCompatActivity {

    private ActivityMainCarBinding mainBinding;
    private CarActivityViewModel carActivityViewModel;
    private StopLoadingListener stopLoading;
    private CarRecyclerViewAdapter mAdapter;
    private ArrayList<CarModel> mCarsArrayList = new ArrayList<>();
    private static int carPage =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_car);
        carActivityViewModel = new ViewModelProvider(this).get(CarActivityViewModel.class);
        initLoadingListener();
        initAdapter();
        initModelObserver();
        initSwipeListener();

    }

    private void initLoadingListener() {
        stopLoading = new StopLoadingListener() {
            @Override
            public void endingLoading(String message) {
                mainBinding.progressBar.setVisibility(View.GONE);
                mainBinding.swipeRefreshRecyclerList.setRefreshing(false);
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void initModelObserver() {
        mainBinding.swipeRefreshRecyclerList.setRefreshing(true);
        carActivityViewModel.initializeViewModel(this,stopLoading).observe(this, new Observer<ArrayList<CarModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<CarModel> Cars) {
                if(Cars != null) {
                    mainBinding.progressBar.setVisibility(View.GONE);
                    mainBinding.swipeRefreshRecyclerList.setRefreshing(false);
                    mCarsArrayList.addAll(Cars);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initSwipeListener() {
        mainBinding.swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                carPage = 1;
                carActivityViewModel.GetCarDataByPage(CarActivity.this,carPage, stopLoading);

            }
        });
    }

    void initAdapter(){
        mAdapter = new CarRecyclerViewAdapter(CarActivity.this, mCarsArrayList);
        mainBinding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
        mainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mainBinding.recyclerView.setAdapter(mAdapter);
        mainBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mainBinding.progressBar.setVisibility(View.VISIBLE);
                    boolean canScrollDownMore = recyclerView.canScrollVertically(1);
                    if (!canScrollDownMore) {

                        getLoadMore();
                        onScrolled(recyclerView, 0, 1);
                    } else {
                        mainBinding.progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
        mAdapter.SetOnItemClickListener(new CarRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, CarModel carModel) {
                Toast.makeText(CarActivity.this, "Car Brand" + carModel.getBrand() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLoadMore() {
        carPage++;
        carActivityViewModel.GetCarDataByPage(CarActivity.this,carPage,stopLoading);
    }
}