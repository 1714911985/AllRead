package com.example.allreader.utils.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.entity.Files;
import com.example.allreader.utils.Manager.ThreadPoolManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: Eccentric
 * Created on 2024/6/11 13:32.
 * Description: com.example.allreader.utils.adapter.RecycleListAdapter
 */
public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {
    private List<Files> filesList;
    private List<Files> filteredList;
    private FilesDao filesDao;

    public RecycleListAdapter(List<Files> filesList, FilesDao filesDao) {
        this.filesList = filesList;
        this.filesDao = filesDao;
        filteredList = new ArrayList<>(filesList);
    }

    public void updateData(List<Files> newFilesList) {
        this.filesList.clear();
        this.filesList.addAll(newFilesList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Files currentFile = filesList.get(position); // 获取当前项的文件对象
        Log.e("TAG", "绑定文件: " + currentFile.getFileName());

        int resId = getIconResourceForFileType(currentFile.getFileType());

        holder.ivItemFile.setImageResource(resId);
        holder.tvItemFileName.setText(currentFile.getFileName());
        holder.tvItemFileTimeAndSize.setText(getFileTimeAndSize(currentFile.getCreatedTime() * 1000, currentFile.getFileSize()));
        holder.ivIsFavorite.setImageResource(currentFile.getIsCollected() == 0 ? R.drawable.ic_star : R.drawable.ic_star_on);

        holder.ivIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavoriteStatus(currentFile, holder);
            }
        });
    }

    private int getIconResourceForFileType(String fileType) {
        switch (fileType) {
            case "PPT":
                return R.drawable.ic_item_file_ppt;
            case "DOC":
                return R.drawable.ic_item_file_doc;
            case "XLS":
                return R.drawable.ic_item_file_xls;
            case "PDF":
                return R.drawable.ic_item_file_pdf;
            case "TXT":
                return R.drawable.ic_item_file_txt;
            case "OTHER":
                return R.drawable.ic_item_file_other;
            default:
                return R.drawable.ic_item_file_other;
        }
    }

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
                        Log.e("TAG", "点击后是否被收藏: " + (file.getIsCollected() == 1 ? "是" : "否"));
                    }
                });
            }
        });
    }


    private CharSequence getFileTimeAndSize(long createdTime, long fileSize) {
        // 创建 SimpleDateFormat 对象
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // 获取 Date 对象
        Date date = new Date(createdTime);

        // 格式化日期
        String formattedDate = sdfDate.format(date);

        // 获取小时和分钟
        String formattedTime = sdfTime.format(date);
        String[] timeParts = formattedTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        String minutes = timeParts[1];

        // 处理 AM/PM
        String period;
        if (hour == 0) {
            hour = 12;
            period = "AM";
        } else if (hour == 12) {
            period = "PM";
        } else if (hour > 12) {
            hour -= 12;
            period = "PM";
        } else {
            period = "AM";
        }

        // 格式化小时
        String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
        String time = formattedDate + " " + formattedHour + ":" + minutes + " " + period;

        if (fileSize < 1024) {
            return time + " | " + fileSize + " B";
        }
        int exp = (int) (Math.log(fileSize) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        @SuppressLint("DefaultLocale") String size = String.format("%.1f %sB", fileSize / Math.pow(1024, exp), unit);
        return time + " | " + size;
    }


    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemFile, ivIsFavorite;
        TextView tvItemFileName, tvItemFileTimeAndSize;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemFile = itemView.findViewById(R.id.iv_item_file);
            ivIsFavorite = itemView.findViewById(R.id.iv_is_favorite);
            tvItemFileName = itemView.findViewById(R.id.tv_item_file_name);
            tvItemFileTimeAndSize = itemView.findViewById(R.id.tv_item_file_time_and_size);
        }

    }

}
