package com.oa.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.Dict;
import com.oa.sys.entity.Menu;
import com.oa.sys.entity.Role;
import com.oa.sys.entity.RoleToMenu;
import com.oa.sys.mapper.DictMapper;
import com.oa.sys.mapper.MenuMapper;
import com.oa.sys.mapper.RoleMapper;
import com.oa.sys.service.IDictService;
import com.oa.sys.service.IMenuService;
import com.oa.sys.util.PageParam;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MenuService implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Menu> getAllMenuList() {
        return menuMapper.getAllMenuList();
    }

    @Override
    public List<Menu> getMenuListById(Long currentId) {
        return menuMapper.getMenuListById(currentId);
    }

    @Override
    public Menu getMenuById(Long menuId) {
        return menuMapper.getMenuById(menuId);
    }

    @Override
    public Integer getChildCount(Long menuId) {
        return menuMapper.getChildCount();
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean addMenu(Menu menu) {
        boolean flag =false;
        menu.setUpdateDate(new Date());
        if(UserUtils.getCurrentUserId()!=null){
            menu.setUpdateBy(UserUtils.getCurrentUserId().toString());
        }
        flag= menuMapper.addMenu(menu);
        RoleToMenu roleToMenu=new RoleToMenu();
        roleToMenu.setRoleId(1l);
        roleToMenu.setMenuId(menu.getId());
        flag=roleMapper.addRoleToMenu(roleToMenu);
        return flag;
    }
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean delMenu(Long menuId) {
        boolean flag=false;
        flag =  menuMapper.delMenu(menuId);
        flag =  roleMapper.delRoleAreaByMenuId(menuId);
        return flag;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        menu.setUpdateDate(new Date());
        if(UserUtils.getCurrentUserId()!=null){
            menu.setUpdateBy(UserUtils.getCurrentUserId().toString());
        }
        return menuMapper.updateMenu(menu);
    }

    @Override
    public List<Menu> getMenuListByUserId(Long userId) {
        return menuMapper.getMenuListByUserId(userId);
    }
}
