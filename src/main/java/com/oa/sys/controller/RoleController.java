package com.oa.sys.controller;


import com.oa.sys.entity.*;
import com.oa.sys.entity.vo.RoleDto;
import com.oa.sys.service.IAreaService;
import com.oa.sys.service.IDeptService;
import com.oa.sys.service.IMenuService;
import com.oa.sys.service.IRoleService;
import com.oa.sys.service.impl.MenuService;
import com.oa.sys.service.impl.RoleService;
import com.oa.sys.util.TreeDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 用户相关的控制器转发
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {
    private static Logger logger=Logger.getLogger(RoleController.class);
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IAreaService areaService;

    //进入区域查询列表功能
    @RequestMapping("/gotoRoleList")
    public String gotoRoleList(Model model){
        List<Role> roleList=new ArrayList<>();
        roleList = roleService.getAllRoleList();
        model.addAttribute("roleList",roleList);
        return "sysmanage/role/roleList";
    }

    @RequestMapping("/gotoRoleEdit")
    public String gotoRoleEdit(@ModelAttribute("editFlag")int editFlag,Long roleId,Model model){

        List<Menu> menuList=menuService.getAllMenuList();
        List<Dept> deptList= deptService.getAllDeptList();
        List<Area>areaList=areaService.getAllAreaList();
        model.addAttribute("menuList",menuList);
        model.addAttribute("deptList",deptList);
        model.addAttribute("areaList",areaList);
        if(editFlag==2){
            List<RoleToMenu>roleMenuList=roleService.getMenuListByRoleId(roleId);
            List<RoleToDept>roleDeptList=roleService.getDeptListByRoleId(roleId);
            List<RoleToArea>roleAreaList=roleService.getAreaListByRoleId(roleId);
            model.addAttribute("roleMenuList",roleMenuList);
            model.addAttribute("roleDeptList",roleDeptList);
            model.addAttribute("roleAreaList",roleAreaList);
            Role role =roleService.getRoleById(roleId);
            model.addAttribute("role",role);
        }
        return "sysmanage/role/roleEdit";
    }
    //保存
    @RequestMapping("/saveRole")
    public @ResponseBody Map<String,Object>saveRole(@RequestBody RoleDto roleDto){
        Map<String,Object>resultMap=new HashMap<>();
        try{
            if(roleDto!=null&&roleDto.getRole()!=null&&roleDto.getRole().getId()!=null){
                roleService.updateRole(roleDto);
                resultMap.put("result","修改角色成功");

            }else{
                roleService.addRole(roleDto);
                resultMap.put("result","增加角色成功");
           }

        }catch (Exception e){
            logger.error("操作角色失败",e);
            resultMap.put("result","操作角色失败");

        }
        return resultMap;
    }

    @RequestMapping("/delRole")
    public @ResponseBody Map<String,Object>delRole(Long roleId){
        Map<String,Object>resultMap=new HashMap<>();

        try{
            roleService.delRole(roleId);
            resultMap.put("result","删除角色信息成功");
        }catch(Exception e){
            logger.error("删除角色失败",e);
            resultMap.put("result","删除角色信息失败");
        }
        return resultMap;
    }







}

