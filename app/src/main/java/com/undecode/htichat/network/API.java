package com.undecode.htichat.network;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.undecode.htichat.models.LoginResponse;
import com.undecode.htichat.models.RoomsResponse;
import com.undecode.htichat.models.SignupRequest;
import com.undecode.htichat.models.UpdateProfileRequest;
import com.undecode.htichat.models.User;
import com.undecode.htichat.models.UsersResponse;
import com.undecode.htichat.utils.MyPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class API {
    private static API api = null;
    private static Gson gson;
    private Requests requests;
    private MyPreference preference;

    private API() {
        this.requests = Requests.getInstance();
        preference = new MyPreference();
        gson = new Gson();
    }

    public static API getInstance() {
        if (api == null) {
            api = new API();
        }
        return api;
    }

    public void cancelRequests() {
        requests.canelRequestsByTag();
    }

    public void login(String phone, String password, OnResponse.ObjectResponse<LoginResponse> response, OnResponse.ErrorResponse errorResponse) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            String url = EndPoints.LOGIN;
            requests.jsonObjectRequest(Request.Method.POST, url, jsonObject, Request.Priority.IMMEDIATE,
                    new OnResponse.ObjectResponse<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject object) {
                            LoginResponse loginResponse = gson.fromJson(object.toString(), LoginResponse.class);
                            preference.login(loginResponse);
                            response.onSuccess(loginResponse);
                        }
                    }, errorResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void register(SignupRequest request, OnResponse.ObjectResponse<JSONObject> response, OnResponse.ErrorResponse errorResponse) {
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request.toString()));
            String url = EndPoints.USERS;
            requests.jsonObjectRequest(Request.Method.POST, url, jsonObject, Request.Priority.IMMEDIATE,
                    new OnResponse.ObjectResponse<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject object) {
                            response.onSuccess(object);
                        }
                    }, errorResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(UpdateProfileRequest request, OnResponse.ObjectResponse<JSONObject> response, OnResponse.ErrorResponse errorResponse) {
        try {
            JSONObject jsonObject = new JSONObject(gson.toJson(request.toString()));
            String url = EndPoints.USERS;
            requests.jsonObjectRequest(Request.Method.PUT, url, jsonObject, Request.Priority.IMMEDIATE,
                    new OnResponse.ObjectResponse<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject object) {
                            response.onSuccess(object);
                        }
                    }, errorResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUser(int id, OnResponse.ObjectResponse<LoginResponse> response, OnResponse.ErrorResponse errorResponse) {
        String url = EndPoints.LOGIN + "/" + id;
        requests.jsonObjectRequest(Request.Method.GET, url, null, Request.Priority.IMMEDIATE,
                new OnResponse.ObjectResponse<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        response.onSuccess(gson.fromJson(object.toString(), LoginResponse.class));
                    }
                }, errorResponse);
    }

    public void getUsers(OnResponse.ObjectResponse<UsersResponse> response, OnResponse.ErrorResponse errorResponse) {
        String url = EndPoints.LOGIN;
        requests.jsonObjectRequest(Request.Method.GET, url, null, Request.Priority.IMMEDIATE,
                object -> response.onSuccess(gson.fromJson(object.toString(), UsersResponse.class)), errorResponse);
    }

    public void getRooms(OnResponse.ObjectResponse<RoomsResponse> response, OnResponse.ErrorResponse errorResponse) {
        String url = EndPoints.ROOMS;
        requests.jsonObjectRequest(Request.Method.GET, url, null, Request.Priority.IMMEDIATE,
                object -> response.onSuccess(gson.fromJson(object.toString(), RoomsResponse.class)), errorResponse);
    }

}
