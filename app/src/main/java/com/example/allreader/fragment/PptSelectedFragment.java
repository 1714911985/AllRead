package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.allreader.utils.adapter.RecycleListAdapter;
import com.example.allreader.utils.util.QueryMethodUtils;

import java.util.ArrayList;
import java.util.List;


public class PptSelectedFragment extends Fragment {
    private RecyclerView rvPptSelected;
    private NestedScrollView nsvPptSelectedNoResult;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private List<Files> filesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ppt_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDatabase = AppDatabase.getInstance(getContext());
        filesDao = new FilesDao_Impl(appDatabase);

        rvPptSelected = view.findViewById(R.id.rv_ppt_selected);
        nsvPptSelectedNoResult = view.findViewById(R.id.nsv_ppt_selected_no_result);

        // 初始化适配器并设置到 RecyclerView
        int viewMethodId = MMKVManager.getInt("viewMethodId", R.id.bdrb_list);
        int sortMethodId = MMKVManager.getInt("sortMethodId", R.id.bdrb_date);
        int orderMethodId = MMKVManager.getInt("orderMethodId", R.id.bdrb_desc);

        List<Files> initialFileList = new ArrayList<>();
        RecycleListAdapter recycleListAdapter = new RecycleListAdapter(initialFileList, filesDao);
        if (viewMethodId == R.id.bdrb_list) {
            rvPptSelected.setAdapter(recycleListAdapter);
        } else {
            //grid
        }

        rvPptSelected.setLayoutManager(new LinearLayoutManager(getContext()));

        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                filesList = QueryMethodUtils.chooseQueryMethod(filesDao, "PPT", sortMethodId, orderMethodId);

                // 在主线程更新 UI
                rvPptSelected.post(new Runnable() {
                    @Override
                    public void run() {
                        recycleListAdapter.updateData(filesList);
                        if (filesList.size() > 0) {
                            nsvPptSelectedNoResult.setVisibility(View.INVISIBLE);
                        } else {
                            nsvPptSelectedNoResult.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}