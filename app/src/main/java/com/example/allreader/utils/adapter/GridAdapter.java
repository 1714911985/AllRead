package com.example.allreader.utils.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.allreader.utils.entity.GridItem;
import com.example.allreader.R;

import java.util.List;
import java.util.Objects;

/**
 * Author: Eccentric
 * Created on 2024/6/3 14:39.
 * Description: com.example.allreader.utils.adapter.GridAdapter
 */
public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private List<GridItem> gridItemList;
    private View view;

    public GridAdapter(Context mContext, int mResource, List<GridItem> gridItemList,View view) {
        this.mContext = mContext;
        this.mResource = mResource;
        this.gridItemList = gridItemList;
        this.view = view;
    }

    @Override
    public int getCount() {
        return gridItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NavController navController = Navigation.findNavController(view);

        ViewHolder holder;
        if (Objects.isNull(convertView)) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.ivFile = convertView.findViewById(R.id.iv_file);
            holder.tvFileName = convertView.findViewById(R.id.tv_file_name);
            holder.tvFileNumber = convertView.findViewById(R.id.tv_file_number);
            holder.tvFileText = convertView.findViewById(R.id.tv_file_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridItem item = (GridItem) getItem(position);
        holder.ivFile.setImageResource(item.getImageResource());
        holder.tvFileName.setText(item.getText1());
        holder.tvFileNumber.setText(item.getText2());
        holder.tvFileText.setText(item.getText3());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                navController.navigate(R.id.fg_classification_display, bundle,getNavOptions());
            }
        });

        return convertView;
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

    private static class ViewHolder {
        ImageView ivFile;
        TextView tvFileName;
        TextView tvFileNumber;
        TextView tvFileText;
    }
}
