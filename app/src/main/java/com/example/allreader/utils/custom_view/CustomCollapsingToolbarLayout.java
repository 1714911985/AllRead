package com.example.allreader.utils.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * Author: Eccentric
 * Created on 2024/6/3 18:39.
 * Description: com.example.allreader.utils.custom_view.CustomCollapsingToolbarLayout
 */
public class CustomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    public CustomCollapsingToolbarLayout(Context context) {
        super(context);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 获取子 View 的数量
        int childCount = getChildCount();
        // 获取当前布局的宽度
        int parentWidth = getWidth();

        // 设置子 View 的垂直排列
        int y = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();
            // 将子 View 垂直居中放置
            int childTop = (parentWidth - childWidth) / 2;
            child.layout(childTop, y, childTop + childWidth, y + childHeight);
            // 更新下一个子 View 的位置
            y += childHeight;
        }
    }
}