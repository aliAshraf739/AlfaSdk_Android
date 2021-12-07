package com.example.alfasdk.Network;


import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alfasdk.Const.ConnectionDetector;
import com.example.alfasdk.R;
import com.example.alfasdk.Util.Alert;

import org.json.JSONObject;

public class RestClient {

    Context context;
    public RestClient(Context context) {
        this.context=context;
    }

    private static final String TAG = "RestClient";
    static ConnectionDetector cd;
    private static ProgressDialog pd;

    public static void postRequest(final String action, Context context, String url,
                                   final JSONObject json, final OnRestClientCallback listener,
                                   final boolean isBackground, String loadingMsg) {

        Log.d(TAG, "url: " + url);
        Log.d(TAG, "action: " + action);

        cd = new ConnectionDetector(context);
//        if (cd.isConnectingToInternet()) {

//            if (!isBackground) {
//                pd = new ProgressDialog(context);
//                pd.setMessage(loadingMsg);
//                pd.setCancelable(false);
//                pd.setIndeterminate(true);
//                pd.show();
//            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    LogResponse(TAG, "response: " + jsonObject.toString());

                    if (!isBackground ) {

//                        pd.dismiss();

                    }

                    listener.onRestSuccess(jsonObject,
                            action);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

//                    if (!isBackground && pd.isShowing()) {
//
//                        pd.dismiss();
//
//                    }

                    listener.onRestError((Exception) error.getCause(), action);
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Add the request to the queue
            RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();
            queue.add(request);

//        } else {
//            Alert.show(context, "Error", "no internet");
//        }

    }


    public static void LogResponse(String tag, String response) {
        int maxLogSize = 1000;
        for (int i = 0; i <= response.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > response.length() ? response.length() : end;
            Log.d(tag, response.substring(start, end));
        }
    }


}
