package cj.springboot.template.config.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;


/*
* 自定义Filter示例，暂时禁用
* */
@Configuration
public class CJServletConfig {

    /*
    * 配置CorsFilter，避免自定义的其他Filtery影响
    * */
    @Bean
    public FilterRegistrationBean corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        //config.addAllowedOrigin("http://manage.leyou.com");
        //config.addAllowedOrigin("http://www.leyou.com");
        //java.lang.IllegalArgumentException:当allowCredentials为true时，allowedOrigins不能包含特殊值"*"
//        config.addAllowedOrigin("*");
        config.addAllowedOriginPattern("*");
        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
//        config.addAllowedMethod("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setMaxAge(3600L);
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        //return new CorsFilter(configSource);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setOrder(Integer.MIN_VALUE);
        return bean;
    }


//    @Bean
    public FilterRegistrationBean timeFilter() {

        FilterRegistrationBean registrationan = new FilterRegistrationBean();

        CJTimeFilter timeFilter = new CJTimeFilter();

        registrationan.setFilter(timeFilter);


        List<String> urls = new ArrayList<>();

        urls.add("/*");

        registrationan.setUrlPatterns(urls);
        registrationan.setOrder(100);
        return registrationan;

    }
}
