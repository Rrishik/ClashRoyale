package com.example.ramen.clashroyaleupdates;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_main);
        mLoading = (ProgressBar) findViewById(R.id.pb_loading);
        loadData();

    }

    private void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
    }

    private  void showNews(){
        mLoading.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
    }

    private void loadData(){

        showLoading();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://clashroyale.com/blog/news";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showNews();
                        parsePage(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showNews();
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

     private void parsePage(String page){
        Document doc = Jsoup.parse(page);
        Elements divs = doc.getElementsByClass("home-news-primary-item-holder");
        for (Element div : divs){
            Elements aTags = div.getElementsByTag("a");
            mTextView.append(aTags.attr("href")+"\n\n\n");
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_refresh){
            Context context = MainActivity.this;
            String toastText = "Refresh";
            Toast.makeText(context,toastText,Toast.LENGTH_SHORT).show();
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
