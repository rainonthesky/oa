package com.oa.sys.entity;

public class UserToRole implements java.io.Serializable {
    private static final long serialVersionUID = 5740104362530862141L;
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}

