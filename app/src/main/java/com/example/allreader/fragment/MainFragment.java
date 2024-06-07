package com.example.allreader.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.allreader.MainActivity;
import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.dao.FilesDao_Impl;
import com.example.allreader.room.database.AppDatabase;
import com.example.allreader.room.database.AppDatabase_Impl;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.adapter.CollectAdapter;
import com.example.allreader.utils.adapter.GridAdapter;
import com.example.allreader.utils.entity.GridItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment implements View.OnClickListener {
    private DrawerLayout dlyMain;
    private Toolbar tbMain;
    private GridView gvClassification;
    private GridAdapter adapter;
    private TabLayout tlyCollect;
    private ViewPager2 vp2Collect;
    private NavigationView ngvDrawer;
    private NavController navController;
    private Dialog dlThemeMode, dlRateUs;
    private Button btnMaybeLater, btnRateNow;
    private MMKV mmkv;
    private FilesDao filesDao;
    private AppDatabase appDatabase;
    private static final String appPackageName = "com.example.allreader";
    //分享的URL   谷歌应用商城链接+包名
    private static final String appUrl = "https://play.google.com/store/apps/details?id=" + appPackageName;
    private int allFileCount, allPDFCount, allPPTCount, allTXTCount, allDOCCount, allXLSCount, allOTHERCount;
    private BroadcastReceiver scanFinishedBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    allFileCount = filesDao.getAllFileCount();
                    allPDFCount = filesDao.getAllPDFCount();
                    allPPTCount = filesDao.getAllPPTCount();
                    allDOCCount = filesDao.getAllDOCCount();
                    allTXTCount = filesDao.getAllTXTCount();
                    allXLSCount = filesDao.getAllXLSCount();
                    allOTHERCount = filesDao.getAllOTHERCount();
                    Log.e("MainFragment", "allFileCount: " + allFileCount);
                    Log.e("MainFragment", "allPDFCount: " + allPDFCount);
                    Log.e("MainFragment", "allPPTCount: " + allPPTCount);
                    Log.e("MainFragment", "allDOCCount: " + allDOCCount);
                    Log.e("MainFragment", "allTXTCount: " + allTXTCount);
                    Log.e("MainFragment", "allXLSCount: " + allXLSCount);
                    Log.e("MainFragment", "allOTHERCount: " + allOTHERCount);

                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            });


        }
    };

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setGridView();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
        initData();
        bindOpenButtonToToolBar();//绑定Toolbar的按钮
        setViewPager2();//设置viewpager2
