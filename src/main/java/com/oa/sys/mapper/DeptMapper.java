package com.oa.sys.mapper;

import com.oa.sys.entity.Area;

import java.util.List;
import com.oa.sys.entity.Area;
import com.oa.sys.entity.Dept;

import java.util.List;

/**
 * 区域的增删改查的mapper代理接口
 */

public interface DeptMapper {
    /**
     * 查询所有区域的列表
     * @return
     */
    public List<Dept>getAllDeptList();

    /**
     *  查询某个节点
     * @return
     */
    public Integer getChildCount(Long deptId);

    /**
     * 通过id获取用户权限列表
     * @param currentId
     * @return
     */
    public List<Dept>getDeptListByUserId(Long currentId);

    /**
     * 查询区域明细
     * @param deptId
     * @return
     */
    public Dept getDeptById(Long deptId);

    /**
     * 增加区域
     * @param dept
     * @return
     */
    public boolean addDept(Dept dept);

    /**
     * 删除区域
     * @param deptId
     * @return
     */
    public boolean delDept(Long deptId);

    /**
     * 修改区域
     * @param dept
     * @return
     */
    public boolean updateDept(Dept dept);
}
