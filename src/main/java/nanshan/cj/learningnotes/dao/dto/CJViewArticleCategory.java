package nanshan.cj.learningnotes.dao.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CJViewArticleCategory {
    private Long id;
    @NotNull(message = "分类名称不能为空")
    private String name;
    private String categoryDesc;
    @NotNull(message = "上级分类不能为空")
    private Long pid;
//    private Boolean isParent = true ;
}
