package com.softxpert.application.Views.CarView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.softxpert.application.Model.car.CarModel;
import com.softxpert.application.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CarRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<CarModel> mCarArrayList;
    private OnItemClickListener mItemClickListener;


    public CarRecyclerViewAdapter(Context context, ArrayList<CarModel> modelList) {
        this.mContext = context;
        this.mCarArrayList = modelList;
    }
    public void updateAdapterData(ArrayList<CarModel> carModelArrayList) {
        this.mCarArrayList.addAll(carModelArrayList);
        notifyDataSetChanged();

    }
    public void clear()
    {
        mCarArrayList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car_recycler_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof ViewHolder) {
            final CarModel carItem = getItem(position);
            Glide.with(mContext).load(carItem.getImageUrl()).into(((ViewHolder) holder).mImageViewCar);
            ((ViewHolder) holder).mItemBrandName.setText(carItem.getBrand());
            ((ViewHolder) holder).mItemYearOfMan.setText(carItem.getConstractionYear());
            if (carItem.getIsUsed()){
                ((ViewHolder) holder).mImageViewCarStatus.setImageDrawable(mContext.getDrawable(R.drawable.ic_used_vector));
            }else{
                ((ViewHolder) holder).mImageViewCarStatus.setImageDrawable(mContext.getDrawable(R.drawable.ic_new_vector));
            }

        }
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mCarArrayList.size();
    }

    private CarModel getItem(int position) {
        return mCarArrayList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, CarModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView_car)
        public ImageView mImageViewCar;
        @BindView(R.id.item_brand_name)
        public TextView mItemBrandName;
        @BindView(R.id.item_yearofman)
        public TextView mItemYearOfMan;
        @BindView(R.id.imageView_car_status)
        public ImageView mImageViewCarStatus;


        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, getAdapterPosition(), mCarArrayList.get(getAdapterPosition()));
                }
            });
        }

    }

}

