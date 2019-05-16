package com.undecode.htichat.fragments;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.undecode.htichat.R;
import com.undecode.htichat.adapters.UsersAdapter;
import com.undecode.htichat.models.RoomsResponse;
import com.undecode.htichat.network.API;

import butterknife.BindView;


public class UsersFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private UsersAdapter usersAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_users;
    }

    @Override
    protected String getTitle() {
        return "Users";
    }

    @Override
    protected void initView(View view) {
        initRecycler();
        getData();
    }

    private void initRecycler() {
        usersAdapter = new UsersAdapter(getBaseActivity(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(usersAdapter);
    }

    private void getData() {
        showProgressDialog();
        API.getInstance().getRooms(rooms -> {
            usersAdapter.setRooms(rooms.getRooms());
            API.getInstance().getUsers(users -> {
                hideProgressDialog();
                usersAdapter.setUsers(users.getUsers());
            }, this);
        }, this);

    }
}
