package com.example.allreader.utils.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Author: Eccentric
 * Created on 2024/6/13 14:34.
 * Description: com.example.allreader.utils.custom_view.ButtomDialogRadioGroup
 */
public class ButtomDialogRadioGroup extends LinearLayout {

    private ButtomDialogRadioButton selectedRadioItem;

    public ButtomDialogRadioGroup(Context context) {
        super(context);
    }

    public ButtomDialogRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtomDialogRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void check(ButtomDialogRadioButton radioItem) {
        if (selectedRadioItem != null) {
            selectedRadioItem.setChecked(false);
        }
        selectedRadioItem = radioItem;
        selectedRadioItem.setChecked(true);
    }

    public ButtomDialogRadioButton getChecked() {
        if (selectedRadioItem != null)
            return selectedRadioItem;
        return null;
    }
}