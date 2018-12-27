package com.oa.sys.mapper;

import com.oa.sys.entity.*;
import com.oa.sys.entity.vo.RoleDto;

import java.util.List;

/**
 * 区域的增删改查的mapper代理接口
 */

public interface RoleMapper {
    /**
     * 查询所有区域的列表
     * @return
     */
    public List<Role>getAllRoleList();
    /**
     * 根据角色id查询角色菜单对应关系
     * @param roleId
     * @return
     */
    public List<RoleToMenu>getMenuListByRoleId(Long roleId);
    /**
     * 根据角色id查询角色部门对应关系
     * @param roleId
     * @return
     */
    public List<RoleToDept>getDeptListByRoleId(Long roleId);
    /**
     * 根据角色id查询角色区域对应关系
     * @param roleId
     * @return
     */
    public List<RoleToArea>getAreaListByRoleId(Long roleId);

    /**
     * 根据角色信息查找角色信息
     * @param roleId
     * @return
     */
    public Role getRoleById(Long roleId);

    /**
     * 增加角色信息
     * @param role
     * @return
     */
    public boolean addRole(Role role);

    /**
     * 批量插入角色菜单对应的信息
     * @param roleToMenuList
     * @return
     */
    public boolean addRoleToMenuBatch(List<RoleToMenu> roleToMenuList);


    /**
     * 批量插入角色部门对应的信息
     * @param roleToDeptList
     * @return
     */
    public boolean addRoleToDeptBatch(List<RoleToDept> roleToDeptList);

    /**
     * 批量插入角色部门对应的信息
     * @param roleToAreaList
     * @return
     */
    public boolean addRoleToAreaBatch(List<RoleToArea> roleToAreaList);

    /**
     * 增加角色菜单对应记录
     * @param roleMenu
     * @return
     */
    public boolean addRoleToMenu(RoleToMenu roleMenu);
    /**

    /**
     * 增加该功能同时给管理员赋予功能
     * @param roleToDept
     * @return
     */
    public boolean addRoleToDept(RoleToDept roleToDept);

    /**
     * 增加功能同时给管理员赋予功能
     * @param roleToArea
     * @return
     */
    public boolean addRoleToArea(RoleToArea roleToArea);

    /**
     * 根据角色ID删除角色菜单对应的关系
     * @param roleId
     * @return
     */
    public boolean delRoleMenuByRoleId(Long roleId);

    /**
     * 根据区域ID删除角色区域对应的关系
     * @param areaId
     * @return
     */
    public boolean delRoleAreaByAreaId(Long areaId);

    /**
     * 根据区域ID删除角色区域对应的关系
     * @param deptId
     * @return
     */
    public boolean delRoleAreaByDeptId(Long deptId);

    /**
     * 根据区域ID删除菜单区域对应的关系
     * @param deptId
     * @return
     */
    public boolean delRoleAreaByMenuId(Long deptId);

    /**
     * 根据角色ID删除角色部门对应的关系
     * @param roleId
     * @return
     */
    public boolean delRoleDeptByRoleId(Long roleId);
    /**
     * 根据角色ID删除角色区域对应的关系
     * @param roleId
     * @return
     */
    public boolean delRoleAreaByRoleId(Long roleId);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public boolean delRole(Long roleId);

    /**
     * 修改角色
     * @param role
     * @return
     */
    public boolean updateRole(Role role);


}
