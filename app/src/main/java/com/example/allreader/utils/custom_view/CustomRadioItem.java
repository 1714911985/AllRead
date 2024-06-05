package com.example.allreader.utils.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.allreader.R;

/**
 * Author: Eccentric
 * Created on 2024/6/4 17:40.
 * Description: com.example.allreader.utils.custom_view.CustomRadioItem
 */
public class CustomRadioItem extends RelativeLayout {

    private ImageView ivIcon;
    private TextView tvText;
    private RadioButton rbCheck;

    public CustomRadioItem(Context context) {
        super(context);
        init(context, null);
    }

    public CustomRadioItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomRadioItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.custom_radio_item, this, true);
        ivIcon = findViewById(R.id.iv_icon);
        tvText = findViewById(R.id.tv_text);
        rbCheck = findViewById(R.id.rb_check);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioItem);
            int iconResId = a.getResourceId(R.styleable.CustomRadioItem_iconSrc, 0);
            String text = a.getString(R.styleable.CustomRadioItem_text);

            if (iconResId != 0) {
                ivIcon.setImageResource(iconResId);
            }

            if (text != null) {
                tvText.setText(text);
            }

            a.recycle();
        }

        // 为整个 item 设置点击监听器
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rbCheck.isChecked()) {
                    rbCheck.setChecked(true);
                    if (getParent() instanceof RadioGroupView) {
                        ((RadioGroupView) getParent()).check(CustomRadioItem.this);
                    }
                }
            }
        });

        // 防止单选按钮的点击事件和整个 item 的点击事件冲突
        rbCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbCheck.isChecked()) {
                    if (getParent() instanceof RadioGroupView) {
                        ((RadioGroupView) getParent()).check(CustomRadioItem.this);
                    }
                }
            }
        });
    }

    public void setIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void setText(String text) {
        tvText.setText(text);
    }

    public void setChecked(boolean checked) {
        rbCheck.setChecked(checked);
    }

    public boolean isChecked() {
        return rbCheck.isChecked();
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        super.setOnClickListener(listener);
        rbCheck.setOnClickListener(listener);
    }
}
