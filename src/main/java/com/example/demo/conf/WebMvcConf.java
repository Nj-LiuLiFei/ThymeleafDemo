package com.example.demo.conf;


import com.example.demo.interceptor.LoginAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConf extends WebMvcConfigurationSupport {

    /**
     * 静态文件路径，不拦截静态文件
     */
    private static final String[] ResourceExcludePathPatterns = {"/fonts/*","/css/*","/js/*","/img/*"};

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginAuthInterceptor()).addPathPatterns("/*").excludePathPatterns(ResourceExcludePathPatterns);
        super.addInterceptors(registry);
    }


  /**
     * 处理静态文件
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*").addResourceLocations("classpath:/static/");
        /*registry.addResourceHandler("/fonts/*").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/*").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/*").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/*").addResourceLocations("classpath:/static/img/");*/
        super.addResourceHandlers(registry);
    }
}
