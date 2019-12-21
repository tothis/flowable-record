package com.example.config;

import com.example.lnterceptor.FlowHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/31 11:36
 * @description webconfig配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("@/*").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "static/");
    }

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FlowHandlerInterceptor()).addPathPatterns("/**");
    }
}