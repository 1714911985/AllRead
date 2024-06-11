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
    private long fileSize;//文件大小
    private String fileType;//文件类型
    private long createdTime;//创建时间
    private long changedTime;//最后修改事件
    private long latestTime;//最近点击时间
    private int isCollected;//是否收藏  0没有   1收藏

    public Files() {
    }

    @Ignore
    public Files(String fileName, String fileUri, long fileSize, String fileType, long createdTime, long changedTime, long latestTime, int isCollected) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.createdTime = createdTime;
        this.changedTime = changedTime;
        this.latestTime = latestTime;
        this.isCollected = isCollected;
    }

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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(long changedTime) {
        this.changedTime = changedTime;
    }

    public long getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(long latestTime) {
        this.latestTime = latestTime;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    @Override
    @Ignore
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileUri='" + fileUri + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", createdTime=" + createdTime +
                ", changedTime=" + changedTime +
                ", latestTime=" + latestTime +
                ", isCollected=" + isCollected +
                '}';
    }
}
