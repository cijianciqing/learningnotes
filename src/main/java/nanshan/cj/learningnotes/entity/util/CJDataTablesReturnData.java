package nanshan.cj.learningnotes.entity.util;

import lombok.*;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.entity.CJArticle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CJDataTablesReturnData {
    private Integer draw;
    private Long total;
    private List<CJViewArticle> data;
}
