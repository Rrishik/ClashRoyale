package com.risk.clashroyaleupdates.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risk.clashroyaleupdates.R;
import com.risk.clashroyaleupdates.helper.Util;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private List<AdapterData> mDataset;
    private Context mContext;
    private final RecyclerViewClickListener mOnclickListener;

    public NewsAdapter(Context context, RecyclerViewClickListener listener) {

        mOnclickListener = listener;
        mContext = context;
        mDataset = new ArrayList<>();
    }

    public interface RecyclerViewClickListener {
        void onClickListener(String link);
    }


    class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mNewsTextView;
        final ImageView mImageIv;
        final TextView mDate;

        NewsAdapterViewHolder(View view) {
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
            mImageIv = (ImageView) view.findViewById(R.id.iv_news_image);
            mDate = (TextView) view.findViewById(R.id.tv_date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String link = mDataset.get(getAdapterPosition()).mLink;
            mOnclickListener.onClickListener(link);
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

    public void clear() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public void setData(List<AdapterData> adapterData) {
        mDataset.clear();
        mDataset.addAll(adapterData);
        notifyDataSetChanged();
    }
}
