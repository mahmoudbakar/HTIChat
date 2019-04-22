package com.undecode.htichat.network;

import org.json.JSONObject;

import java.util.List;

public interface OnResponse
{
    public interface ArrayResponse<T>
    {
        public void onSuccess(List<T> list);
    }

    public interface ObjectResponse<T>
    {
        public void onSuccess(T object);
    }

    public interface CartChanged
    {
        public void onCartCountChanged(int cartCount);
    }

    public interface ErrorResponse{
        public void onAlreadyLoggedIn();
        public void onNoInternet();
        public void onNotAuthorized();
        public void onNotAllowedMethod();
        public void onApiNotFound();
        public void onBadRequest(JSONObject object);
        public void onServerSideError();
        public void onForbidden();
    }
}