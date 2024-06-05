package com.example.allreader.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public GridAdapter(Context mContext, int mResource, List<GridItem> gridItemList) {
        this.mContext = mContext;
        this.mResource = mResource;
        this.gridItemList = gridItemList;
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
        ViewHolder holder;
        if (Objects.isNull(convertView)) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.ivFile = convertView.findViewById(R.id.iv_file);
            holder.tvFileName = convertView.findViewById(R.id.tv_file_name);
            holder.tvFileNumber = convertView.findViewById(R.id.tv_file_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridItem item = (GridItem) getItem(position);
        holder.ivFile.setImageResource(item.getImageResource());
        holder.tvFileName.setText(item.getText1());
        holder.tvFileNumber.setText(item.getText2());

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivFile;
        TextView tvFileName;
        TextView tvFileNumber;
    }
}
