package com.undecode.htichat.activities;


import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greentoad.turtlebody.mediapicker.MediaPicker;
import com.greentoad.turtlebody.mediapicker.core.MediaPickerConfig;
import com.undecode.htichat.R;
import com.undecode.htichat.chatuitools.ChatView;
import com.undecode.htichat.chatuitools.models.ChatMessage;
import com.undecode.htichat.models.MessagesItem;
import com.undecode.htichat.models.RoomsItem;
import com.undecode.htichat.models.SendMessageRequest;
import com.undecode.htichat.models.User;
import com.undecode.htichat.network.API;
import com.undecode.htichat.utils.DateHelper;
import com.undecode.htichat.utils.MyPreference;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ChatActivity extends BaseActivity {

    Handler handler = new Handler();
    @BindView(R.id.chatView)
    ChatView chatView;
    RoomsItem roomsItem;
    Gson gson;
    ArrayList<ChatMessage> messages;
    DateHelper dateHelper;
    User user;

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void initView() {
        showBackArrow();
        getData();
        refresh();
        chatView.setOnSentMessageListener(chatMessage -> {
            messages.add(chatMessage);
            SendMessageRequest request = new SendMessageRequest();
            request.setFile("");
            request.setRoomId(roomsItem.getRoomId());
            request.setType("message");
            request.setMessage(chatMessage.getMessage());
            API.getInstance().sendMessage(request, object -> Toast.makeText(ChatActivity.this, "sent", Toast.LENGTH_SHORT).show(), ChatActivity.this);
            return true;
        });
    }

    private void getData() {
        user = new MyPreference().getMine();
        gson = new Gson();
        dateHelper = new DateHelper();
        messages = new ArrayList<>();
        String room = getIntent().getStringExtra("room");
        Log.wtf("BAKAR Room2", room);
        roomsItem = gson.fromJson(room, RoomsItem.class);
        for (MessagesItem item :
                roomsItem.getMessages()) {
            if (item.getSenderId() == user.getId()) {
                messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.SENT, item.getType(), item.getFile()));
            } else {
                messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.RECEIVED, item.getType(), item.getFile()));
            }
        }
        Collections.reverse(messages);
        chatView.addMessages(messages);
    }

    private void refresh() {
        API.getInstance().getRoom(roomsItem.getRoomId(), object -> {
            messages = new ArrayList<>();
            for (com.undecode.htichat.models.room.MessagesItem item :
                    object.getMessages()) {
                if (item.getSenderId() == user.getId()) {
                    messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.SENT, item.getType(), item.getFile()));
                } else {
                    messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.RECEIVED, item.getType(), item.getFile()));
                }
            }
            Collections.reverse(messages);
            chatView.clearMessages();
            chatView.addMessages(messages);
            if (handler != null) {
                handler.postDelayed(() -> refresh(), 4000);
            }
        }, this);
    }

//    private void select(){
//        MediaPickerConfig pickerConfig = new MediaPickerConfig()
//                .setAllowMultiSelection(false)
//                .setUriPermanentAccess(true)
//                .setShowConfirmationDialog(true);
//
//
//        MediaPicker.with(this,MediaPicker.MediaTypes.IMAGE)
//                .setConfig(pickerConfig)
//                .setFileMissingListener(new MediaPicker.MediaPickerImpl.OnMediaListener() {
//                    @Override
//                    public void onMissingFileWarning() {
//                        //trigger when some file are missing
//                    }
//                })
//                .onResult()
//                .subscribe(new Observer<ArrayList<Uri>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) { }
//
//                    @Override
//                    public void onNext(ArrayList<Uri> uris) {
//                        //uris: list of uri
//                    }
//
//                    @Override
//                    public void onError(Throwable e) { }
//
//                    @Override
//                    public void onComplete() { }
//                });
//    }

    @Override
    protected void onDestroy() {
        handler = null;
        API.getInstance().cancelRequests();
        super.onDestroy();
    }
}
