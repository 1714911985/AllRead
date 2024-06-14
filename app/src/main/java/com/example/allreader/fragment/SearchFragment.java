package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.dao.FilesDao_Impl;
import com.example.allreader.room.database.AppDatabase;
import com.example.allreader.room.entity.Files;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.adapter.RecycleListAdapter;

import java.util.List;

public class SearchFragment extends Fragment {
    private final static int SET_ADAPTER = 1001;
    private ImageView ivBack;
    private EditText etSearch;
    private RecyclerView rvShowSearch;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private List<Files> filesList;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == SET_ADAPTER) {
                setRecycleView(filesList, filesDao);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDatabase = AppDatabase.getInstance(requireActivity());
        filesDao = new FilesDao_Impl(appDatabase);

        ivBack = view.findViewById(R.id.iv_back);
        View svSearch = view.findViewById(R.id.sv_search);
        etSearch = svSearch.findViewById(R.id.et_search);
        rvShowSearch = view.findViewById(R.id.rv_show_search);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBackButton();
        listenEtSearchChange();
        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Files> allFiles = filesDao.getAllFiles();
                setRecycleView(allFiles,filesDao);
            }
        });
    }


    private void setBackButton() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
    }

    private void setRecycleView(List<Files> filesList, FilesDao filesDao) {
        RecycleListAdapter recycleListAdapter = new RecycleListAdapter(filesList, filesDao);
        rvShowSearch.setAdapter(recycleListAdapter);
        rvShowSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleListAdapter.notifyDataSetChanged();
        Log.e("TAG", "setRecycleView: " );
    }


    private void listenEtSearchChange() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        String searchText = etSearch.getText().toString();
                        if (TextUtils.isEmpty(searchText)){
                            filesList = filesDao.getAllFiles();
                        }else {
                            filesList = filesDao.fuzzyGetFiles(searchText);
                        }
                        Message message = Message.obtain();
                        message.what = SET_ADAPTER;
                        handler.sendMessage(message);

                    }
                });
            }
        });
    }
}