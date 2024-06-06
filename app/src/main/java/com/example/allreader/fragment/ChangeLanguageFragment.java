package com.example.allreader.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.allreader.MainActivity;
import com.example.allreader.R;
import com.example.allreader.utils.custom_view.CustomRadioItem;
import com.example.allreader.utils.custom_view.RadioGroupView;
import com.tencent.mmkv.MMKV;

import java.util.Locale;
import java.util.Objects;


public class ChangeLanguageFragment extends Fragment {
    private Toolbar tbChangeLanguage;
    private CustomRadioItem criChinese, criEnglish;
    private RadioGroupView rgvChange;
    private MMKV mmkv;

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
        rgvChange = view.findViewById(R.id.rgv_change);
        MMKV.initialize(requireActivity());
        mmkv = MMKV.defaultMMKV();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarButton();//设置toolbar的返回和确认按钮
        setCheckedCustomRadioItem();

    }

    private void setCheckedCustomRadioItem() {
        String language = mmkv.decodeString("language", "zh");

        switch (Objects.requireNonNull(language)) {
            case "zh":
                criChinese.setChecked(true);
                rgvChange.check(criChinese);
                criChinese.performClick();
                break;
            case "en":
                criEnglish.setChecked(true);
                rgvChange.check(criEnglish);
                criEnglish.performClick();
                break;
            default:
                break;
        }
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
                    changeLanguage();
                    return true;
                }
                return false;
            }

        });
    }

    private void changeLanguage() {
        if (criChinese.isChecked()) {
            setLanguage("zh", "CN");
        } else if (criEnglish.isChecked()) {
            setLanguage("en", "GB");
        }
    }

    private void setLanguage(String language, String country) {
        Locale locale = new Locale(language, country);

        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        requireActivity().finish();
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        mmkv.encode("language", language);
        startActivity(refresh);
        Log.e("TAG", "setLanguage: " );
    }
}