package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.allreader.R;
import com.example.allreader.utils.custom_view.CustomRadioItem;


public class ChangeLanguageFragment extends Fragment {
    private Toolbar tbChangeLanguage;
    private CustomRadioItem criChinese, criEnglish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tbChangeLanguage = view.findViewById(R.id.tb_change_language);
        criChinese = view.findViewById(R.id.cri_chinese);
        criEnglish = view.findViewById(R.id.cri_english);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarButton();//设置toolbar的返回和确认按钮

    }

    private void setToolbarButton() {
        tbChangeLanguage.setNavigationIcon(R.drawable.ic_back_black);
        tbChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });

        tbChangeLanguage.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_confirm) {
                    //切换语言的逻辑

                    return true;
                }
                return false;
            }
        });
    }
}