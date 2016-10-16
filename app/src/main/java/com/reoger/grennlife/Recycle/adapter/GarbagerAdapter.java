package com.reoger.grennlife.Recycle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.loginMVP.model.UserMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/13.
 */
public class GarbagerAdapter extends RecyclerView.Adapter<GarbagerAdapter.PublicHolder>{
    private Context mContext;
    private List<UserMode> mData = new ArrayList<UserMode>();

    public GarbagerAdapter(Context mContext, List<UserMode> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public PublicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_garbager_show,parent,false);
        PublicHolder holder = new PublicHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PublicHolder holder, int position) {
        UserMode userMode = mData.get(position);
        holder.mUserName.setText(userMode.getReallyName());
        holder.mPhoneNum.setText(userMode.getMobilePhoneNumber().toString());
//        if(userMode.getLocations()!=null)
//        holder.mLocation.setText(userMode.getLocations().toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PublicHolder extends RecyclerView.ViewHolder{

        private TextView mUserName;
        private TextView mPhoneNum;
        private TextView mLocation;


        public PublicHolder(View itemView) {
            super(itemView);
            mUserName = (TextView) (itemView).findViewById(R.id.item_garbager_user_name);
            mPhoneNum = (TextView) (itemView).findViewById(R.id.item_garbager_user_phone_num);
            mLocation = (TextView) (itemView).findViewById(R.id.item_garbager_user_location);
        }
    }


}
