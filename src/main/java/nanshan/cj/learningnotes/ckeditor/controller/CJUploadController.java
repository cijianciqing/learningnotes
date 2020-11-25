package nanshan.cj.learningnotes.ckeditor.controller;

import cn.hutool.core.date.DateTime;
import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.ckeditor.entity.CJCKEditorUploadResponse;
import nanshan.cj.learningnotes.ckeditor.entity.CJCKEditorUploadResponseUtil;
import nanshan.cj.learningnotes.ckeditor.entity.CJUploadedFile;
import nanshan.cj.learningnotes.ckeditor.service.CJUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/cjCKEditor/uploader")
public class CJUploadController {

    @Autowired
    CJUploadFileService cjUploadFileService;

    public static final String  CJCKEDITOR_FILE_PATH = "E:\\java\\upload\\file\\";
    public static final String  CJCKEDITOR_IMAGE_PATH = "E:\\java\\upload\\image\\";

    public CJCKEditorUploadResponse handleFileUpload(HttpServletRequest request,String path) {
        System.out.println("nanshan.cj.learningnotes.ckeditor.controller.CJUploadController.handleFileUpload ....");
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("upload");

        String cjFileName = "";
        String cjFilePath = "";
        Long cjFileId = 0L;
        String filePath = path;
        if (!file.isEmpty()) {
            try {
                long time = DateTime.now().getTime();

                cjFileName = String.format("%s_%s", time, file.getOriginalFilename());//加上时间戳，避免重复
                cjFilePath = filePath + cjFileName;

                System.out.println(cjFileName);
                System.out.println(cjFilePath);

                file.transferTo(new File(cjFilePath));

                CJUploadedFile cjUploadedFile = new CJUploadedFile();
                cjUploadedFile.setFileName(cjFileName);
                cjUploadedFile.setFilePath(cjFilePath);
                cjFileId = cjUploadFileService.saveFile(cjUploadedFile);

            } catch (Exception e) {
                e.printStackTrace();
                return CJCKEditorUploadResponseUtil.error();
            }
        }

        return CJCKEditorUploadResponseUtil.success(cjFileName,path, cjFileId);
    }

    @PostMapping("/file")
    public CJCKEditorUploadResponse handleFileUpload01(HttpServletRequest request) {
      return   handleFileUpload(request,CJCKEDITOR_FILE_PATH);
    }
    @PostMapping("/image")
    public CJCKEditorUploadResponse handleFileUpload02(HttpServletRequest request) {
        return   handleFileUpload(request,CJCKEDITOR_IMAGE_PATH);
    }
}
