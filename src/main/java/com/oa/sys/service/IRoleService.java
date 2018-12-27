package com.oa.sys.service;

import com.oa.sys.entity.*;
import com.oa.sys.entity.vo.RoleDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 字典相关业务层接口代码
 */
public interface IRoleService {
    /**
     * 查询所有区域的列表
     *
     * @return
     */
    public List<Role> getAllRoleList();
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
     * 根据角色的id查询角色信息
     * @param roleId
     * @return
     */
    public Role getRoleById(Long roleId);

    /**
     * 增加角色信息
     * @param roleDto
     * @return
     */
    public boolean addRole(RoleDto roleDto);

    /**
     * 修改角色的信息
     * @param roleDto
     * @return
     */
    public boolean updateRole(RoleDto roleDto);

    /**
     * 删除角色
     * @param roleId
     * @return
     */

   public boolean delRole(Long roleId);




}

