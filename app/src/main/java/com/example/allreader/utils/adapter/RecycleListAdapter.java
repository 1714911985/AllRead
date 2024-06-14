package com.example.allreader.utils.adapter;

import android.annotation.SuppressLint;
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
    private static int isFavorite;
    private List<Files> filesList;
    private List<Files> filteredList;
    private FilesDao filesDao;
    private Files thisFiles;

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
        thisFiles = filesList.get(position);
        int resId = 0;
        isFavorite = thisFiles.getIsCollected();
        switch (thisFiles.getFileType()) {
            case "PPT":
                resId = R.drawable.ic_item_file_ppt;
                break;
            case "DOC":
                resId = R.drawable.ic_item_file_doc;
                break;
            case "XLS":
                resId = R.drawable.ic_item_file_xls;
                break;
            case "PDF":
                resId = R.drawable.ic_item_file_pdf;
                break;
            case "TXT":
                resId = R.drawable.ic_item_file_txt;
                break;
            case "OTHER":
                resId = R.drawable.ic_item_file_other;
                break;
        }

        holder.ivItemFile.setImageResource(resId);
        holder.tvItemFileName.setText(thisFiles.getFileName());
        holder.tvItemFileTimeAndSize.setText(getFileTimeAndSize(thisFiles.getCreatedTime() * 1000, thisFiles.getFileSize()));
        holder.ivIsFavorite.setImageResource(isFavorite == 0 ? R.drawable.ic_star : R.drawable.ic_star_on);
        holder.ivIsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolManager.getSingleExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        holder.ivIsFavorite.setImageResource(1 - thisFiles.getIsCollected() == 0 ? R.drawable.ic_star : R.drawable.ic_star_on);
                        filesDao.updateIsFavorite(thisFiles.getId(), 1 - thisFiles.getIsCollected());
                        thisFiles = filesDao.getFilesById(thisFiles.getId());
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
