package com.example.ramen.clashroyaleupdates.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramen.clashroyaleupdates.R;
import com.example.ramen.clashroyaleupdates.helper.Util;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private List<AdapterData> mDataset;
    private Context mContext;

    public NewsAdapter(Context context) {
        mContext = context;
        mDataset = new ArrayList<>();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mNewsTextView;
        public final ImageView mImageIv;
        public final TextView mDate;

        public NewsAdapterViewHolder(View view) {
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
            mImageIv = (ImageView) view.findViewById(R.id.iv_news_image);
            mDate = (TextView) view.findViewById(R.id.tv_date);
        }

    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_list, viewGroup, false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.mNewsTextView.setText(mDataset.get(position).mNewsData);
        Util.loadImageByPicasso(mContext, holder.mImageIv, mDataset.get(position).mImageLink);
        holder.mDate.setText(Util.reformatDate(mDataset.get(position).mDate));
    }

    @Override
    public int getItemCount() {
        if (mDataset == null)
            return 0;
        return mDataset.size();
    }

    public void clear(){
        mDataset.clear();
    }

    public void setData(List<AdapterData> adapterData) {
        mDataset.clear();
        mDataset.addAll(adapterData);
        notifyDataSetChanged();
    }
}
