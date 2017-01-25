package com.example.ramen.clashroyaleupdates.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class VolleyUtils {

    public interface VolleyRequestListener {

        void onResponse(String response);

        void onError(String error);
    }

    public static void sendVolleyStringRequest(final Context context, String url, final VolleyRequestListener listener) {
        StringRequest volleyRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // successful response from server, check for success or failure
                        Log.d("MyVolley", "success");
                        if (listener != null) {
                            if (response != null)
                                listener.onResponse(response.trim());
                            else
                                listener.onResponse(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // volley error
                        String errMessage = error.getLocalizedMessage();
                        Log.d("MyVolley", "error");
                        if (listener != null) {
                            if (errMessage != null)
                                listener.onError(errMessage);
                            else
                                listener.onError("Network Error (Null Specified)");
                        }
                    }
                });

        Log.d("MyVolley", "sending volley req to " + url);
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyVolley.getInstance(context).addToRequestQueue(volleyRequest);
    }

    /************************************************************
     * FUNCTIONS BELOW THIS ARE UNUSED
     ***********************************************************/

    public static void sendVolleyJsonRequest(final Context context, String url, int method, final Map<String,
            String> params, int timeout, int numRetries, final VolleyRequestListener listener) {
        if (params != null)
            Log.d("MyVolley", params.toString());

        if (method == Request.Method.GET) {
            sendVolleyGetRequest(context, url, params, timeout, numRetries, listener);
        } else if (method == Request.Method.POST) {
            sendVolleyPostRequest(context, url, params, timeout, numRetries, listener);
        } else {
            Log.e("GOPI", "Unsupported HTTP request type");
        }
    }

    public static void sendVolleyJsonRequest(final Context context, String url, int method, final Map<String, String> params, int numRetries, final VolleyRequestListener listener) {
        sendVolleyJsonRequest(context, url, method, params, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, numRetries, listener);
    }

    public static void sendVolleyJsonRequest(final Context context, String url, int method, final Map<String, String> params, final VolleyRequestListener listener) {
        sendVolleyJsonRequest(context, url, method, params, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 1, listener);
    }

    private static void sendVolleyGetRequest(final Context context, String url, final Map<String,
            String> params, int timeout, int numRetries, final VolleyRequestListener listener) {

        if (params != null) {
            url += "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    String val = entry.getValue();
                    if (val != null && !val.isEmpty())
                        url = url + entry.getKey() + "=" + URLEncoder.encode(val, "UTF-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            url = url.substring(0, url.length() - 1);
        }

        StringRequest volleyRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // successful response from server, check for success or failure
                        Log.d("MyVolley", "success");
                        if (listener != null) {
                            if (response != null)
                                listener.onResponse(response.trim());
                            else
                                listener.onResponse(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // volley error
                        String errMessage = error.getLocalizedMessage();
                        Log.d("MyVolley", "error");
                        if (listener != null) {
                            if (errMessage != null)
                                listener.onError(errMessage);
                            else
                                listener.onError("Network Error (Null Specified)");
                        }
                    }
                }) {
        };

        Log.d("MyVolley", "sending volley req to " + url);
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                numRetries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyVolley.getInstance(context).addToRequestQueue(volleyRequest);
    }

    private static void sendVolleyPostRequest(final Context context, String url, final Map<String,
            String> params, int timeout, int numRetries, final VolleyRequestListener listener) {

        StringRequest volleyRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // successful response from server, check for success or failure
                        Log.d("MyVolley", "success");
                        if (listener != null) {
                            if (response != null)
                                listener.onResponse(response.trim());
                            else
                                listener.onResponse(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // volley error
                        String errMessage = error.getLocalizedMessage();
                        Log.d("MyVolley", "error");
                        if (listener != null) {
                            if (errMessage != null)
                                listener.onError(errMessage);
                            else
                                listener.onError("Network Error (Null Specified)");
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        Log.d("MyVolley", "sending volley req to " + url);
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(
                timeout,
                numRetries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyVolley.getInstance(context).addToRequestQueue(volleyRequest);
    }

    public static void sendVolleyPostBodyRequest(final Context context, String url, final Map<String,String> headers, final String httpPostBody, final VolleyRequestListener listener) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MyVolley", "success");
                        if (listener != null) {
                            if (response != null)
                                listener.onResponse(response.trim());
                            else
                                listener.onResponse(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errMessage = error.getLocalizedMessage();
                        Log.d("MyVolley", "error");
                        if (listener != null) {
                            if (errMessage != null)
                                listener.onError(errMessage);
                            else
                                listener.onError("Network Error (Null Specified)");
                        }
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };


        Log.d("My Volley", httpPostBody);
        Log.d("MyVolley", "sending volley req to " + url);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyVolley.getInstance(context).addToRequestQueue(postRequest);
    }
}
