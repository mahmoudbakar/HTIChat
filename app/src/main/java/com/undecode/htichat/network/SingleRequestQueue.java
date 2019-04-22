package com.undecode.htichat.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SingleRequestQueue {
    private static SingleRequestQueue instance;
    private static Context context;
    private RequestQueue requestQueue;

    private SingleRequestQueue(Context mContext) {
        context = mContext;
        requestQueue = getRequestQueue();
    }

    public static synchronized SingleRequestQueue getInstance(Context mContext) {
        if (instance == null) {
            instance = new SingleRequestQueue(mContext);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
//            HttpStack httpStack;
//            if (Build.VERSION.SDK_INT > 19){
//                httpStack = new CustomHurlStack();
//            } else if (Build.VERSION.SDK_INT >= 9 && Build.VERSION.SDK_INT <= 19)
//            {
//                httpStack = new OkHttpHurlStack();
//            } else {
//                httpStack = new HttpClientStack(AndroidHttpClient.newInstance("Android"));
//            }
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
