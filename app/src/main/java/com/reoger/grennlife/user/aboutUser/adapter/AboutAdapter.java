package com.reoger.grennlife.user.aboutUser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reoger.grennlife.MainProject.model.Comment;
import com.reoger.grennlife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 24540 on 2016/10/24.
 */
public class AboutAdapter extends BaseAdapter {
    private List<Comment> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public AboutAdapter(List<Comment> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_system_message, null);
            holder.mUser = (TextView) convertView.findViewById(R.id.item_message_user);
            holder.mTime = (TextView) convertView.findViewById(R.id.item_message_time);
            holder.mComments = (TextView) convertView.findViewById(R.id.item_message_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Comment comment = mData.get(position);
        holder.mUser.setText(comment.getDynamic().getTitle());
        holder.mTime.setText(comment.getCreatedAt().toString());
        holder.mComments.setText(comment.getContent());

        return convertView;
    }

    class Holder {
        private TextView mUser;
        private TextView mComments;
        private TextView mTime;
    }
}
