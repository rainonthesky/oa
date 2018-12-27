package com.oa.sys.service.impl;

import com.oa.sys.entity.Area;
import com.oa.sys.entity.Dept;
import com.oa.sys.entity.RoleToArea;
import com.oa.sys.entity.RoleToDept;
import com.oa.sys.mapper.AreaMapper;
import com.oa.sys.mapper.DeptMapper;
import com.oa.sys.mapper.RoleMapper;
import com.oa.sys.service.IAreaService;
import com.oa.sys.service.IDeptService;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DeptService implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Dept> getAllDeptList() {
        return deptMapper.getAllDeptList();
    }

    @Override
    public List<Dept> getDeptListByUserId(Long currentId) {
        return deptMapper.getDeptListByUserId(currentId);
    }

    @Override
    public Integer getChildCount(Long areaId) {
        return deptMapper.getChildCount(areaId);
    }

    @Override
    public Dept getDeptById(Long deptId) {
        return deptMapper.getDeptById(deptId);
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean addDept(Dept dept) {
        boolean flag=false;
        dept.setUpdateDate(new Date());
        if(UserUtils.getCurrentUserId()!=null){
            dept.setUpdateBy(UserUtils.getCurrentUserId().toString());
        }
        flag=deptMapper.addDept(dept);
        RoleToDept roleToDept=new RoleToDept();
        roleToDept.setRoleId(1l);
        roleToDept.setDeptId(dept.getId());
        flag = roleMapper.addRoleToDept(roleToDept);
        return flag;
    }
    //除了删除本身意外，还要删除角色部门表
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean delDept(Long deptId) {
        boolean flag=false;

        flag= roleMapper.delRoleAreaByDeptId(deptId);

        flag=deptMapper.delDept(deptId);
        return flag;
    }

    @Override
    public boolean updateDept(Dept dept) {
        return deptMapper.updateDept(dept);
    }
}
