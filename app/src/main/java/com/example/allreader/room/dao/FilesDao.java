package com.example.allreader.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.allreader.room.entity.Files;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/7 11:02.
 * Description: com.example.allreader.room.dao.FileDao
 */
@Dao
public interface FilesDao {
    @Insert
    void insert(Files file);

    @Query("DELETE FROM files WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM files")
    void deleteAll();

    @Query("UPDATE files SET fileName = :newFileName WHERE id = :id")
    void updateFileName(int id, String newFileName);

    @Query("UPDATE files SET isCollected = :isCollected WHERE id = :id")
    void updateIsCollected(int id, int isCollected);

    @Query("SELECT * FROM files ORDER BY fileName ASC")
    List<Files> getAllFilesSortedByNameAscending();

    @Query("SELECT * FROM files ORDER BY fileName DESC")
    List<Files> getAllFilesSortedByNameDescending();

    @Query("SELECT * FROM files ORDER BY createdTime ASC")
    List<Files> getAllFilesSortedByCreatedTimeAscending();

    @Query("SELECT * FROM files ORDER BY createdTime DESC")
    List<Files> getAllFilesSortedByCreatedTimeDescending();

    @Query("SELECT * FROM files ORDER BY fileType ASC")
    List<Files> getAllFilesSortedByFileTypeAscending();

    @Query("SELECT * FROM files ORDER BY fileType DESC")
    List<Files> getAllFilesSortedByFileTypeDescending();

    @Query("SELECT * FROM files ORDER BY fileSize ASC")
    List<Files> getAllFilesSortedByFileSizeAscending();

    @Query("SELECT * FROM files ORDER BY fileSize DESC")
    List<Files> getAllFilesSortedByFileSizeDescending();

    @Query("SELECT COUNT(*) FROM files")
    int getAllFileCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'PDF'")
    int getAllPDFCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'PPT'")
    int getAllPPTCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'DOC'")
    int getAllDOCCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'XLS'")
    int getAllXLSCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'TXT'")
    int getAllTXTCount();

    @Query("SELECT COUNT(*) FROM files WHERE fileType = 'OTHER'")
    int getAllOTHERCount();


}
