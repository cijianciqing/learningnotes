package nanshan.cj.learningnotes.ckeditor.dao;

import nanshan.cj.learningnotes.ckeditor.entity.CJUploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CJUploadedFileDao extends JpaRepository<CJUploadedFile,Long> {



}
