package com.example.ramen.clashroyaleupdates;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private String [] mNewsData;

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mNewsTextView;
        public NewsAdapterViewHolder(View view){
            super(view);
            mNewsTextView = (TextView) view.findViewById(R.id.tv_news_data);
        }

    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.news_list,viewGroup,false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.mNewsTextView.setText(mNewsData[position]);
    }

    @Override
    public int getItemCount() {
        if (mNewsData == null)
            return 0;
        else
            return mNewsData.length;
    }

    public void setmNewsData(String[] mNewsData) {
        this.mNewsData = mNewsData;
    }
}
