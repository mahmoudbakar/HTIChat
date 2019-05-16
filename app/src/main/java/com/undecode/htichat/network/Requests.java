package com.undecode.htichat.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.undecode.htichat.utils.MyPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requests {
    private static final String TAG = "BAKAR API ";
    private static final int TIMEOUT = 10000;
    private static final int RETRIES = 2;
    private static Requests instance = null;
    private SingleRequestQueue singleRequestQueue;
    private MyPreference preference;

    private Requests(Context context) {
        singleRequestQueue = SingleRequestQueue.getInstance(context);
        preference = new MyPreference();
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
     * @param requestType       type ( GET - POST - DELETE )
     * @param url               the end point url
     * @param requestJsonObject JSONObject Request
     * @param priority          set request priority
     * @param responseListener  success response interface
     * @param errorResponse     error response interface
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
        } else {
            Log.wtf(TAG + "REQUEST", "There is no RequestObject for this");
        }
        OnResponse.ErrorResponse finalErrorResponse = errorResponse;
        JsonObjectRequest request = new JsonObjectRequest(requestType, url, requestJsonObject,
                response -> {
                    Log.e(TAG + "RESPONSE", response.toString());
                    responseListener.onSuccess(response);
                },
                error -> handleError(error, finalErrorResponse)) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Priority getPriority() {
                return priority;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getRequestHeaders();
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

    private void handleError(VolleyError error, OnResponse.ErrorResponse errorResponse) {
        int responseCode = 5000;
        if (error.networkResponse != null) {
            responseCode = error.networkResponse.statusCode;
            Log.wtf(TAG + "ERROR", responseCode + new String(error.networkResponse.data));
            errorResponse.onBadRequest(null);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(new String(error.networkResponse.data));
            } catch (JSONException e) {
            }
            switch (responseCode) {
                case 400:
                    errorResponse.onBadRequest(jsonObject);
                    break;
                case 401:
                    errorResponse.onNotAuthorized();
                    break;
                case 403:
                    errorResponse.onForbidden();
                    break;
                case 404:
                    errorResponse.onApiNotFound();
                    break;
                case 405:
                    errorResponse.onApiNotFound();
                    break;
                case 440:
                    errorResponse.onNotAuthorized();
                    break;
                case 500:
                    errorResponse.onServerSideError();
                    break;
            }
        } else {
            errorResponse.onNoInternet();
            Log.wtf(TAG + "ERROR", responseCode + " No Internet Connection Available");
        }
    }

    private void handle400(String message, OnResponse.ErrorResponse errorResponse, JSONObject jsonObject) {
        switch (message) {
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

    private Map<String, String> getRequestHeaders() {
        Map<String, String> header = new HashMap<>();

        header.put("AUTH", preference.getToken());
        for (Map.Entry<String, String> entry :
                header.entrySet()) {
            Log.wtf(TAG + "HEADER", entry.getKey() + " : " + entry.getValue());
        }
        return header;
    }
}
