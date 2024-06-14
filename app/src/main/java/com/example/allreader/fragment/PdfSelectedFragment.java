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
import com.example.allreader.utils.Manager.MMKVManager;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.adapter.RecycleListAdapter;
import com.example.allreader.utils.entity.EventMessage;
import com.example.allreader.utils.util.EventBusUtils;
import com.example.allreader.utils.util.QueryMethodUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class PdfSelectedFragment extends Fragment {
    private RecyclerView rvPdfSelected;
    private NestedScrollView nsvPdfSelectedNoResult;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private List<Files> filesList;
    private RecycleListAdapter recycleListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf_selected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDatabase = AppDatabase.getInstance(getContext());
        filesDao = new FilesDao_Impl(appDatabase);

        rvPdfSelected = view.findViewById(R.id.rv_pdf_selected);
        nsvPdfSelectedNoResult = view.findViewById(R.id.nsv_pdf_selected_no_result);

        // 初始化适配器并设置到 RecyclerView
        int viewMethodId = MMKVManager.getInt("viewMethodId", R.id.bdrb_list);
        int sortMethodId = MMKVManager.getInt("sortMethodId", R.id.bdrb_date);
        int orderMethodId = MMKVManager.getInt("orderMethodId", R.id.bdrb_desc);

        List<Files> initialFileList = new ArrayList<>();
        recycleListAdapter = new RecycleListAdapter(initialFileList, filesDao);
        if (viewMethodId == R.id.bdrb_list) {
            rvPdfSelected.setAdapter(recycleListAdapter);
        } else {
            //grid
        }

        rvPdfSelected.setLayoutManager(new LinearLayoutManager(getContext()));

        updateAdapter(sortMethodId,orderMethodId);
    }

    private void updateAdapter(int sortMethodId, int orderMethodId) {
        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                filesList = QueryMethodUtils.chooseQueryMethod(filesDao, "PDF", sortMethodId, orderMethodId);

                Log.e("getAllFilesSortedByCreatedTimeDescending", System.currentTimeMillis() - currentTimeMillis + "");
                // 在主线程更新 UI
                rvPdfSelected.post(new Runnable() {
                    @Override
                    public void run() {
                        recycleListAdapter.updateData(filesList);
                        if (filesList.size() > 0) {
                            nsvPdfSelectedNoResult.setVisibility(View.INVISIBLE);
                        } else {
                            nsvPdfSelectedNoResult.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(EventMessage message) {
        updateAdapter(message.getSortMethodId(), message.getOrderMethodId());
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