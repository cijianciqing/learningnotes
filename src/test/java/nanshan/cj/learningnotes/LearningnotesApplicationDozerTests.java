package nanshan.cj.learningnotes;

import cn.com.ns.cj.cjuniversalspringbootstarter.dozer.CJDozerUtil;
import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.CJArticleDao;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.CJArticleCategory;
import nanshan.cj.learningnotes.entity.CJArticleState;
import nanshan.cj.learningnotes.service.CJArticleCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    public void test01() {
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

    @Test
    public void test02() {
        CJViewArticle cjViewArticle = new CJViewArticle();
        cjViewArticle.setName("abc");
        cjViewArticle.setArticleState("improve");
        CJArticle convertor = cjDozerUtil.convertor(cjViewArticle, CJArticle.class);
        System.out.println(convertor.getCjArticleState());
    }

    @Test
    public void test03() {
        CJArticle cjArticle = new CJArticle();
//        CJArticleCategory cjArticleCategory = new CJArticleCategory();
//        cjArticleCategory.setId(1l);
//        cjArticleCategory.setName("c1");
//        cjArticleCategory.setCategoryDesc("c11");

        cjArticle.setName("a1");
        cjArticle.setArticleDesc("a11");
        cjArticle.setContent("a111");
        cjArticle.setCjArticleState(CJArticleState.CJVerify);
        CJViewArticle convertor = cjDozerUtil.convertor(cjArticle, CJViewArticle.class);
        System.out.println(convertor);
//        cjArticle.setCategory(cjArticleCategory);


    }


}
