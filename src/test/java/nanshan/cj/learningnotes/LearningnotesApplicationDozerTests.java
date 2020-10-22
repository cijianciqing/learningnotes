package nanshan.cj.learningnotes;

import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.CJArticleDao;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.CJArticleCategory;
import nanshan.cj.learningnotes.service.CJArticleCategoryService;
import nanshan.cj.learningnotes.service.CJArticleService;
import nanshan.cj.learningnotes.utils.dozer.CJDozerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearningnotesApplicationDozerTests {
    @Autowired
    CJArticleDao cjArticleDao;
    @Autowired
    CJArticleCategoryDao cjArticleCategoryDao;
    @Autowired
    CJArticleCategoryService cjArticleCategoryService;
    @Autowired
    CJDozerUtil cjDozerUtil;

    public void test01(){
        CJArticle cjArticle = new CJArticle();
        CJArticleCategory cjArticleCategory = new CJArticleCategory();
        cjArticleCategory.setId(1l);
        cjArticleCategory.setName("c1");
        cjArticleCategory.setCategoryDesc("c11");

        cjArticle.setName("a1");
        cjArticle.setArticleDesc("a11");
        cjArticle.setContent("a111");
        cjArticle.setCategory(cjArticleCategory);


    }


}
