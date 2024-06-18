package com.example.allreader.utils.util;


import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.allreader.R;
import com.example.allreader.room.entity.Files;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: Eccentric
 * Created on 2024/6/7 11:23.
 * Description: com.example.allreader.utils.util.FileUtils
 */
public class FileUtils {

    // 将File对象转换为File实体类
//    public static Files fileToFileEntity(File file) {
//        Files fileEntity = new Files();
//        fileEntity.setFileName(file.getName());
//        fileEntity.setFileUri(file.getAbsolutePath());
//        fileEntity.setFileSize((int) file.length());
//        fileEntity.setFileType(getFileType(file.getName()));
//        // 设置创建时间、最后修改时间等信息
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        fileEntity.setCreatedTime(getFileCreationTime(file));
//        fileEntity.setChangedTime(sdf.format(new Date(file.lastModified())));
//        fileEntity.setLatestTime(null);
//        fileEntity.setIsCollected(0); // 默认为未收藏
//        return fileEntity;
//    }

    // 根据文件名获取文件类型
    public static String getFileType(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String end = fileName.substring(index + 1).toLowerCase();
            switch (end) {
                case "ppt":
                case "pptx":
                    return "PPT";
                case "doc":
                case "docx":
                    return "DOC";
                case "xls":
                case "xlsx":
                    return "XLS";
                case "pdf":
                    return "PDF";
                case "txt":
                    return "TXT";
                default:
                    return "OTHER";
            }
        } else {
            return "Unknown";
        }
    }

    //是不是文档类型
    public static boolean isDocument(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".ppt") || fileName.endsWith(".pptx") ||
                fileName.endsWith(".doc") || fileName.endsWith(".docx") ||
                fileName.endsWith(".xls") || fileName.endsWith(".xlsx") ||
                fileName.endsWith(".pdf") ||
                fileName.endsWith(".txt") ||
                fileName.endsWith(".xml") ||
                fileName.endsWith(".json");
    }

    // 获取文件在手机里的的创建时间
    private static String getFileCreationTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Path filePath = file.toPath();
            try {
                BasicFileAttributes attributes = java.nio.file.Files.readAttributes(filePath, BasicFileAttributes.class);
                // 获取文件的创建时间
                long millis = attributes.creationTime().toMillis();
                Date creationDate = new Date(millis);
                // 格式化时间
                return sdf.format(creationDate);
            } catch (Exception e) {
                e.printStackTrace();
                return "Unknown";
            }
        }
        return sdf.format(new Date(file.lastModified()));
    }

    public static String getNameWithPath(String pathStr) {
        if (pathStr == null || pathStr.isEmpty()) {
            return "";
        }

        // 使用 File 类解析路径
        File file = new File(pathStr);
        return file.getName();
    }

    public static CharSequence getFileTimeAndSize(long createdTime, long fileSize) {
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
            return time + " | " + fileSize + ".0 B";
        }
        int exp = (int) (Math.log(fileSize) / Math.log(1024));
        char unit = "KMGTPE".charAt(exp - 1);
        @SuppressLint("DefaultLocale") String size = String.format("%.1f %sB", fileSize / Math.pow(1024, exp), unit);
        return time + " | " + size;
    }

    public static int getIconResourceForFileType(String fileType) {
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

    public static int getBackgroundColorForFileType(String fileType) {
        switch (fileType) {
            case "PPT":
                return R.color.pptBackgroundColor;
            case "DOC":
                return R.color.docBackgroundColor;
            case "XLS":
                return R.color.xlsBackgroundColor;
            case "PDF":
                return R.color.pdfBackgroundColor;
            case "TXT":
                return R.color.txtBackgroundColor;
            case "OTHER":
                return R.color.otherBackgroundColor;
        }
        return 0;
    }
}