package com.oa.sys.mapper;

import com.oa.sys.entity.LeaveBean;

import java.util.List;

public interface LeaveBeanMapper {
    /**
     * 获取请假单列表
     * @param leaveBean
     * @return
     */
    public List<LeaveBean> getLeaveBeanList(LeaveBean leaveBean);
    /**
     * 获取请假单明细
     * @param leaveId
     * @return
     */
    public LeaveBean getLeaveBeanById(Long leaveId);

    /**
     * 请假单录入
     * @param leaveBean
     * @return
     */
    public boolean addLeaveBean(LeaveBean leaveBean);

    /**
     * 删除请假单
     * @param leaveId
     * @return
     */
    public boolean delLeaveBean(Long leaveId);

    /**
     * 修改请假单状态
     * @param leaveId
     * @param state
     * @return
     */
    public boolean updateLeaveBeanState(Long leaveId,Integer state);

    /**
     * 修改请假单
     * @param leaveBean
     * @return
     */
    public boolean updateLeaveBean(LeaveBean leaveBean);

    /**
     * 处理请假单（请假单的启动）
     * @param leaveId
     */

    public void doleaveProcess(Long leaveId);

















}
