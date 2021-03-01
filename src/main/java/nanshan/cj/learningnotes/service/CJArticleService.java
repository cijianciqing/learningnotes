package nanshan.cj.learningnotes.service;

import cn.com.ns.cj.cjuniversalspringbootstarter.dozer.CJDozerUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.CJArticleDao;
import nanshan.cj.learningnotes.dao.dto.CJViewArticle;
import nanshan.cj.learningnotes.entity.CJArticle;
import nanshan.cj.learningnotes.entity.util.CJArticleSearchUtilBean;
import nanshan.cj.learningnotes.entity.util.CJDataTablesReturnData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    public CJDataTablesReturnData getArticlesBycategoryId(Long categoryId, CJArticleSearchUtilBean searchBean){
        log.info("getArticlesBycategoryId :"+searchBean.toString());
        List<CJArticle> articles = new ArrayList<>();

        //获取所有的category
        Set<Long> allChildren = new HashSet<>();

        String searchArea = searchBean.getSearchArea();
        if(searchArea.equalsIgnoreCase("cjDir02")){//包含子Dir
            //获取所有的category
            allChildren = cjArticleCategoryService.getAllChildren(categoryId);
        }
        allChildren.add(categoryId);

        //page num从0开始
        Pageable pageable =  PageRequest.of(searchBean.getPage()-1,searchBean.getLimit());


        Set<Long> finalAllChildren = allChildren;
        Specification<CJArticle> specification = (Specification<CJArticle>) (root, query, criteriaBuilder) -> {
            Path<Object> name = root.get("name");
            Path<Object> articleDesc = root.get("articleDesc");
            Path<Object> category = root.get("category");

            //category的in查询
            CriteriaBuilder.In<Object> categoryCriteria = criteriaBuilder.in(category);
            for (Long allChild : finalAllChildren) {
                CriteriaBuilder.In<Object> value = categoryCriteria.value(allChild);
            }
            Predicate cjCriteria = criteriaBuilder.and(categoryCriteria);

            //article的名称和desc相似度匹配
            if(StringUtils.isNotEmpty(searchBean.getSearchValue())){
                Predicate nameLike = criteriaBuilder.like(name.as(String.class), "%"+searchBean.getSearchValue()+"%");
                Predicate descLike = criteriaBuilder.like(articleDesc.as(String.class), "%"+searchBean.getSearchValue()+"%");
                Predicate valueCriteria = criteriaBuilder.or(nameLike,descLike);
                cjCriteria = criteriaBuilder.and(cjCriteria, valueCriteria);
            }


            return cjCriteria;
        };

        Page<CJArticle> all = cjArticleDao.findAll(specification, pageable);

//        log.info(String.valueOf(all.getTotalElements()));
//        log.info(String.valueOf(all.getTotalPages()));
//        List<CJArticle> content = all.getContent();
//        log.info(String.valueOf(content.size()));

        CJDataTablesReturnData cjDataTablesReturnData = new CJDataTablesReturnData();
        cjDataTablesReturnData.setDraw(searchBean.getDraw());
        cjDataTablesReturnData.setTotal(all.getTotalElements());

        cjDataTablesReturnData.setData(cjDozerUtil.convertor(all.getContent(), CJViewArticle.class));
        return cjDataTablesReturnData;
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
