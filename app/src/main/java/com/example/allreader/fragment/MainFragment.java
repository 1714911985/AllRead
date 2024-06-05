package com.example.allreader.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.allreader.R;
import com.example.allreader.utils.adapter.CollectAdapter;
import com.example.allreader.utils.adapter.GridAdapter;
import com.example.allreader.utils.entity.GridItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private DrawerLayout dlyMain;
    private Toolbar tbMain;
    private GridView gvClassification;
    private GridAdapter adapter;
    private TabLayout tlyCollect;
    private ViewPager2 vp2Collect;
    private NavigationView ngvDrawer;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindOpenButtonToToolBar();//绑定Toolbar的按钮
        setGridView();//设置gridview
        setViewPager2();//设置viewpager2
        setTabLayout();//配置tablayout
        setDrawerLayoutButton();//设置侧边栏的点击事件
        setToolBarButton();//设置toolbar两个按钮的点击事件

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dlyMain = view.findViewById(R.id.dly_main);
        tbMain = view.findViewById(R.id.tb_main);
        gvClassification = view.findViewById(R.id.gv_classification);
        tlyCollect = view.findViewById(R.id.tly_collect);
        vp2Collect = view.findViewById(R.id.vp2_collect);
        ngvDrawer = view.findViewById(R.id.ngv_drawer);

        navController = Navigation.findNavController(view);
    }

    private void setToolBarButton() {
        tbMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_search) {//搜索按钮
                    navController.navigate(R.id.fg_search, null, getNavOptions());
                    return true;
                } else if (item.getItemId() == R.id.item_file) {//文件按钮

                    return true;
                }
                return false;
            }
        });
    }

    private void setDrawerLayoutButton() {
        NavOptions navOptions = getNavOptions();

        ngvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_theme_mode) {//主题模式
                    setAndShowChangeThemeModeDialog();
                    return true;
                } else if (item.getItemId() == R.id.item_language) {//语言切换
                    navController.navigate(R.id.fg_language, null, navOptions);
                    return true;
                } else if (item.getItemId() == R.id.item_evaluate_us) {//评价
                    setAndShowRateUsDialog();
                    return true;
                } else if (item.getItemId() == R.id.item_analytical_applications) {//分享

                    return true;
                }
                return false;
            }
        });
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


    private void setAndShowRateUsDialog() {
        final Dialog dialog = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.dialog_rate_us);
        Window window = dialog.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dialog.show();

        dlyMain.close();
    }

    private void setAndShowChangeThemeModeDialog() {
        //                                                  设置dialog自带的背景框为透明
        final Dialog dialog = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.dialog_theme_mode);
        Window window = dialog.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dialog.show();

        dlyMain.close();
    }

    private void setViewPager2() {
        vp2Collect.setAdapter(new CollectAdapter(requireActivity()));
    }

    private void setTabLayout() {
        new TabLayoutMediator(tlyCollect, vp2Collect, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getText(R.string.recent));
                        break;
                    case 1:
                        tab.setText(getResources().getText(R.string.favorite));
                        break;
                }
            }
        }).attach();
    }

    private void setGridView() {
        List<GridItem> gridItemList = generateItems();
        adapter = new GridAdapter(requireActivity(), R.layout.grid_file, gridItemList);
        gvClassification.setAdapter(adapter);
    }

    private void updateSecondLine(int position, int fileNum) {
        GridItem item = (GridItem) adapter.getItem(position);
        item.setText2(fileNum + " " + getResources().getText(R.string.file));
        adapter.notifyDataSetChanged();
    }

    private List<GridItem> generateItems() {
        List<GridItem> gridItemList = new ArrayList<>();
        gridItemList.add(new GridItem(R.drawable.ic_home_all, getResources().getText(R.string.all).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_pdf, getResources().getText(R.string.pdf).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_doc, getResources().getText(R.string.document).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_xls, getResources().getText(R.string.xls).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_ppt, getResources().getText(R.string.ppt).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_txt, getResources().getText(R.string.txt).toString(), "0 文件"));
        gridItemList.add(new GridItem(R.drawable.ic_home_other, getResources().getText(R.string.other).toString(), "0 文件"));
        return gridItemList;
    }

    private void bindOpenButtonToToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), dlyMain, tbMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dlyMain.addDrawerListener(toggle);
        toggle.syncState();
    }
}