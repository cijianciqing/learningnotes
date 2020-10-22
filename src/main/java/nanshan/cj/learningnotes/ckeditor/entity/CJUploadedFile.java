package nanshan.cj.learningnotes.ckeditor.entity;

import lombok.*;
import nanshan.cj.learningnotes.entity.CJBaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cj_uploaded_file")
@Entity
//逻辑删除注解，删除sql变成了update
@SQLDeleteAll(sql = "update cj_uploaded_file set cjuniversal_del_status = 1 where id = ?")
//where条件带上了逻辑删除条件
@Where(clause = "cjuniversal_del_status = 0")
@ToString
public class CJUploadedFile extends CJBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;
}
