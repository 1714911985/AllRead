package com.example.allreader.utils.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.entity.Files;
import com.example.allreader.utils.Manager.ThreadPoolManager;
import com.example.allreader.utils.util.FileUtils;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/11 13:32.
 * Description: com.example.allreader.utils.adapter.RecycleGridAdapter
 */
public class RecycleGridAdapter extends RecyclerView.Adapter<RecycleGridAdapter.ViewHolder> {
    private Context context;
    private List<Files> filesList;
    private FilesDao filesDao;

    public RecycleGridAdapter(Context context, List<Files> filesList, FilesDao filesDao) {
        this.context = context;
        this.filesList = filesList;
        this.filesDao = filesDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_grid_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Files currentFile = filesList.get(position); // 获取当前项的文件对象

        // 根据文件类型拿到对应的背景颜色
        int colorId = FileUtils.getBackgroundColorForFileType(currentFile.getFileType());
        int resId = FileUtils.getIconResourceForFileType(currentFile.getFileType());

        holder.ivItemFile.setImageResource(resId);
        holder.tvItemFileName.setText(currentFile.getFileName());
        holder.tvItemFileName.setSelected(true);
        holder.tvItemFileTimeAndSize.setText(FileUtils.getFileTimeAndSize(currentFile.getCreatedTime() * 1000, currentFile.getFileSize()));
        holder.ivIsFavorite.setImageResource(currentFile.getIsCollected() == 0 ? R.drawable.ic_star : R.drawable.ic_star_on);
        holder.viewBackground.setBackground(new GradientDrawable() {{
            setColor(ContextCompat.getColor(context, colorId));
            setCornerRadius(5f);
        }});

        holder.ivIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus(currentFile, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    //切换收藏状态
    private void toggleFavoriteStatus(Files file, ViewHolder holder) {
        ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                filesDao.updateIsFavorite(file.getId(), 1 - file.getIsCollected());

                // 在主线程中更新UI
                ((Activity) holder.itemView.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        file.setIsCollected(1 - file.getIsCollected()); // 更新文件对象的收藏状态
                        holder.ivIsFavorite.setImageResource(file.getIsCollected() == 0 ? R.drawable.ic_star : R.drawable.ic_star_on);
                    }
                });
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Files> newFilesList) {
        this.filesList.clear();
        this.filesList.addAll(newFilesList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemFile, ivIsFavorite;
        TextView tvItemFileName, tvItemFileTimeAndSize;
        View viewBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemFile = itemView.findViewById(R.id.iv_item_file);
            ivIsFavorite = itemView.findViewById(R.id.iv_is_favorite);
            tvItemFileName = itemView.findViewById(R.id.tv_item_file_name);
            tvItemFileTimeAndSize = itemView.findViewById(R.id.tv_item_file_time_and_size);
            viewBackground = itemView.findViewById(R.id.v_background);
        }
    }
}
