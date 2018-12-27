package com.oa.sys.service.impl;

import com.oa.sys.entity.*;
import com.oa.sys.entity.vo.RoleDto;
import com.oa.sys.mapper.AreaMapper;
import com.oa.sys.mapper.RoleMapper;
import com.oa.sys.service.IAreaService;
import com.oa.sys.service.IRoleService;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoleList() {
        return roleMapper.getAllRoleList();
    }

    @Override
    public List<RoleToMenu> getMenuListByRoleId(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<RoleToDept> getDeptListByRoleId(Long roleId) {
        return roleMapper.getDeptListByRoleId(roleId);
    }

    @Override
    public List<RoleToArea> getAreaListByRoleId(Long roleId) {
        return roleMapper.getAreaListByRoleId(roleId);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleMapper.getRoleById(roleId);
    }


    /*
     * 1:保存角色信息,返回角色ID
     * 2:根据角色id,部门id组合角色部门对应列表,进行批量插入
     * 3:根据角色id,区域id组合角色区域对应列表,进行批量插入
     * 4:根据角色id,菜单id组合角色菜单对应列表,进行批量插入
     * */

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean addRole(RoleDto roleDto) {
        boolean flag =false;
//        增加角色对象
        Role role =roleDto.getRole();
        role.setUpdateBy(UserUtils.getCurrentUserId().toString());
        role.setUpdateDate(new Date());
        flag =roleMapper.addRole(role);
        Long roleId=role.getId();
        if(roleDto.getMenuIds().values().size()!=0) {
            List<RoleToMenu> roleMenuList = new ArrayList<>();
            RoleToMenu roleMenu;
            for (Long menuId : roleDto.getMenuIds().values()) {
                roleMenu = new RoleToMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenuList.add(roleMenu);
            }
            //批量插入
            flag = roleMapper.addRoleToMenuBatch(roleMenuList);
        }

        //根据新增对象的id（角色id）组合角色部门对应列表，进行批量插入

        if(roleDto.getDeptIds().values().size()!=0){
        List<RoleToDept> roleDeptList =new ArrayList<>();
        RoleToDept roleDept;
        for (Long deptId:roleDto.getDeptIds().values()){
            roleDept=new RoleToDept();
            roleDept.setRoleId(roleId);
            roleDept.setDeptId(deptId);
            roleDeptList.add(roleDept);
        }
        flag=roleMapper.addRoleToDeptBatch(roleDeptList);
        }

        //根据新增对象的id（角色id）组合角色部门对应列表，进行批量插入
        if(roleDto.getDeptIds().values().size()!=0) {
            List<RoleToArea> roleAreaList = new ArrayList<>();
            RoleToArea roleArea;
            for (Long areaId : roleDto.getAreaIds().values()) {
                roleArea = new RoleToArea();
                roleArea.setRoleId(roleId);
                roleArea.setAreaId(areaId);
                roleAreaList.add(roleArea);
            }
            flag = roleMapper.addRoleToAreaBatch(roleAreaList);
        }
        return flag;
    }
    //删除角色列表以及角色相关的列表
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean delRole(Long roleId) {
        boolean flag =false;
        flag =roleMapper.delRoleMenuByRoleId(roleId);
        flag =roleMapper.delRoleDeptByRoleId(roleId);
        flag =roleMapper.delRoleAreaByRoleId(roleId);
        flag =roleMapper.delRole(roleId);
        return flag;
    }


    /*
     * 1:保存修改的角色信息
     * 2:根据角色id删除角色部门对应关系,角色菜单对应关系,角色区域对应关系
     * 3:根据角色id,部门id组合角色部门对应列表,进行批量插入
     * 4:根据角色id,区域id组合角色区域对应列表,进行批量插入
     * 5:根据角色id,菜单id组合角色菜单对应列表,进行批量插入
     * */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean updateRole(RoleDto roleDto) {
        boolean flag =false;
        //修改角色对象
        Role role =roleDto.getRole();
        role.setUpdateBy(UserUtils.getCurrentUserId().toString());
        role.setUpdateDate(new Date());
        flag= roleMapper.updateRole(role);
        Long roleId =role.getId();
        //根据角色id删除角色部门关系，角色菜单对应的关系，角色区域对应关系
        flag =roleMapper.delRoleAreaByRoleId(roleId);
        flag =roleMapper.delRoleDeptByRoleId(roleId);
        flag =roleMapper.delRoleMenuByRoleId(roleId);
        //根据新增对象的id（角色ID）菜单id组合角色菜单的列表，进行批量插入
        if(roleDto.getMenuIds().values().size()!=0) {
            List<RoleToMenu> roleMenuList = new ArrayList<>();
            RoleToMenu roleMenu;
            for (Long menuId : roleDto.getMenuIds().values()) {
                roleMenu = new RoleToMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                roleMenuList.add(roleMenu);
            }
            //批量插入菜单角色对应的列表
            flag = roleMapper.addRoleToMenuBatch(roleMenuList);
        }

        //根据新增对象的id（角色id）组合角色部门对应列表，进行批量插入
        if(roleDto.getDeptIds().values().size()!=0) {
            List<RoleToDept> roleDeptList = new ArrayList<>();
            RoleToDept roleDept;
            for (Long deptId : roleDto.getDeptIds().values()) {
                roleDept = new RoleToDept();
                roleDept.setRoleId(roleId);
                roleDept.setDeptId(deptId);
                roleDeptList.add(roleDept);
            }
            flag = roleMapper.addRoleToDeptBatch(roleDeptList);
        }

        //根据新增对象的id（角色id）组合角色部门对应列表，进行批量插入
        if(roleDto.getAreaIds().values().size()!=0) {
            List<RoleToArea> roleAreaList = new ArrayList<>();
            RoleToArea roleArea;
            for (Long areaId : roleDto.getAreaIds().values()) {
                roleArea = new RoleToArea();
                roleArea.setRoleId(roleId);
                roleArea.setAreaId(areaId);
                roleAreaList.add(roleArea);
            }
            flag = roleMapper.addRoleToAreaBatch(roleAreaList);
        }

        return flag;
    }


}
