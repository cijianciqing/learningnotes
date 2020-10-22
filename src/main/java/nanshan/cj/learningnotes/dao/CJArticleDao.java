package nanshan.cj.learningnotes.dao;

import nanshan.cj.learningnotes.entity.CJArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CJArticleDao extends JpaRepository<CJArticle,Long> {

    List<CJArticle> findByCategory_id(Long categoryId);

}
