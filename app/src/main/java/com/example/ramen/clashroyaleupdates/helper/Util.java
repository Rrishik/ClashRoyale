package com.example.ramen.clashroyaleupdates.helper;

import android.content.Context;
import android.widget.ImageView;

import com.example.ramen.clashroyaleupdates.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Gopi on 09-01-2017.
 */

public class Util {

    public static void loadImageByPicasso(Context context, ImageView imageView, String imageUrl) {

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .noFade()
                .into(imageView);
    }

    public static Date convertToDate(String crDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        try {
            return sdf.parse(crDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToString(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    public static String reformatDate(String crDate) {
        return convertToString(convertToDate(crDate));
    }

}
