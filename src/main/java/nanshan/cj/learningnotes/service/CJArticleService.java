package nanshan.cj.learningnotes.service;

import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.CJArticleDao;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.utils.dozer.CJDozerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CJArticleService {

    @Autowired
    CJDozerUtil cjDozerUtil;

    @Autowired
    CJArticleCategoryDao cjArticleCategoryDao;
    @Autowired
    CJArticleDao cjArticleDao;

    @Autowired
    CJArticleCategoryService cjArticleCategoryService;

    public List<CJViewArticle> getArticlesBycategoryId(Long categoryId){
        List<CJArticle> articles = new ArrayList<>();
        //获取所有的category
        Set<Long> allChildren = cjArticleCategoryService.getAllChildren(categoryId);
        allChildren.add(categoryId);
        for (Long aCategoryId: allChildren) {
            List<CJArticle> byCategory_id = cjArticleDao.findByCategory_id(aCategoryId);
            articles.addAll(byCategory_id);
        }

        return cjDozerUtil.convertor(articles, CJViewArticle.class);
    }

    public void getArticleCategory(){

    }

    public CJViewArticle getArticle(Long id) {
        CJArticle one = cjArticleDao.getOne(id);
        CJViewArticle convertor = cjDozerUtil.convertor(one, CJViewArticle.class);
        return convertor;
    }

    public void saveArticle(CJViewArticle cjViewArticle) {
        CJArticle convertor = cjDozerUtil.convertor(cjViewArticle, CJArticle.class);
//        System.out.println(convertor);
        CJArticle save = cjArticleDao.save(convertor);
    }

    public void update(CJViewArticle cjViewArticle) {
        CJArticle convertor = cjDozerUtil.convertor(cjViewArticle, CJArticle.class);
        CJArticle save = cjArticleDao.save(convertor);
    }

    public void delete(Long id) {
        cjArticleDao.deleteById(id);
    }
}
