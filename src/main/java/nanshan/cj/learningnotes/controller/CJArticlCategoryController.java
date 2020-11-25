package nanshan.cj.learningnotes.controller;

import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJRDataUtil;
import cn.com.ns.cj.cjuniversalspringbootstarter.returnData.CJReturnedData;
import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.dao.dto.CJViewArticleCategory;
import nanshan.cj.learningnotes.service.CJArticleCategoryService;
import nanshan.cj.learningnotes.ztree.bean.CJZTreeNodeMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/article/category")
public class CJArticlCategoryController {

    @Autowired
    CJArticleCategoryService cjArticleCategoryService;

    @GetMapping(value = "/getAll")
    public CJReturnedData getAllCategory() {
        List<CJViewArticleCategory> allCategory = cjArticleCategoryService.getAllCategory();
        return CJRDataUtil.success(allCategory);
    }

    @PostMapping
    public CJReturnedData createCategory(@RequestBody @Valid CJViewArticleCategory cjViewArticleCategory) {
        CJViewArticleCategory re = cjArticleCategoryService.saveCategory(cjViewArticleCategory);
        return CJRDataUtil.success(re);
    }

    @GetMapping(value = "/{categoryId}")
    public CJReturnedData getCategory(@PathVariable(value = "categoryId") Long id) {
        CJViewArticleCategory category = cjArticleCategoryService.getCategory(id);
        return CJRDataUtil.success(category);
    }

    @PutMapping
    public CJReturnedData updateCategory(@RequestBody @Valid CJViewArticleCategory cjViewArticleCategory) {
        CJViewArticleCategory re = cjArticleCategoryService.saveCategory(cjViewArticleCategory);
        return CJRDataUtil.success(re);
    }

    @DeleteMapping(value = "/{categoryId}")
    public CJReturnedData deleteCategory(@PathVariable(value = "categoryId") Long id) {
        cjArticleCategoryService.deleteCategory(id);
        return CJRDataUtil.success();
    }


    @PostMapping("/move")
    public CJReturnedData moveCategory(@RequestBody @Valid CJZTreeNodeMove cjzTreeNodeMove) {
        cjArticleCategoryService.nodeMove(cjzTreeNodeMove);
        return CJRDataUtil.success();
    }
}
