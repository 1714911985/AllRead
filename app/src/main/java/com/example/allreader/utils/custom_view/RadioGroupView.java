package com.example.allreader.utils.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Author: Eccentric
 * Created on 2024/6/4 17:47.
 * Description: com.example.allreader.utils.custom_view.RadioGroupView
 */
public class RadioGroupView extends LinearLayout {

    private CustomRadioItem selectedRadioItem;

    public RadioGroupView(Context context) {
        super(context);
    }

    public RadioGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void check(CustomRadioItem radioItem) {
        if (selectedRadioItem != null) {
            selectedRadioItem.setChecked(false);
        }
        selectedRadioItem = radioItem;
        selectedRadioItem.setChecked(true);
    }
}