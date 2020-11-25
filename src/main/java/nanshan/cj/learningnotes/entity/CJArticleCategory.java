package nanshan.cj.learningnotes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cj_article_category")
@Entity
//逻辑删除注解，删除sql变成了update
@SQLDelete(sql = "update cj_article_category set cjuniversal_del_status = 1 where id = ?")
//@SQLDeleteAll(sql = "update cj_security_roleToUser set cjuniversal_del_status = 1 where id = ?")
//where条件带上了逻辑删除条件
@Where(clause = "cjuniversal_del_status = 0")
//在转化成json的时候，fasterxml.jackson将对象转换为json报错，发现有字段为null。
//Jackson用到的注解（Jackson是springboot默认json转化工具），注解在类上。对于懒加载的属性，在json序列化时忽略该属性。
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class CJArticleCategory extends CJBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String categoryDesc;

    @JsonManagedReference
    @ManyToOne
    private CJArticleCategory parent;

    @JsonBackReference
    @OneToMany(mappedBy = "parent",fetch=FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @OrderBy("sortNo ASC")
    private Set<CJArticleCategory> children;

    //序号
    private int sortNo;

    @JsonBackReference
    @OneToMany(mappedBy = "category",fetch=FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private Set<CJArticle> articles = new HashSet<>();

    @Override
    public String toString() {
        return "CJArticleCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryDesc='" + categoryDesc + '\'' +
                ", parent=" + parent.getId()  + '\'' +
//                ", children=" + children +
                ", sortNo=" + sortNo  + '\'' +
//                ", articles=" + articles +
                '}';
    }
}
