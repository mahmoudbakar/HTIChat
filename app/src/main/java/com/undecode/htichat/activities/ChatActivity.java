package com.undecode.htichat.activities;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.undecode.htichat.R;
import com.undecode.htichat.chatuitools.ChatView;
import com.undecode.htichat.chatuitools.models.ChatMessage;
import com.undecode.htichat.models.MessagesItem;
import com.undecode.htichat.models.RoomsItem;
import com.undecode.htichat.models.SendMessageRequest;
import com.undecode.htichat.models.User;
import com.undecode.htichat.network.API;
import com.undecode.htichat.network.FileModel;
import com.undecode.htichat.network.OnResponse;
import com.undecode.htichat.utils.DateHelper;
import com.undecode.htichat.utils.MyPreference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    Handler handler = new Handler();
    @BindView(R.id.chatView)
    ChatView chatView;
    RoomsItem roomsItem;
    Gson gson;
    ArrayList<ChatMessage> messages;
    DateHelper dateHelper;
    User user;
    @BindView(R.id.btnAttach)
    ImageButton btnAttach;
    //private AudioRecordButton audioRecordButton;

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

//        audioRecordButton = (AudioRecordButton) findViewById(R.id.audio_record_button);
//        audioRecordButton.setOnAudioListener(new AudioListener() {
//            @Override
//            public void onStop(RecordingItem recordingItem) {
//                Toast.makeText(getBaseContext(), "Audio...", Toast.LENGTH_SHORT).show();
//                AudioRecording audioRecording = new AudioRecording(getBaseContext());
//
//                audioRecording.play(recordingItem);
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.d("MainActivity", "Error: " + e.getMessage());
//            }
//        });

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAttach)
    public void onViewClicked() {
        //final File[] actualImage = new File[1];

        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {
                //actualImage[0] = FileUtils.from(ChatActivity.this, r.getUri());
                //Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
                FileModel fileModel = null;
                fileModel = new FileModel(r.getPath());
                API.getInstance().uploadFile(fileModel, new OnResponse.ObjectResponse<String>() {
                    @Override
                    public void onSuccess(String object) {
                        SendMessageRequest request = new SendMessageRequest();
                        request.setRoomId(roomsItem.getRoomId());
                        request.setType("picture");
                        request.setFile(object);
                        request.setMessage("");
                        API.getInstance().sendMessage(request, new OnResponse.ObjectResponse<MessagesItem>() {
                            @Override
                            public void onSuccess(MessagesItem object) {

                            }
                        }, ChatActivity.this);
                    }
                });
            }
        })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                    }
                }).show((FragmentActivity) ChatActivity.this);
    }
}
