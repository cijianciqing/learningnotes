package nanshan.cj.learningnotes.entity.util;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CJArticleSearchUtilBean {
    private Integer draw;
    private Integer limit;
    private Integer start;
    private Integer page;
    private String searchValue;
    private String searchArea;
}
