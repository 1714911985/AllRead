package com.example.allreader.utils.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.allreader.fragment.AllSelectedFragment;
import com.example.allreader.fragment.DocSelectedFragment;
import com.example.allreader.fragment.OtherSelectedFragment;
import com.example.allreader.fragment.PdfSelectedFragment;
import com.example.allreader.fragment.PptSelectedFragment;
import com.example.allreader.fragment.TxtSelectedFragment;
import com.example.allreader.fragment.XlsSelectedFragment;

/**
 * Author: Eccentric
 * Created on 2024/6/7 18:03.
 * Description: com.example.allreader.utils.adapter.ClassificationDisplayAdapter
 */
public class ClassificationDisplayAdapter extends FragmentStateAdapter {
    public ClassificationDisplayAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AllSelectedFragment();
            case 1:
                return new PdfSelectedFragment();
            case 2:
                return new DocSelectedFragment();
            case 3:
                return new XlsSelectedFragment();
            case 4:
                return new PptSelectedFragment();
            case 5:
                return new TxtSelectedFragment();
            case 6:
                return new OtherSelectedFragment();
        }
        return new AllSelectedFragment();
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
