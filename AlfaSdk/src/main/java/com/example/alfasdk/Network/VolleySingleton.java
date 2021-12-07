package com.example.alfasdk.Network;

/**
 * Developed by Hasham.Tahir on 1/20/2016.
 */

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.alfasdk.Application;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(Application.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

}
