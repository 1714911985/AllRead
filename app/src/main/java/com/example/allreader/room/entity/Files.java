package com.example.allreader.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Author: Eccentric
 * Created on 2024/6/7 10:41.
 * Description: com.example.allreader.room.entity.File
 */
@Entity(tableName = "files")
public class Files {
    @PrimaryKey(autoGenerate = true)
    private int id;//id
    private String fileName;//文件名
    private String fileUri;//文件uri
    private int fileSize;//文件大小
    private String fileType;//文件类型
    private String createdTime;//创建时间
    private String changedTime;//最后修改事件
    private String latestTime;//最近点击时间
    private int isCollected;//是否收藏  0没有   1收藏

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(String changedTime) {
        this.changedTime = changedTime;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }


    public Files() {
    }
}
