package com.undecode.htichat.activities;


import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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

public class ChatActivity extends BaseActivity {

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
    protected void initView() {
        showBackArrow();
        getData();
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
                messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.SENT));
            } else {
                messages.add(new ChatMessage(item.getMessage(), dateHelper.getTimeOfDate(item.getSendDate()), ChatMessage.Type.RECEIVED));
            }
        }
        Collections.reverse(messages);
        chatView.addMessages(messages);
    }
}
