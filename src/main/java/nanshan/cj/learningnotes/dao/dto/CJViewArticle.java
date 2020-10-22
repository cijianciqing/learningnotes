package nanshan.cj.learningnotes.dao.dto;

import lombok.*;

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
    @NotNull(message = "文章名称不能为空")
    private String name;
    private String articleDesc;
    private String content;
    @NotNull(message = "文章分类不能为空")
    private Long categoryId;
    private Date createTime;
    private Date updateTime;
}
