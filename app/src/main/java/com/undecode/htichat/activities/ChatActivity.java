package com.undecode.htichat.activities;


import com.undecode.htichat.R;
import com.undecode.htichat.chatuitools.ChatView;
import com.undecode.htichat.chatuitools.models.ChatMessage;

import java.util.ArrayList;

import butterknife.BindView;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.chatView)
    ChatView chatView;

    @Override
    protected void initView() {
        showBackArrow();
        ArrayList<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("هاي يا صاحبي", 213543321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 2135432143, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213545321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543217, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213546321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543213, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213547321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543215, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213594321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543281, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213547321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 2135432121, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213546321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543215, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213543321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543212, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 2135421321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543214, ChatMessage.Type.RECEIVED));
        messages.add(new ChatMessage("هاي يا صاحبي", 213354321, ChatMessage.Type.SENT));
        messages.add(new ChatMessage("خير قولي", 213543221, ChatMessage.Type.RECEIVED));
        chatView.addMessages(messages);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }



}
