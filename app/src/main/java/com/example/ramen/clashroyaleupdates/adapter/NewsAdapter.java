package com.example.ramen.clashroyaleupdates.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramen.clashroyaleupdates.R;
import com.squareup.picasso.Picasso;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private AdapterData[] Data;
    private Context context;

    public NewsAdapter(Context context){
        this.context = context;
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
        holder.mNewsTextView.setText(Data[position].mNewsData);
        Picasso.with(context).load(Data[position].mImageLink).into(holder.mImageIv);
        holder.mDate.setText(Data[position].mDate);
    }

    @Override
    public int getItemCount() {
        if (Data == null)
            return 0;
        else
            return Data.length;
    }

    public void setData(AdapterData[] adapterData) {
        this.Data = adapterData;
        notifyDataSetChanged();
    }
}
