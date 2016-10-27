package com.reoger.grennlife.user.monitoringHistroy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reoger.grennlife.R;
import com.reoger.grennlife.monitoring.Model.ReportInfo;

import java.util.List;

/**
 * Created by 24540 on 2016/10/11.
 * 举报历史信息的适配器
 */
public class MonitoringHistoryAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ReportInfo> mData;
    private static final String[] STATE={"...","审核中","审核通过","审核不通过"};

    public MonitoringHistoryAdapter(Context mContext,List mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder h = null;
        if (convertView == null){
            h = new holder();
            convertView = mInflater.inflate(R.layout.item_monitoring_history,null);
            h.mTitle = (TextView) convertView.findViewById(R.id.item_user_monitoring_title);
            h.mContent = (TextView) convertView.findViewById(R.id.item_user_monitoring_content);
            h.mTime = (TextView) convertView.findViewById(R.id.item_user_monitoring_time);
            h.mState = (TextView) convertView.findViewById(R.id.item_user_monitoring_state);
            convertView.setTag(h);
        }else{
           h = (holder) convertView.getTag();
        }

        ReportInfo info = mData.get(position);
        h.mTitle.setText(info.getTitle());
        h.mContent.setText(info.getContent());
        h.mTime.setText(info.getCreatedAt().toString());
        h.mState.setText(STATE[info.getStatuts()]);
        return convertView;
    }

    class holder{
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        TextView mState;
    }
}
