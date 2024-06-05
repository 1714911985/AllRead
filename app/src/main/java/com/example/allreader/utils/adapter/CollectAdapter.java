package com.example.allreader.utils.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.allreader.fragment.FavoriteFragment;
import com.example.allreader.fragment.RecentFragment;

/**
 * Author: Eccentric
 * Created on 2024/6/3 16:31.
 * Description: com.example.allreader.utils.adapter.ViewPager2Adapter
 */
public class CollectAdapter extends FragmentStateAdapter {
    public CollectAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new FavoriteFragment();
        }
        return new RecentFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
