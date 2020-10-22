package nanshan.cj.learningnotes.ckeditor.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class CJCKEditorUploadResponse {
    private int uploaded;
    private String fileName;
    private String url;
//    private List<CJCKEditorUploadResponseError> error;

    public CJCKEditorUploadResponse(int uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }
}
