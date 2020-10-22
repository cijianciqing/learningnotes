package nanshan.cj.learningnotes.utils.dozer;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.el.DefaultELEngine;
import com.github.dozermapper.core.el.ELExpressionFactory;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CJDozerConfig {



    @Bean
    public Mapper dozerMapper() {
        DefaultELEngine elEngine = new DefaultELEngine(ELExpressionFactory.newInstance());
        Mapper mapper = DozerBeanMapperBuilder.create()
                //指定 dozer mapping 的配置文件(放到 resources 类路径下即可)，可添加多个 xml 文件，用逗号隔开
                .withMappingFiles("dozerBeanMapping.xml")
                .withELEngine(elEngine)
                .withMappingBuilder(beanMappingBuilder())
                .build();
        return mapper;
    }

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                // 个性化配置添加在此
//                mapping(CJResource.class, CJViewResource.class)
//                        .fields("parent.id", "pid")
//                        .fields("cjResourceType.abbreviation","type")
//                        .fields("cjResourceType.abbreviation==","type")
//                        .fields("cjResourceMethod.abbreviation", "method");
            }
        };
    }
}