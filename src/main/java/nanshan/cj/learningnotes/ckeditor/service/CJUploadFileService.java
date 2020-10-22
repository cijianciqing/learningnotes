package nanshan.cj.learningnotes.ckeditor.service;

import nanshan.cj.learningnotes.ckeditor.dao.CJUploadedFileDao;
import nanshan.cj.learningnotes.ckeditor.entity.CJUploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CJUploadFileService {
    @Autowired
    CJUploadedFileDao  cjUploadedFileDao;

    public Long saveFile(CJUploadedFile cjUploadedFile) {
        CJUploadedFile save = cjUploadedFileDao.save(cjUploadedFile);
        return  save.getId();
    }

    public CJUploadedFile getFile(Long fileId) {
        CJUploadedFile cjUploadedFile = cjUploadedFileDao.findById(fileId).get();
        return cjUploadedFile;
    }
}
