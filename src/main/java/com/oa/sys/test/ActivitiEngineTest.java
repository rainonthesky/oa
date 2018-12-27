package com.oa.sys.test;

import javafx.application.Application;
import org.activiti.engine.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivitiEngineTest {
   @Test
    public  void  testActivitiEngine(){
       ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
       ProcessEngine processEngine=(ProcessEngine)ac.getBean("processEngine");
       System.out.println(processEngine);
   }
}
