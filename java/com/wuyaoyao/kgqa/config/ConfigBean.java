package com.wuyaoyao.kgqa.config;

import com.wuyaoyao.kgqa.core.CoreProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>全局Bean配置类</p>
 */
@Configuration
public class ConfigBean {

    /** 指定问题question及字典的txt模板所在的根目录*/
    @Value("${rootDirPath}")
    private String rootDirPath;

    @Bean
    public CoreProcessor modelProcess() throws Exception{
        return new CoreProcessor(rootDirPath);
    }

}