//        setGridView();
        setTabLayout();//配置tablayout
        setDrawerLayoutButton();//设置侧边栏的点击事件
        setToolBarButton();//设置toolbar两个按钮的点击事件

    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private void initData() {
        IntentFilter filter = new IntentFilter("com.example.allreader.ACTION_SCAN_FINISHED");
        requireActivity().registerReceiver(scanFinishedBroadcast, filter);

    }

    @Override
    public void onResume() {
        super.onResume();
        setGridView();
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
        MMKV.initialize(requireActivity());
        mmkv = MMKV.defaultMMKV();
        appDatabase = AppDatabase.getInstance(getContext());
        filesDao = new FilesDao_Impl(appDatabase);

    }

    private void initFragment() {
        //设置主题模式
        int mode = mmkv.decodeInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                setLightMode();
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                setNightMode();
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                setAutoMode();
                break;
            default:
                break;
        }

        //设置语言
        String language = mmkv.decodeString("language", "zh");
        switch (language) {
            case "zh":
                setLanguage("zh", "CN");
                break;
            case "en":
                setLanguage("en", "GB");
                break;
            default:
                break;
        }
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
                    shareApp();
                    return true;
                }
                return false;
            }
        });
    }

    private void listenThemeModeChanged() {
        RadioGroup rgThemeMode = dlThemeMode.findViewById(R.id.rg_theme_mode);
        rgThemeMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_auto_mode) {//自动模式
                    setAutoMode();
                } else if (checkedId == R.id.rb_light_mode) {//日间模式
                    setLightMode();
                } else if (checkedId == R.id.rb_dark_mode) {//夜间模式
                    setNightMode();
                }
                requireActivity().finish();
                startActivity(new Intent(requireActivity(), MainActivity.class));
            }
        });
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


    private void shareApp() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, appUrl);
        startActivity(Intent.createChooser(share, "分享应用"));
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
        dlRateUs = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dlRateUs.setContentView(R.layout.dialog_rate_us);
        Window window = dlRateUs.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dlRateUs.show();

        btnMaybeLater = dlRateUs.findViewById(R.id.btn_maybe_later);
        btnRateNow = dlRateUs.findViewById(R.id.btn_rate_now);
        btnMaybeLater.setOnClickListener(this);
        btnRateNow.setOnClickListener(this);
        dlyMain.close();
    }

    private void setAndShowChangeThemeModeDialog() {
        //                                                  设置dialog自带的背景框为透明
        dlThemeMode = new Dialog(requireActivity(), R.style.CustomDialogTheme);
        dlThemeMode.setContentView(R.layout.dialog_theme_mode);
        Window window = dlThemeMode.getWindow();
        //设置dialog的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.DialogAnimation);
        dlThemeMode.show();

        dlyMain.close();

        //设置默认选中的模式
        int mode = mmkv.decodeInt("mode", AppCompatDelegate.MODE_NIGHT_NO);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                RadioButton rbLightMode = dlThemeMode.findViewById(R.id.rb_light_mode);
                rbLightMode.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                RadioButton rbNightMode = dlThemeMode.findViewById(R.id.rb_dark_mode);
                rbNightMode.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                RadioButton rbAutoMode = dlThemeMode.findViewById(R.id.rb_auto_mode);
                rbAutoMode.setChecked(true);
                break;
            default:
                break;
        }

        listenThemeModeChanged();

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
        gridItemList.add(new GridItem(R.drawable.ic_home_all, getResources().getText(R.string.all).toString(), String.valueOf(allFileCount), allFileCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_pdf, getResources().getText(R.string.pdf).toString(), String.valueOf(allPDFCount), allPDFCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_doc, getResources().getText(R.string.document).toString(), String.valueOf(allDOCCount), allDOCCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_xls, getResources().getText(R.string.xls).toString(), String.valueOf(allXLSCount), allXLSCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_ppt, getResources().getText(R.string.ppt).toString(), String.valueOf(allPPTCount), allPPTCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_txt, getResources().getText(R.string.txt).toString(), String.valueOf(allTXTCount), allTXTCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        gridItemList.add(new GridItem(R.drawable.ic_home_other, getResources().getText(R.string.other).toString(), String.valueOf(allOTHERCount), allOTHERCount == 1 ? getResources().getText(R.string.file).toString() : getResources().getText(R.string.files).toString()));
        return gridItemList;
    }

    private void bindOpenButtonToToolBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), dlyMain, tbMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dlyMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 日间模式
    private void setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    // 夜间模式
    private void setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    // 自动模式
    private void setAutoMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        saveThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void saveThemeMode(int mode) {
        mmkv.encode("mode", mode);
    }

    private void setLanguage(String language, String country) {
        Locale locale = new Locale(language, country);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        updateSidebarText();
    }

    private void updateSidebarText() {
        // 找到侧边栏中的菜单项标题，并更新文本内容
        Menu menu = ngvDrawer.getMenu();
        MenuItem itemLanguage = menu.findItem(R.id.item_language);
        MenuItem itemThemeMode = menu.findItem(R.id.item_theme_mode);
        MenuItem itemRateUs = menu.findItem(R.id.item_evaluate_us);
        MenuItem itemShare = menu.findItem(R.id.item_analytical_applications);
        itemLanguage.setTitle(R.string.language);
        itemThemeMode.setTitle(R.string.theme_mode);
        itemRateUs.setTitle(R.string.evaluate_us);
        itemShare.setTitle(R.string.analytical_applications);

        View headerView = ngvDrawer.getHeaderView(0);
        TextView tvSecondNav = headerView.findViewById(R.id.tv_second_nav);
        tvSecondNav.setText(R.string.nav_header_text);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_maybe_later) {
            dlRateUs.dismiss();
        } else if (v.getId() == R.id.btn_rate_now) {
            Toast.makeText(requireActivity(), getResources().getText(R.string.thankForYourRate), Toast.LENGTH_SHORT).show();
            dlRateUs.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(scanFinishedBroadcast);
    }
}