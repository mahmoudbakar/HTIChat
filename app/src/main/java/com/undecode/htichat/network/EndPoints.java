package com.undecode.htichat.network;

public interface EndPoints {
    String SERVER = "http://chat.undecode.com/public/";
    String REGISTER = SERVER + "api/users";
    String USERS = SERVER + "getusers.php";
    String UPLOAD = SERVER + "upload.php";
    String LOGIN = SERVER + "login.php";
    String ROOMS = SERVER + "getrooms.php";
    String ROOM = SERVER + "getroom.php";
    String SEND = SERVER + "sendmessage.php";
    String TOKEN = SERVER + "updatetoken.php";
    String CREATE_ROOM = SERVER + "createroom.php";
}
