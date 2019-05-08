package com.undecode.htichat.fragments;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.undecode.htichat.R;
import com.undecode.htichat.adapters.ChatsAdapter;
import com.undecode.htichat.network.API;

import butterknife.BindView;


public class ChatsFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private ChatsAdapter chatsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_chats;
    }

    @Override
    protected String getTitle() {
        return "Rooms";
    }

    @Override
    protected void initView(View view) {
        initRecycler();
        getData();
    }

    private void initRecycler() {
        chatsAdapter = new ChatsAdapter(getBaseActivity(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatsAdapter);
    }

    private void getData() {
        showProgressDialog();
        API.getInstance().getRooms(object -> {
            chatsAdapter.setItems(object.getRooms());
            hideProgressDialog();
        }, this);
    }

}
