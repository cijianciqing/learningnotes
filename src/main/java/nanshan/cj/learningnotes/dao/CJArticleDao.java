package nanshan.cj.learningnotes.dao;

import nanshan.cj.learningnotes.entity.CJArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CJArticleDao extends JpaRepository<CJArticle,Long> , JpaSpecificationExecutor<CJArticle> {

    List<CJArticle> findByCategory_id(Long categoryId);

}
