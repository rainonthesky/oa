package com.oa.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LeaveBean {
    private static final long serialVersionUID = 4277515992589522616L;

    private Long leaveId;	//请假单id
    private Long leaveUserId;  //请假人员id
    private String leaveUserName;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date leaveDate;
    private Integer leaveDays;
    private String leaveReason;
    private String remark;
    private Integer leaveState;
    private String leaveStateDesc;

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Long getLeaveUserId() {
        return leaveUserId;
    }

    public void setLeaveUserId(Long leaveUserId) {
        this.leaveUserId = leaveUserId;
    }

    public String getLeaveUserName() {
        return leaveUserName;
    }

    public void setLeaveUserName(String leaveUserName) {
        this.leaveUserName = leaveUserName;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLeaveState() {
        return leaveState;
    }

    public void setLeaveState(Integer leaveState) {
        this.leaveState = leaveState;
    }

    public String getLeaveStateDesc() {
        return leaveStateDesc;
    }

    public void setLeaveStateDesc(String leaveStateDesc) {
        this.leaveStateDesc = leaveStateDesc;
    }
}
