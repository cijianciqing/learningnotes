package nanshan.cj.learningnotes.utils.dozer;

import com.github.dozermapper.core.Mapper;
import nanshan.cj.learningnotes.dao.dto.CJViewArticleCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class CJDozerUtil {
    @Autowired
    private Mapper dozerMapper;


    /**
     * List  实体类 转换器
     *
     * @param source 原数据
     * @param clz    转换类型
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> List<T> convertor(List<S> source, Class<T> clz) {
        if (source == null) return null;
        List<T> map = new ArrayList<>();
        for (S s : source) {
            map.add(dozerMapper.map(s, clz));
        }
        return map;
    }

    /**
     * Set 实体类 深度转换器
     *
     * @param source 原数据
     * @param clz    目标对象
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> Set<T> convertor(Set<S> source, Class<T> clz) {
        if (source == null) return null;
        Set<T> set = new TreeSet<>();
        for (S s : source) {
            set.add(dozerMapper.map(s, clz));
        }
        return set;
    }

    /**
     * 实体类 深度转换器
     *
     * @param <T>
     * @param <S>
     * @param source
     * @param clz
     * @return
     */

    public <T, S> T convertor(S source, Class<T> clz) {
        if (source == null) return null;
        return dozerMapper.map(source, clz);
    }

    public void convertor(Object source, Object object) {
        dozerMapper.map(source, object);
    }

    public <T> void copyConvertor(T source, Object object) {
        dozerMapper.map(source, object);
    }


}
