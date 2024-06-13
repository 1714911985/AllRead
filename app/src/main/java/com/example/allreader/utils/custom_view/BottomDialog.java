package com.example.allreader.utils.custom_view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.allreader.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * Author: Eccentric
 * Created on 2024/6/13 18:03.
 * Description: com.example.allreader.utils.custom_view.BottomDialog
 */
public class BottomDialog extends BottomSheetDialog {
    private ButtomDialogRadioGroup bdrgViewMethod, bdrgSortMethod, bdrgOrderMethod;

    public BottomDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected BottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_bottom_arrangement);
        bdrgViewMethod = findViewById(R.id.bdrg_view_method);
        bdrgSortMethod = findViewById(R.id.bdrg_sort_method);
        bdrgOrderMethod = findViewById(R.id.bdrg_order_method);


    }


}
