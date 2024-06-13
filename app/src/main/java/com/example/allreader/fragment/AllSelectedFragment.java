package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
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
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.adapter.RecycleListAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllSelectedFragment extends Fragment {
    private final static String TAG = "AllSelectedFragment";
    private RecyclerView rvAllSelected;
    private NestedScrollView nsvAllSelectedNoResult;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private List<Files> filesList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDatabase = AppDatabase.getInstance(getContext());
        filesDao = new FilesDao_Impl(appDatabase);

        rvAllSelected = view.findViewById(R.id.rv_all_selected);
        nsvAllSelectedNoResult = view.findViewById(R.id.nsv_all_selected_no_result);

        // 初始化适配器并设置到 RecyclerView
        List<Files> initialFileList = new ArrayList<>();
        RecycleListAdapter recycleListAdapter = new RecycleListAdapter(initialFileList, filesDao);
        rvAllSelected.setAdapter(recycleListAdapter);

        rvAllSelected.setLayoutManager(new LinearLayoutManager(getContext()));

        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                filesList = filesDao.getAllFilesSortedByCreatedTimeDescending();
                Log.e("getAllFilesSortedByCreatedTimeDescending", System.currentTimeMillis()-currentTimeMillis+"" );
                // 在主线程更新 UI
                rvAllSelected.post(new Runnable() {
                    @Override
                    public void run() {
                        recycleListAdapter.updateData(filesList);
                        if (filesList.size() > 0) {
                            nsvAllSelectedNoResult.setVisibility(View.INVISIBLE);
                        } else {
                            nsvAllSelectedNoResult.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });


    }
}