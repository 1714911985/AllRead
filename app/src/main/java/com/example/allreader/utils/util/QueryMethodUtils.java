package com.example.allreader.utils.util;

import com.example.allreader.R;
import com.example.allreader.room.dao.FilesDao;
import com.example.allreader.room.entity.Files;

import java.util.List;

/**
 * Author: Eccentric
 * Created on 2024/6/14 14:40.
 * Description: com.example.allreader.utils.util.QueryMethodUtils
 */
public class QueryMethodUtils {
    public static List<Files> chooseQueryMethod(FilesDao filesDao, String filesType, int sortMethodId, int orderMethodId) {
        List<Files> filesList = null;
        switch (filesType) {
            case "ALL":
                filesList = chooseALLQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "PPT":
                filesList = choosePPTQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "DOC":
                filesList = chooseDOCQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "XLS":
                filesList = chooseXLSQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "PDF":
                filesList = choosePDFQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "TXT":
                filesList = chooseTXTQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
            case "OTHER":
                filesList = chooseOTHERQueryMethod(filesDao, sortMethodId, orderMethodId);
                break;
        }
        return filesList;
    }

    private static List<Files> chooseALLQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getALLFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getALLFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getALLFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getALLFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getALLFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getALLFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getALLFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getALLFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> choosePPTQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPPTFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPPTFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPPTFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPPTFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPPTFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPPTFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPPTFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPPTFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> chooseDOCQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getDOCFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getDOCFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getDOCFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getDOCFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getDOCFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getDOCFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getDOCFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getDOCFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> chooseXLSQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getXLSFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getXLSFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getXLSFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getXLSFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getXLSFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getXLSFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getXLSFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getXLSFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> choosePDFQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPDFFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPDFFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPDFFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPDFFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getPDFFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getPDFFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getPDFFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getPDFFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> chooseTXTQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getTXTFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getTXTFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getTXTFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getTXTFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getTXTFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getTXTFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getTXTFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getTXTFilesSortByFileSizeDescending();
            }
        }
        return null;
    }

    private static List<Files> chooseOTHERQueryMethod(FilesDao filesDao, int sortMethodId, int orderMethodId) {
        if (orderMethodId == R.id.bdrb_asc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getOTHERFilesSortByFileNameAscending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getOTHERFilesSortByCreatedTimeAscending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getOTHERFilesSortByFileTypeAscending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getOTHERFilesSortByFileSizeAscending();
            }
        } else if (orderMethodId == R.id.bdrb_desc) {
            if (sortMethodId == R.id.bdrb_name) {
                return filesDao.getOTHERFilesSortByFileNameDescending();
            } else if (sortMethodId == R.id.bdrb_date) {
                return filesDao.getOTHERFilesSortByCreatedTimeDescending();
            } else if (sortMethodId == R.id.bdrb_type) {
                return filesDao.getOTHERFilesSortByFileTypeDescending();
            } else if (sortMethodId == R.id.bdrb_size) {
                return filesDao.getOTHERFilesSortByFileSizeDescending();
            }
        }
        return null;
    }


}
