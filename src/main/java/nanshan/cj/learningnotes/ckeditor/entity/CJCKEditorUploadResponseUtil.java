package nanshan.cj.learningnotes.ckeditor.entity;

import cn.hutool.core.collection.ListUtil;
import lombok.*;
import nanshan.cj.learningnotes.utils.returnData.CJReturnEnum;
import nanshan.cj.learningnotes.utils.returnData.CJReturnedData;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;


public class CJCKEditorUploadResponseUtil {

    public static final String CJCKEDITOR_FILE_PATH = "E:\\java\\upload\\file\\";
    public static final String CJCKEDITOR_IMAGE_PATH = "E:\\java\\upload\\image\\";

    public static final String CJCKEDITOR_FILE_URL_PREFIX = "http://localhost:8089/cjCKEditor/downloader/file/";
    public static final String CJCKEDITOR_IMAGE_URL_PREFIX = "http://localhost:8089/cjCKEditor/downloader/image/";

    //成功
    public static CJCKEditorUploadResponse success(String fileName, String cjUploadPath, Long fileId) {
        String url = "";
        if (cjUploadPath.equalsIgnoreCase(CJCKEDITOR_FILE_PATH)) {
            url = CJCKEDITOR_FILE_URL_PREFIX + fileId;
        }else{
            url = CJCKEDITOR_IMAGE_URL_PREFIX + fileId;
        }
        return new CJCKEditorUploadResponse(1, fileName, url);
    }

    //失败返回信息
    public static CJCKEditorUploadResponse error() {
        CJCKEditorUploadResponseError m1 = new CJCKEditorUploadResponseError("The file is too big.");
        CJCKEditorUploadResponse r1 = new CJCKEditorUploadResponse();
        r1.setUploaded(0);
//        r1.setError(ListUtil.list(false,m1));
        return r1;
    }
}
