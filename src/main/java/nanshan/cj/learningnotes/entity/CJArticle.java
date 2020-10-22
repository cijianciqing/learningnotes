package nanshan.cj.learningnotes.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cj_article")
@Entity
//逻辑删除注解，删除sql变成了update
@SQLDelete(sql = "update cj_article set cjuniversal_del_status = 1 where id = ?")
//@SQLDeleteAll(sql = "update cj_security_roleToUser set cjuniversal_del_status = 1 where id = ?")
//where条件带上了逻辑删除条件
@Where(clause = "cjuniversal_del_status = 0")
public class CJArticle extends CJBaseEntity implements Serializable {
    /**
     * 数据库表主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String articleDesc;
    @Column(columnDefinition ="TEXT")
    private String content;

    @JsonManagedReference
    @ManyToOne
    private CJArticleCategory category;

    @Override
    public String toString() {
        return "CJArticle{" +
                "id=" + id +
                "name=" + name +'\'' +
                ", articleDesc='" + articleDesc + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category.getId() + '\'' +
                '}';
    }
}
