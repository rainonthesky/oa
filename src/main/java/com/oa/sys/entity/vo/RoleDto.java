package com.oa.sys.entity.vo;

import com.oa.sys.entity.Role;

import java.util.Map;

public class RoleDto extends Role implements java.io.Serializable {
    private static final long serialVersionUID = 3573926540976059111L;
    private Role role;
    private Map<Long,Long> deptIds;
    private Map<Long,Long>menuIds;
    private Map<Long,Long>areaIds;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<Long, Long> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(Map<Long, Long> deptIds) {
        this.deptIds = deptIds;
    }

    public Map<Long, Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Map<Long, Long> menuIds) {
        this.menuIds = menuIds;
    }

    public Map<Long, Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(Map<Long, Long> areaIds) {
        this.areaIds = areaIds;
    }
}
