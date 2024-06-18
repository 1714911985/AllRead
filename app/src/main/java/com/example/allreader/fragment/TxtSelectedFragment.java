package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.dao.FilesDao_Impl;
import com.example.allreader.room.database.AppDatabase;
import com.example.allreader.room.entity.Files;
import com.example.allreader.utils.Manager.MMKVManager;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.adapter.RecycleGridAdapter;
import com.example.allreader.utils.adapter.RecycleListAdapter;
import com.example.allreader.utils.entity.EventMessage;
import com.example.allreader.utils.util.EventBusUtils;
import com.example.allreader.utils.util.QueryMethodUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TxtSelectedFragment extends Fragment {
    private RecyclerView rvTxtSelected;
    private NestedScrollView nsvTxtSelectedNoResult;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private List<Files> filesList;
    private RecycleListAdapter recycleListAdapter;
    private RecycleGridAdapter recycleGridAdapter;
    private int originViewMethodId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_txt_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDatabase = AppDatabase.getInstance(getContext());
        filesDao = new FilesDao_Impl(appDatabase);

        rvTxtSelected = view.findViewById(R.id.rv_txt_selected);
        nsvTxtSelectedNoResult = view.findViewById(R.id.nsv_txt_selected_no_result);

        // 初始化适配器并设置到 RecyclerView
        int viewMethodId = MMKVManager.getInt("viewMethodId", R.id.bdrb_list);
        int sortMethodId = MMKVManager.getInt("sortMethodId", R.id.bdrb_date);
        int orderMethodId = MMKVManager.getInt("orderMethodId", R.id.bdrb_desc);

        originViewMethodId = viewMethodId;
        List<Files> initialFileList = new ArrayList<>();
        if (viewMethodId == R.id.bdrb_list) {
            recycleListAdapter = new RecycleListAdapter(initialFileList, filesDao);
            rvTxtSelected.setAdapter(recycleListAdapter);
            rvTxtSelected.setLayoutManager(new LinearLayoutManager(requireActivity()));
        } else {
            //grid
            recycleGridAdapter = new RecycleGridAdapter(requireActivity(), initialFileList, filesDao);
            rvTxtSelected.setAdapter(recycleGridAdapter);
            rvTxtSelected.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        }

        updateAdapter(viewMethodId, sortMethodId, orderMethodId);
    }

    private void updateAdapter(int viewMethodId, int sortMethodId, int orderMethodId) {
        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                filesList = QueryMethodUtils.chooseQueryMethod(filesDao, "TXT", sortMethodId, orderMethodId);

                // 在主线程更新 UI
                rvTxtSelected.post(new Runnable() {
                    @Override
                    public void run() {
                        if (viewMethodId == R.id.bdrb_grid) {
                            if (originViewMethodId == R.id.bdrb_list) {
                                recycleGridAdapter = new RecycleGridAdapter(requireActivity(), filesList, filesDao);
                                rvTxtSelected.setAdapter(recycleGridAdapter);
                                rvTxtSelected.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
                            } else {
                                rvTxtSelected.setLayoutManager(new GridLayoutManager(requireActivity(), 3)); // 确保布局管理器被设置
                                recycleGridAdapter.updateData(filesList);
                            }
                        } else {
                            if (originViewMethodId == R.id.bdrb_grid) {
                                recycleListAdapter = new RecycleListAdapter(filesList, filesDao);
                                rvTxtSelected.setAdapter(recycleListAdapter);
                                rvTxtSelected.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            } else {
                                rvTxtSelected.setLayoutManager(new LinearLayoutManager(requireActivity())); // 确保布局管理器被设置
                                recycleListAdapter.updateData(filesList);
                            }
                        }
                        if (filesList.size() > 0) {
                            nsvTxtSelectedNoResult.setVisibility(View.INVISIBLE);
                        } else {
                            nsvTxtSelectedNoResult.setVisibility(View.VISIBLE);
                        }
                        originViewMethodId = viewMethodId;
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(EventMessage message) {
        updateAdapter(message.getViewMethodId(), message.getSortMethodId(), message.getOrderMethodId());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }
}