package nanshan.cj.learningnotes.service;

import cn.com.ns.cj.cjuniversalspringbootstarter.dozer.CJDozerUtil;
import lombok.extern.slf4j.Slf4j;
import nanshan.cj.learningnotes.dao.CJArticleCategoryDao;
import nanshan.cj.learningnotes.dao.dto.CJViewArticleCategory;
import nanshan.cj.learningnotes.entity.CJArticleCategory;
import nanshan.cj.learningnotes.ztree.bean.CJZTreeNodeMove;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CJArticleCategoryService {

    @Autowired
    CJDozerUtil cjDozerUtil;

    @Autowired
    CJArticleCategoryDao cjArticleCategoryDao;

    public List<CJViewArticleCategory> getAllCategory() {
//        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Sort sort = new Sort(Sort.Direction.ASC, "sortNo");//根据sortNo进行排序
        List<CJArticleCategory> all = cjArticleCategoryDao.findAll(sort);
        List<CJViewArticleCategory> convertor = cjDozerUtil.convertor(all, CJViewArticleCategory.class);
        return convertor;
    }

    public Set<Long> getAllChildren(Long parentId) {

        Set<Long> allCategory = new HashSet<>();

        Set<Long> rootCategory = new HashSet<>();
        rootCategory.add(parentId);

        Set<Long> nextGeneraitonCategory = new HashSet<>();
        nextGeneraitonCategory.addAll(rootCategory);


        while (CollectionUtils.isNotEmpty(nextGeneraitonCategory)) {
            allCategory.addAll(nextGeneraitonCategory);
            //便面 id为1的重复
            Set<Long> nextGeneraitonCategory1 = getNextGeneraitonCategory(nextGeneraitonCategory);
            boolean b = nextGeneraitonCategory1.removeAll(nextGeneraitonCategory);
            nextGeneraitonCategory = nextGeneraitonCategory1;

        }
        boolean remove = allCategory.remove(parentId);
        return allCategory;
    }

    /**
     * 获取下一代 category
     *
     * @param parentCategory
     * @return
     */
    public Set<Long> getNextGeneraitonCategory(Set<Long> parentCategory) {
        Set<Long> netxtGenerationCategory = new HashSet<>();
        List<CJArticleCategory> allById = cjArticleCategoryDao.findAllById(parentCategory);
        if (CollectionUtils.isNotEmpty(allById)) {
            for (CJArticleCategory cjArticleCategory : allById) {
                if (CollectionUtils.isNotEmpty(cjArticleCategory.getChildren())) {
                    netxtGenerationCategory.addAll(cjArticleCategory.getChildren().stream().map(CJArticleCategory::getId).collect(Collectors.toSet()));
                }
            }
        }
        return netxtGenerationCategory;
    }
    //获取子类中，最大的SortNo
    public int getTheMaxSortNo(Long parentId){
        int cjDestNo = 0;
        CJArticleCategory targetNode = cjArticleCategoryDao.getOne(parentId);
        List<CJArticleCategory> allChilderen = cjArticleCategoryDao.findByParentOrderBySortNoDesc(targetNode);
        if(CollectionUtils.isNotEmpty(allChilderen)){
            cjDestNo=allChilderen.get(0).getSortNo();
        }
        return cjDestNo;
    }

    public CJViewArticleCategory saveCategory(CJViewArticleCategory cjViewArticleCategory) {
        CJArticleCategory convertor = cjDozerUtil.convertor(cjViewArticleCategory, CJArticleCategory.class);


        int cjDestNo = getTheMaxSortNo(cjViewArticleCategory.getPid());
        convertor.setSortNo(cjDestNo+1);

        CJArticleCategory save = cjArticleCategoryDao.save(convertor);
        CJViewArticleCategory convertor1 = cjDozerUtil.convertor(save, CJViewArticleCategory.class);
        return convertor1;
    }

    public CJViewArticleCategory getCategory(Long id) {
        CJArticleCategory one = cjArticleCategoryDao.getOne(id);
        return cjDozerUtil.convertor(one, CJViewArticleCategory.class);
    }

    public void deleteCategory(Long id) {
        cjArticleCategoryDao.deleteById(id);
    }

    public void nodeMove(CJZTreeNodeMove cjzTreeNodeMove) {

//        log.info(cjzTreeNodeMove.toString());
        String moveType = cjzTreeNodeMove.getMoveType();
        Long treeNodeId = cjzTreeNodeMove.getTreeNodeId();
        Long targetId = cjzTreeNodeMove.getTargetId();

        if(moveType.equalsIgnoreCase("prev")){
            moveNodePre(treeNodeId,targetId);
        }
        if(moveType.equalsIgnoreCase("next")){
            moveNodeNext(treeNodeId,targetId);
        }
        if(moveType.equalsIgnoreCase("inner")){
            moveNodeInner(treeNodeId,targetId);
        }

    }

    @Transactional
    public void moveNodePre(Long treeNodeId, Long targetId) {
        log.info(treeNodeId.toString()+"--prev->"+targetId.toString());
        //1.获取source
        CJArticleCategory sourceNode = cjArticleCategoryDao.getOne(treeNodeId);
        //2.获取targetId的sortNo
        CJArticleCategory targetNode = cjArticleCategoryDao.getOne(targetId);
        boolean isBrother = false;
            //判断source与target是否为同一目录
        if(sourceNode.getParent().getId() == targetNode.getParent().getId()){
            isBrother=true;
        }

        //3.treeNode取代target的sortNo
        sourceNode.setParent(targetNode.getParent());
        sourceNode.setSortNo(targetNode.getSortNo());
        //4.target及之后的node+1
        List<CJArticleCategory> targetNodes = cjArticleCategoryDao.findCJArticleCategoriesBySortNoIsGreaterThanAndParentEqualsOrderBySortNoDesc(targetNode.getSortNo(),targetNode.getParent());
        targetNodes.add(targetNode);

        if(isBrother){
            //原来source,可能再target之后，也可能在target之前
            //如果原来原来source在target之后，就需要删除
            log.info("兄弟nodes,需要将sourceTarget去掉");

            targetNodes.remove(cjArticleCategoryDao.getOne(treeNodeId));
        }
        log.info(String.valueOf(isBrother));
        log.info(sourceNode.toString());
        cjArticleCategoryDao.save(sourceNode);

        if(CollectionUtils.isNotEmpty(targetNodes)){
            targetNodes.forEach(s->{
                s.setSortNo(s.getSortNo()+1);
            });
        }
        cjArticleCategoryDao.saveAll(targetNodes);
    }
    @Transactional
    public void moveNodeNext(Long treeNodeId, Long targetId) {
        log.info(treeNodeId.toString()+"--next->"+targetId.toString());
        //1.获取source
        CJArticleCategory sourceNode = cjArticleCategoryDao.getOne(treeNodeId);
        //2.获取targetId的sortNo
        CJArticleCategory targetNode = cjArticleCategoryDao.getOne(targetId);

        boolean isBrother = false;
        //判断source与target是否为同一目录
        if(sourceNode.getParent().getId() == targetNode.getParent().getId()){
            isBrother=true;
        }
        //3.treeNode的排序为target的sortNo+1
        sourceNode.setParent(targetNode.getParent());
        sourceNode.setSortNo(targetNode.getSortNo()+1);//唯一不同
        //4.target之后的node+1
        List<CJArticleCategory> targetNodes = cjArticleCategoryDao.findCJArticleCategoriesBySortNoIsGreaterThanAndParentEqualsOrderBySortNoDesc(targetNode.getSortNo(),targetNode.getParent());

        if(isBrother){
            //原来source,可能再target之后，也可能在target之前
            //如果原来原来source在target之后，就需要删除
            log.info("兄弟nodes,需要将sourceTarget去掉");

            targetNodes.remove(cjArticleCategoryDao.getOne(treeNodeId));
        }

        if(CollectionUtils.isNotEmpty(targetNodes)){
            targetNodes.forEach(s->{
                s.setSortNo(s.getSortNo()+1);
            });
        }
        cjArticleCategoryDao.saveAll(targetNodes);
    }
    @Transactional
    public void moveNodeInner(Long treeNodeId, Long targetId) {//targetId为父类Id
//        log.info(treeNodeId.toString()+"--inner->"+targetId.toString());
        //1.获取source
        CJArticleCategory sourceNode = cjArticleCategoryDao.getOne(treeNodeId);
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
//        log.info(sourceNode.toString());
        //2.获取target
        CJArticleCategory targetNode = cjArticleCategoryDao.getOne(targetId);

        //3.treeNode的序号设置为：子类最大的sortNo+1
        sourceNode.setParent(targetNode);
        int cjDestNo = getTheMaxSortNo(targetId);
        sourceNode.setSortNo(cjDestNo+1);
//        log.info(sourceNode.toString());
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        //4.target及之后的node+1
        cjArticleCategoryDao.save(sourceNode);

    }
}
