package com.example.allreader.utils.util;


import android.os.Build;

import androidx.annotation.RequiresApi;

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

    // 获取文件的创建时间
    private static String getFileCreationTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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
}