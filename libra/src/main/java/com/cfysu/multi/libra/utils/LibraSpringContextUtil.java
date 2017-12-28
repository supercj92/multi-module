package com.cfysu.multi.libra.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LibraSpringContextUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;
    

    private static void setContext(ApplicationContext applicationContext){
        LibraSpringContextUtil.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }
    private static void checkApplicationContext(){
        if(applicationContext == null){
            throw new IllegalStateException("applicationContext未注入,请在applicationContext.xml中定义SpringContextUtil");
        }
    }
    @SuppressWarnings("unchecked")
    public static<T> T getBean(String beanName){
        checkApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }
    public static<T> T getBean(Class<T> className){
        checkApplicationContext();
        return (T) applicationContext.getBean(className);
    }
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        setContext(applicationContext);
    }

}
