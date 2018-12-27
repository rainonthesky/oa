package com.oa.sys.mapper;

import com.oa.sys.entity.Dict;
import com.oa.sys.entity.Menu;

import java.util.List;

/**
 * 用户增删改查以及登录验证mapper代理接口
 * @author chenyaping
 */

public interface MenuMapper {
        /**
         * 获取所有菜单列表
         * @return
         */
       public  List<Menu> getAllMenuList();
    /**
     * 通过用户权限获取菜单列表
     * @param currentId
     * @return
     */

    public List<Menu> getMenuListById(Long currentId);

    /**
     *  获取子节点的数目，用于删除的特殊判断
     * @param menuId
     * @return
     */
       public  Integer getChildCount(Long menuId);

        /**
         * 获取某个节点的子节点数目，用于删除的特殊判断
         * @return
         */
       public Integer getChildCount();
        /**
         * 通过Id获取对象信息
         * @param menuId
        * @return
         */
        public Menu getMenuById(Long menuId);

    /**
     * 增加菜单
     * @param menu
     * @return
     */

    public boolean addMenu(Menu menu);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    public boolean delMenu(Long menuId);

    /**
     * 修改菜单的信息
     * @param menu
     * @return
     */
    public boolean updateMenu(Menu menu);

    /**
     * 查询用户权限类的所拥有的菜单
     * @param userId
     * @return
     */
    public List<Menu>getMenuListByUserId(Long userId);





}

