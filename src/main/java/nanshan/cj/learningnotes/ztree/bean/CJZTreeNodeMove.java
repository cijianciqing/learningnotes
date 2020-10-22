package nanshan.cj.learningnotes.ztree.bean;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CJZTreeNodeMove {
    @NotNull(message = "treeNodeId不能为空")
    private Long treeNodeId;
    @NotNull(message = "targetId不能为空")
    private Long targetId;
    @NotNull(message = "moveType不能为空")
    private String moveType;
}
