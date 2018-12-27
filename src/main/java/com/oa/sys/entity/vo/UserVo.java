package com.oa.sys.entity.vo;

import com.oa.sys.entity.User;

public class UserVo extends User implements java.io.Serializable {
    private String deptName;
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
