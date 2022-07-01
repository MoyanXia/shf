package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("oldqiu")
//                .password("{noop}oldqiu")
//                .roles("");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //允许iframe嵌套显示
        http.headers().frameOptions().disable();

        //设置放行静态资源
        http.authorizeRequests()
                //放行静态资源, login.html页面
                .antMatchers("/static/**", "/login").permitAll()
                //其他资源登录后即可访问
                .anyRequest().authenticated();


        //设置自定义登录页面
        http.formLogin()
                .loginPage("/login")//设置登录页面
                .loginProcessingUrl("/login_process")//设置处理登录请求的url
                .defaultSuccessUrl("/")//设置登录成功跳转的页面
                .failureForwardUrl("/login");//设置登录失败跳转的页面

        //设置注销登录
        http.logout()
                .logoutUrl("/logout")//设置处理注销请求的url
                .logoutSuccessUrl("/login");//设置注销成功跳转的页面

        //禁用csrf
        http.csrf().disable();

        //自定义权限不足处理方案
//        http.exceptionHandling()
//                .accessDeniedHandler(new AtguiguAccessDeniedHandler());
        http.exceptionHandling().accessDeniedHandler(new AtguiguAccessDeniedHandler());

    }
}