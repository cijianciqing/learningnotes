package nanshan.cj.learningnotes;

import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.CJArticleDao;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.CJArticleCategory;
import nanshan.cj.learningnotes.service.CJArticleCategoryService;
import nanshan.cj.learningnotes.service.CJArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearningnotesApplicationJPATests {
    @Autowired
    CJArticleDao cjArticleDao;
    @Autowired
    CJArticleCategoryDao cjArticleCategoryDao;
    @Autowired
    CJArticleCategoryService cjArticleCategoryService;

    @Test
    public void contextLoads() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void test01(){
        CJArticleCategory cjArticleCategory01 = new CJArticleCategory();
        cjArticleCategory01.setName("name01");
        cjArticleCategory01.setCategoryDesc("desc01");
        CJArticleCategory save = cjArticleCategoryDao.save(cjArticleCategory01);

        CJArticle cjArticle01 = new CJArticle();
        cjArticle01.setName("name01");
        cjArticle01.setArticleDesc("desc01");
        cjArticle01.setContent("content01");
        cjArticle01.setCategory(save);
        cjArticleDao.save(cjArticle01);

    }

 /*   @Test
    @Transactional
    @Rollback(value = false)
    public void test02(){
        CJArticleCategory one = cjArticleCategoryDao.getOne(1L);
//        List<CJArticleCategory> children = one.getChildren();
        for (CJArticleCategory cj: children) {
            System.out.println(cj.getId());
        }
    }*/


    @Test
    @Transactional
    @Rollback(value = false)
    public void test03(){
        Set<Long> allChildren = cjArticleCategoryService.getAllChildren(3L);
        System.out.println(allChildren);
    }

    @Autowired
    CJArticleService cjArticleService;
    @Test
    @Transactional
    @Rollback(value = false)
    public void test04(){
//        List<CJArticle> articlesBycategoryId = cjArticleService.getArticlesBycategoryId(1L);
//        articlesBycategoryId.forEach(c-> System.out.println(c.getName()));
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void test05(){
        cjArticleCategoryDao.deleteById(7L);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void test06(){
        CJArticleCategory one = cjArticleCategoryDao.getOne(1L);

//        CJArticleCategory byParentOrderBySortNoDesc = cjArticleCategoryDao.findByParentOrderBySortNoDesc(one).get(0);
        List<CJArticleCategory> byParentOrderBySortNoDesc = cjArticleCategoryDao.findCJArticleCategoriesBySortNoIsGreaterThanAndParentEqualsOrderBySortNoDesc(1, one);
        System.out.println(byParentOrderBySortNoDesc.size());
        System.out.println(byParentOrderBySortNoDesc.get(0).getName());
        System.out.println(byParentOrderBySortNoDesc.get(1).getName());
//        System.out.println(byParentOrderBySortNoDesc.getName());
    }


}
