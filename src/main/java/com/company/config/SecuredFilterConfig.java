package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean =new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/avtoprokat/adm/*");
        bean.addUrlPatterns("/car/adm/*");
        bean.addUrlPatterns("/distr/adm/*");
        bean.addUrlPatterns("/driver/adm/*");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/teritory/adm/*");

        return bean;
    }
}
