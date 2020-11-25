package nanshan.cj.learningnotes.dao.dto;

import lombok.*;
import nanshan.cj.learningnotes.entity.CJArticleState;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CJViewArticle {
    private Long id;
    @NotBlank(message = "文章名称不能为空")
    private String name;
    private String articleDesc;
    private String content;
    @NotNull(message = "文章分类不能为空")
    private Long categoryId;

    private String createTime;
    private String updateTime;

    @NotBlank(message = "文章状态不能为空")
    private String articleState;
}
