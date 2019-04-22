package com.undecode.htichat.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Requests {
    private static final String TAG = "BAKAR API ";
    private static Requests instance = null;
    private static final int TIMEOUT = 10000;
    private static final int RETRIES = 2;

    private SingleRequestQueue singleRequestQueue;

    private Requests(Context context) {
        singleRequestQueue = SingleRequestQueue.getInstance(context);
    }

    public static synchronized Requests getInstance(Context context) {
        if (instance == null) {
            instance = new Requests(context);
        }
        return instance;
    }

    static Requests getInstance() {
        if (instance == null) {
            throw new IllegalStateException(Requests.class.getSimpleName() + "Is not initialized, Call getInstance(sendContextHere) first");
        }
        return instance;
    }

    /**
     * @param requestType
     * @param url
     * @param requestJsonObject
     * @param priority
     * @param responseListener
     * @param errorResponse     send this with null if you wanna use MasterBaseActivity Ashour
     */
    JsonObjectRequest jsonObjectRequest(int requestType,
                                        String url,
                                        JSONObject requestJsonObject,
                                        final Request.Priority priority,
                                        final OnResponse.ObjectResponse<JSONObject> responseListener,
                                        OnResponse.ErrorResponse errorResponse) {

        Log.d(TAG + "SEPARATE", "----------------------------------------------------------------------------------------------------------------");
        Log.d(TAG + "URL", url + "     " + requestType);
        if (requestJsonObject != null) {
            Log.e(TAG + "REQUEST", requestJsonObject.toString());
        }
        OnResponse.ErrorResponse finalErrorResponse = errorResponse;
        JsonObjectRequest tempRequest = null;
        JsonObjectRequest request = new JsonObjectRequest(requestType, url, requestJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG + "RESPONSE", response.toString());
                        responseListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if internet connection Available it will not be null
                        int responseCode = 9000;
                        if (error.networkResponse != null) {
                            responseCode = error.networkResponse.statusCode;
                        } else {
                            if (finalErrorResponse != null) {
                                //finalErrorResponse.onNoInternet();
                            }
                            Log.d(TAG + "ERROR", responseCode + " No Internet Connection Available");
                        }
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Priority getPriority() {
                return priority;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TAG);
        singleRequestQueue.addToRequestQueue(request);
        return request;
    }

    void canelRequestsByTag() {
        singleRequestQueue.getRequestQueue().cancelAll(TAG);
        Log.d(TAG, "Requests Canceled");
    }

    public void requestRetry(JsonObjectRequest request) {
        request.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TAG);
        singleRequestQueue.addToRequestQueue(request);
    }

    private void handle400(String message, OnResponse.ErrorResponse errorResponse, JSONObject jsonObject) {
        switch (message) {
            case "User is logged.":
                errorResponse.onAlreadyLoggedIn();
                break;
            case "User is not logged.":
                errorResponse.onNotAuthorized();
                break;
            case "User is not logged in":
                errorResponse.onNotAuthorized();
                break;
            default:
                errorResponse.onBadRequest(jsonObject);
        }
    }
}