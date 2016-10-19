package com.reoger.grennlife.user.myResuouers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.Recycle.model.OldThing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/17.
 */
public class ResourcesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<OldThing> mData = new ArrayList<>();

    public ResourcesAdapter(Context mContext, List data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        mData = data;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_myresources,null);
            holder.mUserName = (TextView) convertView.findViewById(R.id.item_myresources_username);
            holder.mTime = (TextView) convertView.findViewById(R.id.item_myresources_time);
            holder.mState = (TextView) convertView.findViewById(R.id.item_myresources_state);

            OldThing info = mData.get(position);

            holder.mUserName.setText(info.getAuthor().getUsername());
            holder.mTime.setText(info.getCreatedAt().toString());
            convertView.setTag(holder);
        }else{
            convertView.getTag();
        }
        return convertView;
    }

    class  Holder {
        TextView mUserName;
        TextView mTime;
        TextView mState;
    }
}
