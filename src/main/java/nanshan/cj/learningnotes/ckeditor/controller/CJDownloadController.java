package nanshan.cj.learningnotes.ckeditor.controller;

import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.ckeditor.entity.CJUploadedFile;
import nanshan.cj.learningnotes.ckeditor.service.CJUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


@Slf4j
@RestController
@RequestMapping(value = "/cjCKEditor/downloader")
public class CJDownloadController {

    @RequestMapping
    public String tes() {
        return "abc";
    }

    @Autowired
    CJUploadFileService cjUploadFileService;

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPic(@PathVariable(value = "id") Long fileId) throws Exception {
        File file = new File(cjUploadFileService.getFile(fileId).getFilePath());
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;

    }

    @GetMapping(value = "/file/{id}")
    public void getFile(@PathVariable(value = "id") Long fileId, HttpServletResponse response) throws Exception {
        CJUploadedFile file1 = cjUploadFileService.getFile(fileId);
        File file = new File(file1.getFilePath());
        String fileName = URLEncoder.encode(file1.getFileName(),"UTF-8");//解决中文乱码

        log.info(file1.toString());
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
//            response.setContentType("bin");
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + fileName + "\"");
//            response.addHeader("Content-Disposition", "attachment; filename=\"" + file1.getFileName() + "\"");
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return response;
    }


}
