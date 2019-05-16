package com.undecode.htichat.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.undecode.htichat.R;
import com.undecode.htichat.activities.ChatActivity;
import com.undecode.htichat.models.RoomsItem;
import com.undecode.htichat.models.User;
import com.undecode.htichat.utils.MyPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {
    private Activity activity;
    private Context context;
    private List<RoomsItem> items;
    private Gson gson;

    public ChatsAdapter(Activity activity, Context context) {
        gson = new Gson();
        this.activity = activity;
        this.context = context;
        items = new ArrayList<>();
    }

    public List<RoomsItem> getItems() {
        return items;
    }

    public void setItems(List<RoomsItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomsItem item = items.get(position);
        User user;

        if (new MyPreference().getMine().getId() == item.getUsers().get(0).getId()){
            try{
                user = item.getUsers().get(1);
            } catch (IndexOutOfBoundsException e){
                user = item.getUsers().get(0);
            }
        }else {
                user = item.getUsers().get(0);
        }

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, ChatActivity.class);
            String room = gson.toJson(item);
            Log.wtf("BAKAR Room", room);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
        holder.txtName.setText(user.getName());
        try{
            holder.txtMessage.setText(item.getMessages().get(0).getMessage());
        }catch (Exception e){

        }
        Glide.with(holder.itemView).load(user.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtMessage)
        TextView txtMessage;
        @BindView(R.id.txtDate)
        TextView txtDate;
        @BindView(R.id.txtMessageCount)
        TextView txtMessageCount;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
