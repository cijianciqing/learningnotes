package nanshan.cj.learningnotes.controller;

import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJRDataUtil;
import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJReturnedData;
import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.dao.dto.CJViewArticleCategory;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.util.CJArticleSearchUtilBean;
import nanshan.cj.learningnotes.entity.util.CJDataTablesReturnData;
import nanshan.cj.learningnotes.service.CJArticleCategoryService;
import nanshan.cj.learningnotes.service.CJArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/article")
public class CJArticlController {

    @Autowired
    CJArticleCategoryService cjArticleCategoryService;
    @Autowired
    CJArticleService cjArticleService;

    @GetMapping(value = "/ttt")
    public String getAllCategory(){
        System.out.println(1/0);
        return "cjT01";
    }

    @PostMapping(value = "/list/{categoryId}")
    public CJReturnedData getAllCategory(@PathVariable(value = "categoryId",required = false) Long id
            , @RequestBody @Valid CJArticleSearchUtilBean cjArticleSearchUtilBean){

//        log.info(cjArticleSearchUtilBean.toString());
        CJDataTablesReturnData articlesBycategoryId = cjArticleService.getArticlesBycategoryId(id, cjArticleSearchUtilBean);

        return  CJRDataUtil.success(articlesBycategoryId);
    }



   /* @GetMapping(value = "/{articleId}")
    public CJReturnedData getArticle(@PathVariable(value = "articleId") Long id ){
        CJViewArticle article = cjArticleService.getArticle(id);
        return  CJRDataUtil.success(article);
    }*/
    @GetMapping(value = "/{articleId}")
    public ModelAndView getArticle(@PathVariable(value = "articleId") Long id ){
        CJViewArticle article = cjArticleService.getArticle(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("article/articleView");
        mv.addObject("cjArticleObject",article);
        mv.addObject("cjView","1");
        return  mv;
    }

    @GetMapping(value = "/withCategory/{categoryId}")
    public ModelAndView createArticlePage(@PathVariable(value = "categoryId") Long id ){
        ModelAndView mv = new ModelAndView();
        CJViewArticle article = new CJViewArticle();
        article.setCategoryId(id);
        mv.setViewName("article/article");
        mv.addObject("cjArticleObject",article);
        return  mv;
    }
    @PostMapping
    public CJReturnedData createArticle(@RequestBody @Valid CJViewArticle cjViewArticle){
        log.info("nanshan.cj.learningnotes.controller.CJArticlController.createArticle...");
        System.out.println(cjViewArticle);
        cjArticleService.saveArticle(cjViewArticle);
        return  CJRDataUtil.success();
    }
    /*
     * 文章更改-页面
     * */
    @GetMapping(value = "/update/{articleId}")
    public ModelAndView updateArticlePage(@PathVariable(value = "articleId") Long id){
        CJViewArticle article = cjArticleService.getArticle(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("article/article");
        mv.addObject("cjArticleObject",article);
        return  mv;
    }
    /*
    * 文章更改-提交
    * */
    @PutMapping
    public CJReturnedData updateArticle(@RequestBody @Valid CJViewArticle cjViewArticle){
        cjArticleService.update(cjViewArticle);
        return  CJRDataUtil.success();
    }

    @DeleteMapping(value = "/{articleId}")
    public CJReturnedData deleteArticle(@PathVariable(value = "articleId") Long id ){
        cjArticleService.delete(id);
        return  CJRDataUtil.success();
    }

}
