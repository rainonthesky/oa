package com.oa.sys.test;

import com.oa.sys.entity.Menu;
import com.oa.sys.service.IAreaService;
import com.oa.sys.service.IDeptService;
import com.oa.sys.service.IMenuService;
import com.oa.sys.service.IUserService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ServiceTest {
    private ApplicationContext ac = null;
    @Before
    public void init(){
        ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    @Test
    public void testGetUserList(){
        IMenuService menuService = (IMenuService) ac.getBean("menuService");
        List<Menu> menuList= menuService.getMenuListById(13l);
        System.out.println(menuList.size());

    }



}
