package com.oa.sys.mapper;

import com.oa.sys.entity.Area;

import java.util.List;

/**
 * 区域的增删改查的mapper代理接口
 */

public interface AreaMapper {
    /**
     * 查询所有区域的列表
     * @return
     */
    public List<Area>getAllAreaList();

    /**
     * 通过当前用户的权限获取用户的区域列表
     * @return
     */
    public List<Area>  getAreaListById(Long currentId);

    /**
     *  查询某个节点
     * @return
     */
    public Integer getChildCount(Long areaId);

    /**
     * 查询区域明细
     * @param areaId
     * @return
     */
    public Area getAreaById(Long areaId);

    /**
     * 增加区域
     * @param area
     * @return
     */
    public boolean addArea(Area area);

    /**
     * 删除区域
     * @param areaId
     * @return
     */
    public boolean delArea(Long areaId);

    /**
     * 修改区域
     * @param area
     * @return
     */
    public boolean updateArea(Area area);
}
