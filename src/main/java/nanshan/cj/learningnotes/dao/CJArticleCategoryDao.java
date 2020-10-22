package nanshan.cj.learningnotes.dao;

import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.CJArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CJArticleCategoryDao extends JpaRepository<CJArticleCategory,Long> {



    //获取父类下比SortNo还大的CJArticleCategory,根据sortNo desc排序
    List<CJArticleCategory> findCJArticleCategoriesBySortNoIsGreaterThanAndParentEqualsOrderBySortNoDesc(int sortNo, CJArticleCategory parent);

    //获取父类下所有的直属子类，并按照sortNo排序
    List<CJArticleCategory>  findByParentOrderBySortNoDesc(CJArticleCategory targetId);

}
