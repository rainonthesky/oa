package com.oa.sys.entity.vo;

import com.oa.sys.entity.User;

import java.util.Map;

public class UserDto extends User implements java.io.Serializable {
    private static final long serialVersionUID = 3573926540976059111L;
    private User user;
    private Map<Long,Long>roleIds;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Long, Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Map<Long, Long> roleIds) {
        this.roleIds = roleIds;
    }
}
