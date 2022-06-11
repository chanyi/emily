package com.emily.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.ServletContext;


public class EmilyServletContextListener implements ApplicationListener<ContextRefreshedEvent> {

    //容器初始化完成之后调用
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        ServletContext servletContext = applicationContext.getBean(ServletContext.class);
        //容器初始化完成之后，将信息缓存到内存中（这里也可以执行业务的redis缓存）
        servletContext.setAttribute("cacheInfo","a");
    }
}
