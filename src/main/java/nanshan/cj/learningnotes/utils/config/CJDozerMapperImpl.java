package nanshan.cj.learningnotes.utils.config;

import cn.com.ns.cj.cjuniversalspringbootstarter.dozer.CJDozerMapperInterface;
import org.springframework.stereotype.Component;

@Component
public class CJDozerMapperImpl implements CJDozerMapperInterface {
    @Override
    public String cjGetMapperFile() {
        return "cjDozerBeanMapping.xml";
    }
}
