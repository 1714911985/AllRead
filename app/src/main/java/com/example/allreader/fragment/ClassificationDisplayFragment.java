package com.example.allreader.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.allreader.R;
import com.example.allreader.utils.adapter.ClassificationDisplayAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ClassificationDisplayFragment extends Fragment {
    private NavController navController;
    private Toolbar tbClassificationDisplay;
    private TabLayout tlyClassificationDisplay;
    private ViewPager2 vp2ClassificationDisplay;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classification_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        navController = Navigation.findNavController(view);
        tbClassificationDisplay = view.findViewById(R.id.tb_classification_display);
        tlyClassificationDisplay = view.findViewById(R.id.tly_classification_display);
        vp2ClassificationDisplay = view.findViewById(R.id.vp2_classification_display);
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolBarButton();
        setViewPager2();
        setTabLayout();

    }

    private void setViewPager2() {
        vp2ClassificationDisplay.setAdapter(new ClassificationDisplayAdapter(requireActivity()));
    }

    private void setToolBarButton() {

        tbClassificationDisplay.setNavigationIcon(R.drawable.ic_back_black);
        setToolBarTitle(position);
        tbClassificationDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
        tbClassificationDisplay.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {//搜索按钮
                    navController.navigate(R.id.fg_search, null, getNavOptions());
                    return true;
                } else if (item.getItemId() == R.id.item_show) {//展示方式按钮

                    return true;
                }
                return false;
            }
        });
    }

    private void setToolBarTitle(int position) {
        CharSequence title = null;
        switch (position) {
            case 0:
                title = getResources().getText(R.string.title_all);
                break;
            case 1:
                title = getResources().getText(R.string.title_pdf);
                break;
            case 2:
                title = getResources().getText(R.string.title_document);
                break;
            case 3:
                title = getResources().getText(R.string.title_xls);
                break;
            case 4:
                title = getResources().getText(R.string.title_ppt);
                break;
            case 5:
                title = getResources().getText(R.string.title_txt);
                break;
            case 6:
                title = getResources().getText(R.string.title_other);
                break;
        }
        tbClassificationDisplay.setTitle(title);

    }

    private NavOptions getNavOptions() {
        // 创建一个NavOptions实例
        return new NavOptions.Builder()
                .setEnterAnim(R.anim.common_slide_in_right) // 进入动画
                .setExitAnim(R.anim.common_slide_out_left)   // 退出动画
                .setPopEnterAnim(R.anim.common_slide_in_left) // 回退进入动画
                .setPopExitAnim(R.anim.common_slide_out_right)// 回退退出动画;
                .build();
    }

    private void setTabLayout() {
        new TabLayoutMediator(tlyClassificationDisplay, vp2ClassificationDisplay, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getText(R.string.ALL));
                        break;
                    case 1:
                        tab.setText(getResources().getText(R.string.PDF));
                        break;
                    case 2:
                        tab.setText(getResources().getText(R.string.WORD));
                        break;
                    case 3:
                        tab.setText(getResources().getText(R.string.EXCEL));
                        break;
                    case 4:
                        tab.setText(getResources().getText(R.string.SLIDE));
                        break;
                    case 5:
                        tab.setText(getResources().getText(R.string.TXT));
                        break;
                    case 6:
                        tab.setText(getResources().getText(R.string.OTHER));
                        break;
                }
            }
        }).attach();
        TabLayout.Tab tab = tlyClassificationDisplay.getTabAt(position);
        if (tab != null) {
            tab.select();
        }

        tlyClassificationDisplay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setToolBarTitle(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}