package cj.springboot.template.util.dozer;

import cn.com.ns.cj.cjuniversalspringbootstarter.dozer.CJDozerMapperInterface;
import org.springframework.stereotype.Component;

@Component
public class CJDozerMapperSysImpl implements CJDozerMapperInterface {
    @Override
    public String cjGetMapperFile() {
        return "cjSysDozerBeanMapping.xml";
    }
}
