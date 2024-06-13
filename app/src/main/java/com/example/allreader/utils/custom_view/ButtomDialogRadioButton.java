package com.example.allreader.utils.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.allreader.R;

/**
 * Author: Eccentric
 * Created on 2024/6/13 14:23.
 * Description: com.example.allreader.utils.custom_view.ButtomDialogRadioButton
 */
public class ButtomDialogRadioButton extends LinearLayout {
    private ImageView ivBottomDialogButton;
    private TextView tvBottomDialogButton;
    private boolean isChecked = false;

    public ButtomDialogRadioButton(Context context) {
        super(context);
        init(context, null);
    }

    public ButtomDialogRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ButtomDialogRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int imgResId;
    private int imgSelectedResId;
    private int textColorId;
    private int backgroundId;
    private int textSelectedColorId;
    private int backgroundSelectedResId;
    private String text;

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.button_radio_buttom_dialog, this, true);
        ivBottomDialogButton = findViewById(R.id.iv_bottom_dialog_button);
        tvBottomDialogButton = findViewById(R.id.tv_bottom_dialog_button);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ButtomDialogRadioButton);
            imgResId = a.getResourceId(R.styleable.ButtomDialogRadioButton_imgSrc, 0);
            backgroundId = a.getResourceId(R.styleable.ButtomDialogRadioButton_background, 0);
            imgSelectedResId = a.getResourceId(R.styleable.ButtomDialogRadioButton_imgSrcSelected, 0);
            backgroundSelectedResId = a.getResourceId(R.styleable.ButtomDialogRadioButton_backgroundSelected, 0);
            textColorId = a.getColor(R.styleable.ButtomDialogRadioButton_btnTextColor, 0);
            textSelectedColorId = a.getColor(R.styleable.ButtomDialogRadioButton_btnTextColorSelected, 0);
            text = a.getString(R.styleable.ButtomDialogRadioButton_btnText);
            drawButton(isChecked);

            a.recycle();

            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(true);
                    if (getParent() instanceof ButtomDialogRadioGroup) {
                        ((ButtomDialogRadioGroup) getParent()).check(ButtomDialogRadioButton.this);
                    }
                }
            });
        }
    }

    private void drawButton(boolean isChecked) {
        ivBottomDialogButton.setImageResource(isChecked ? imgSelectedResId : imgResId);
        tvBottomDialogButton.setTextColor(isChecked ? textSelectedColorId : textColorId);
        tvBottomDialogButton.setText(text);
        ButtomDialogRadioButton.this.setBackgroundResource(isChecked ? backgroundSelectedResId : backgroundId);
    }


    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        drawButton(isChecked);
    }
}
