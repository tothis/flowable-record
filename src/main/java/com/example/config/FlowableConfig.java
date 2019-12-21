package com.example.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.ui.common.properties.FlowableCommonAppProperties;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author lilei
 * @description
 * @time 2019/9/4 16:13
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Bean
    @Primary
    public FlowableCommonAppProperties flowableCommonAppProperties() {
        FlowableCommonAppProperties flowableCommonAppProperties = new FlowableCommonAppProperties();
        flowableCommonAppProperties.setIdmUrl("http://localhost:8083/flowable-idm");
        FlowableCommonAppProperties.Admin idmAdmin = flowableCommonAppProperties.getIdmAdmin();
        idmAdmin.setUser("admin");
        idmAdmin.setPassword("test");
        return flowableCommonAppProperties;
    }

    @Bean
    public FlowableModelerAppProperties flowableModelerAppProperties() {
        return new FlowableModelerAppProperties();
    }

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }
}