package com.undecode.htichat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.undecode.htichat.R;
import com.undecode.htichat.activities.BaseActivity;
import com.undecode.htichat.activities.ChatActivity;
import com.undecode.htichat.models.RoomsItem;
import com.undecode.htichat.models.User;
import com.undecode.htichat.network.API;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private BaseActivity activity;
    private Context context;
    private List<User> users;
    private List<RoomsItem> rooms;
    private Gson gson;

    public UsersAdapter(BaseActivity activity, Context context) {
        this.activity = activity;
        gson = new Gson();
        this.context = context;
        users = new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setRooms(List<RoomsItem> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.txtMessageCount.setVisibility(View.GONE);
        User user = users.get(position);
        Glide.with(holder.itemView).load(user.getImage()).into(holder.image);
        holder.txtName.setText(user.getName());
        holder.txtMessage.setText(user.getStatus());
        holder.txtDate.setText(user.getDateSeen());
        holder.itemView.setOnClickListener(v -> {
            RoomsItem room = isInRoom(user);
            if (room != null) {
                Intent intent = new Intent(context, ChatActivity.class);
                String obj = gson.toJson(room);
                intent.putExtra("room", obj);
                context.startActivity(intent);
            } else {
                activity.showProgressDialog();
                API.getInstance().createRoom(user.getId(), object -> {
                    activity.hideProgressDialog();
                    rooms.add(object);
                    Intent intent = new Intent(context, ChatActivity.class);
                    String obj = gson.toJson(object);
                    intent.putExtra("room", obj);
                    context.startActivity(intent);
                }, activity);
            }
        });
    }

    private RoomsItem isInRoom(User user) {
        RoomsItem room = null;
        for (RoomsItem temp :
                rooms) {
            if (temp.getUsers().size() == 2) {
                if (user.getId() == temp.getUsers().get(0).getId() || user.getId() == temp.getUsers().get(1).getId()) {
                    room = temp;
                    break;
                }
            }
        }
        return room;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
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

        UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
