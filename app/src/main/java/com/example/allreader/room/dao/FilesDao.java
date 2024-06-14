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

    @Query("UPDATE files SET isCollected = :isCollected WHERE id = :id")
    void updateIsFavorite(int id, int isCollected);

    @Query("UPDATE files SET fileName = :newFileName WHERE id = :id")
    void updateFileName(int id, String newFileName);

    @Query("UPDATE files SET isCollected = :isCollected WHERE id = :id")
    void updateIsCollected(int id, int isCollected);

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY createdTime DESC")
    List<Files> getPDFFilesSortedByCreatedTimeDescending();

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

    @Query("SELECT * FROM files WHERE id = :id")
    Files getFilesById(int id);

    @Query("SELECT * FROM files WHERE fileName LIKE '%'|| :fuzzyFileName|| '%'")
    List<Files> fuzzyGetFiles(String fuzzyFileName);

    @Query("SELECT * FROM files")
    List<Files> getAllFiles();

//    -----------------getALLFilesSortBy---------------------
    @Query("SELECT * FROM files ORDER BY fileName ASC")
    List<Files> getALLFilesSortByFileNameAscending();

    @Query("SELECT * FROM files ORDER BY fileName DESC")
    List<Files> getALLFilesSortByFileNameDescending();

    @Query("SELECT * FROM files ORDER BY createdTime ASC")
    List<Files> getALLFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files ORDER BY createdTime DESC")
    List<Files> getALLFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files ORDER BY fileType ASC")
    List<Files> getALLFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files ORDER BY fileType DESC")
    List<Files> getALLFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files ORDER BY fileSize ASC")
    List<Files> getALLFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files ORDER BY fileSize DESC")
    List<Files> getALLFilesSortByFileSizeDescending();
    //    -----------------getPPTFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileName ASC")
    List<Files> getPPTFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileName DESC")
    List<Files> getPPTFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY createdTime ASC")
    List<Files> getPPTFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY createdTime DESC")
    List<Files> getPPTFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileType ASC")
    List<Files> getPPTFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileType DESC")
    List<Files> getPPTFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileSize ASC")
    List<Files> getPPTFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PPT' ORDER BY fileSize DESC")
    List<Files> getPPTFilesSortByFileSizeDescending();

    //    -----------------getDOCFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileName ASC")
    List<Files> getDOCFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileName DESC")
    List<Files> getDOCFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY createdTime ASC")
    List<Files> getDOCFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY createdTime DESC")
    List<Files> getDOCFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileType ASC")
    List<Files> getDOCFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileType DESC")
    List<Files> getDOCFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileSize ASC")
    List<Files> getDOCFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'DOC' ORDER BY fileSize DESC")
    List<Files> getDOCFilesSortByFileSizeDescending();

    //    -----------------getXLSFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileName ASC")
    List<Files> getXLSFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileName DESC")
    List<Files> getXLSFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY createdTime ASC")
    List<Files> getXLSFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY createdTime DESC")
    List<Files> getXLSFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileType ASC")
    List<Files> getXLSFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileType DESC")
    List<Files> getXLSFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileSize ASC")
    List<Files> getXLSFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'XLS' ORDER BY fileSize DESC")
    List<Files> getXLSFilesSortByFileSizeDescending();

    //    -----------------getPDFFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileName ASC")
    List<Files> getPDFFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileName DESC")
    List<Files> getPDFFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY createdTime ASC")
    List<Files> getPDFFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY createdTime DESC")
    List<Files> getPDFFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileType ASC")
    List<Files> getPDFFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileType DESC")
    List<Files> getPDFFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileSize ASC")
    List<Files> getPDFFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF' ORDER BY fileSize DESC")
    List<Files> getPDFFilesSortByFileSizeDescending();

    //    -----------------getTXTFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileName ASC")
    List<Files> getTXTFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileName DESC")
    List<Files> getTXTFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY createdTime ASC")
    List<Files> getTXTFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY createdTime DESC")
    List<Files> getTXTFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileType ASC")
    List<Files> getTXTFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileType DESC")
    List<Files> getTXTFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileSize ASC")
    List<Files> getTXTFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'TXT' ORDER BY fileSize DESC")
    List<Files> getTXTFilesSortByFileSizeDescending();

    //    -----------------getOTHERFilesSortBy---------------------
    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileName ASC")
    List<Files> getOTHERFilesSortByFileNameAscending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileName DESC")
    List<Files> getOTHERFilesSortByFileNameDescending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY createdTime ASC")
    List<Files> getOTHERFilesSortByCreatedTimeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY createdTime DESC")
    List<Files> getOTHERFilesSortByCreatedTimeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileType ASC")
    List<Files> getOTHERFilesSortByFileTypeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileType DESC")
    List<Files> getOTHERFilesSortByFileTypeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileSize ASC")
    List<Files> getOTHERFilesSortByFileSizeAscending();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER' ORDER BY fileSize DESC")
    List<Files> getOTHERFilesSortByFileSizeDescending();

    @Query("SELECT * FROM files WHERE fileType = 'PDF'")
    List<Files> getPDFFiles();

    @Query("SELECT * FROM files WHERE fileType = 'PPT'")
    List<Files> getPPTFiles();

    @Query("SELECT * FROM files WHERE fileType = 'DOC'")
    List<Files> getDOCFiles();

    @Query("SELECT * FROM files WHERE fileType = 'XLS'")
    List<Files> getXLSFiles();

    @Query("SELECT * FROM files WHERE fileType = 'TXT'")
    List<Files> getTXTFiles();

    @Query("SELECT * FROM files WHERE fileType = 'OTHER'")
    List<Files> getOtherFiles();

}
