package com.oa.sys.service;

import com.oa.sys.entity.Area;
import com.oa.sys.entity.Dept;

import java.util.List;

/**
 * 字典相关业务层接口代码
 */
public interface IDeptService {
    /**
     * 查询所有区域的列表
     *
     * @return
     */
    public List<Dept> getAllDeptList();

    /**
     * 通过id获取用户权限列表
     * @param currentId
     * @return
     */
    public List<Dept>getDeptListByUserId(Long currentId);

    /**
     * 查询某个节点
     *
     * @return
     */
    public Integer getChildCount(Long deptId);

    /**
     * 查询区域明细
     *
     * @param deptId
     * @return
     */
    public Dept getDeptById(Long deptId);

    /**
     * 增加区域
     *
     * @param dept
     * @return
     */
    public boolean addDept(Dept dept);

    /**
     * 删除区域
     *
     * @param deptId
     * @return
     */
    public boolean delDept(Long deptId);

    /**
     * 修改区域
     *
     * @param area
     * @return
     */
    public boolean updateDept(Dept area);
}

