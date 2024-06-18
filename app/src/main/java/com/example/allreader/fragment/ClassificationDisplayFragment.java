package com.example.allreader.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.allreader.R;
import com.example.allreader.utils.adapter.ClassificationDisplayAdapter;
import com.example.allreader.utils.custom_view.ButtomDialogRadioGroup;
import com.example.allreader.utils.entity.EventMessage;
import com.example.allreader.utils.util.EventBusUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tencent.mmkv.MMKV;

public class ClassificationDisplayFragment extends Fragment {
    private NavController navController;
    private Toolbar tbClassificationDisplay;
    private TabLayout tlyClassificationDisplay;
    private ViewPager2 vp2ClassificationDisplay;
    private int position;
    private MMKV mmkv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classification_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MMKV.initialize(requireActivity());
        mmkv = MMKV.defaultMMKV();
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        navController = Navigation.findNavController(view);
        tbClassificationDisplay = view.findViewById(R.id.tb_classification_display);
        tlyClassificationDisplay = view.findViewById(R.id.tly_classification_display);
        vp2ClassificationDisplay = view.findViewById(R.id.vp2_classification_display);


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
                    showBottomDialog(requireActivity());
                    return true;
                }
                return false;
            }
        });
    }

    private void showBottomDialog(Context context) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = getLayoutInflater().inflate(R.layout.dialog_bottom_arrangement, null);
        bottomSheetDialog.setContentView(view);
        int viewMethodId = mmkv.decodeInt("viewMethodId", R.id.bdrb_list);
        int sortMethodId = mmkv.decodeInt("sortMethodId", R.id.bdrb_date);
        int orderMethodId = mmkv.decodeInt("orderMethodId", R.id.bdrb_desc);
        ButtomDialogRadioGroup bdrgViewMethod = view.findViewById(R.id.bdrg_view_method);
        ButtomDialogRadioGroup bdrgSortMethod = view.findViewById(R.id.bdrg_sort_method);
        ButtomDialogRadioGroup bdrgOrderMethod = view.findViewById(R.id.bdrg_order_method);
        bdrgViewMethod.check(view.findViewById(viewMethodId));
        bdrgSortMethod.check(view.findViewById(sortMethodId));
        bdrgOrderMethod.check(view.findViewById(orderMethodId));
        Button btnBottomDialogApply = view.findViewById(R.id.btn_bottom_dialog_apply);

        ImageView ivBottomDialogCancel = view.findViewById(R.id.iv_bottom_dialog_cancel);
        ivBottomDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btnBottomDialogApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewMethodId = bdrgViewMethod.getChecked().getId();
                int sortMethodId = bdrgSortMethod.getChecked().getId();
                int orderMethodId = bdrgOrderMethod.getChecked().getId();
                mmkv.encode("viewMethodId",viewMethodId);
                mmkv.encode("sortMethodId",sortMethodId);
                mmkv.encode("orderMethodId",orderMethodId);
                //刷新
                EventBusUtils.post(new EventMessage(viewMethodId,sortMethodId,orderMethodId));
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
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
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tlyClassificationDisplay, vp2ClassificationDisplay, new TabLayoutMediator.TabConfigurationStrategy() {
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
        });
        tabLayoutMediator.attach();
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
        vp2ClassificationDisplay.post(new Runnable() {
            @Override
            public void run() {
//                vp2ClassificationDisplay.setCurrentItem(position);
                TabLayout.Tab tab = tlyClassificationDisplay.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}