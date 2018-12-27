package com.oa.sys.service.impl;

import com.oa.sys.entity.Area;
import com.oa.sys.entity.RoleToArea;
import com.oa.sys.mapper.AreaMapper;
import com.oa.sys.mapper.RoleMapper;
import com.oa.sys.service.IAreaService;
import com.oa.sys.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class AreaService implements IAreaService {
    @Autowired
    private AreaMapper areaMapper;


   @Autowired
   private RoleMapper roleMapper;
    @Override
    public List<Area> getAllAreaList() {
        return areaMapper.getAllAreaList();
    }

    @Override
    public List<Area> getAreaListById(Long currentId) {
        return areaMapper.getAreaListById(currentId);
    }

    @Override
    public Integer getChildCount(Long areaId) {
        return areaMapper.getChildCount(areaId);
    }

    @Override
    public Area getAreaById(Long areaId) {
        return areaMapper.getAreaById(areaId);
    }
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean addArea(Area area) {
        boolean flag = false;
        area.setUpdateDate(new Date());
        if (UserUtils.getCurrentUserId() != null) {
            area.setUpdateBy(UserUtils.getCurrentUserId().toString());

        }
        flag=areaMapper.addArea(area);
        RoleToArea roleArea=new RoleToArea();
        roleArea.setRoleId(1l);
        roleArea.setAreaId(area.getId());
        flag = roleMapper.addRoleToArea(roleArea);
        return flag;
    }
    //除了删除本身意外，还要删除角色区域表
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean delArea(Long areaId) {
        boolean flag=false;
        flag= roleMapper.delRoleAreaByAreaId(areaId);
        flag= areaMapper.delArea(areaId);
        return flag;
    }

    @Override
    public boolean updateArea(Area area) {
        return areaMapper.updateArea(area);
    }
}
