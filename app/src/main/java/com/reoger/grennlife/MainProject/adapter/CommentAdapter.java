package com.reoger.grennlife.MainProject.adapter;

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
 * Created by 24540 on 2016/10/16.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Comment> mData = new ArrayList<>();

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mData = mData;
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
        Holder holder ;
        if (convertView == null) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(R.layout.item_comments, null);
            holder.mUserName = (TextView) convertView.findViewById(R.id.item_comments_username);
            holder.mComments = (TextView) convertView.findViewById(R.id.item_comments_content);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mUserName.setText(mData.get(position).getUser().getUsername());
        holder.mComments.setText(mData.get(position).getContent());
        return convertView;
    }

    class Holder {
        TextView mUserName;
        TextView mComments;
    }
}
