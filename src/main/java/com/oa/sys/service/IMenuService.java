package com.oa.sys.service;
import com.oa.sys.entity.Menu;
import java.util.List;

/**
 * 字典相关业务层接口代码
 */
public interface IMenuService {
    /**
     * 获取所有字典的类型
     * @return
     */
    public List<Menu> getAllMenuList();

    /**
     * 通过用户权限获取菜单列表
     * @param currentId
     * @return
     */

    public List<Menu> getMenuListById(Long currentId);

    /**
     * 通过Id获取对象信息
     * @param menuId
     * @return
     */
    public Menu getMenuById(Long menuId);

    /**
     * 获取某个节点的子节点的个数
     * @param menuId
     * @return
     */

    public Integer getChildCount(Long menuId);

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
