package com.oa.sys.controller;


import com.oa.sys.entity.Menu;
import com.oa.sys.service.IMenuService;
import com.oa.sys.service.impl.MenuService;
import com.oa.sys.util.TreeDto;
import com.oa.sys.util.TreeUtils;
import com.oa.sys.util.UserUtils;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户相关的控制器转发
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController {
    private static Logger logger=Logger.getLogger(MenuController.class);
    @Autowired
    private IMenuService menuService;
    //进入菜单查询列表功能
    @RequestMapping("/gotoMenuList")
    public String gotoMenuList(Model model){
        //进入功能初始化
         List<Menu> menuList=menuService.getMenuListById(UserUtils.getCurrentUserId());
//       List<Menu> menuList=menuService.getAllMenuList();
        List<Menu> sortMenuList=new ArrayList<Menu>();
        TreeUtils.sortTreeList(sortMenuList,menuList,0l);
        model.addAttribute("menuList",sortMenuList);
        return "sysmanage/menu/menuList";

    }
    //进入字典编辑功能
    //进入编辑功能的时候，要根据id获取编辑对象的明细记录
    @RequestMapping("/gotoMenuEdit")
    public String gotoMenuEdit(@ModelAttribute("editFlag") int editFlag,Long menuId,Long parentId,Model model){
        logger.info("进入修改");
        //如果editFlag ==2则进入修改页面，我们 需要查询带修改的明细
        if(editFlag==2){
           Menu menu =menuService.getMenuById(menuId);
           model.addAttribute("menu",menu);
        }
        if(editFlag ==1){//进入增加界面
            if(parentId!=null){
                Menu parentMenu =menuService.getMenuById(parentId);
                Menu menu =new Menu();
                menu.setParentId(parentMenu.getId());
                menu.setParentName(parentMenu.getName());
                model.addAttribute("menu",menu);

            }

        }
        return "sysmanage/menu/menuEdit";

   }
   //获取所有树形结构 菜单节点信息
   @RequestMapping("/getParentMenuTreeData")
    public @ResponseBody List<TreeDto> getParentMenuTreeData(Long menuId){
        List<TreeDto> treeDtoList =new ArrayList<>();
        List<Menu> menuList =menuService.getAllMenuList();
            for (Menu menu:menuList){
                TreeDto treeDto=new TreeDto();
                treeDto.setId(menu.getId());
                treeDto.setName(menu.getName());
                treeDto.setParentId(menu.getParentId());
                treeDtoList.add(treeDto);
            }

        if(menuId!=null){
            Map<Long,TreeDto>childrenMap=new HashMap<Long,TreeDto>() ;
            //1:把他的儿子，孙子全部找出来
            TreeUtils.getAllChildrenMap(childrenMap,treeDtoList,menuId);
            Iterator<TreeDto> treeDtoIterator=treeDtoList.iterator();
            while(treeDtoIterator.hasNext()){
                TreeDto treeDto =treeDtoIterator.next();
                //如果子节点列表中存在这个对象，则删除
                if(childrenMap.get(treeDto.getId())!=null){
                    treeDtoIterator.remove();
                }
                if(treeDto.getId().equals(menuId)){
                    treeDtoIterator.remove();
                }
            }

        }
        return treeDtoList;
    }
    @RequestMapping("/saveMenu")
    public  @ResponseBody Map<String,Object>saveMenu(@RequestBody Menu menu){
        Map<String,Object> result =new HashMap<>();
        try{
            if(menu!=null&&menu.getId()!=null){
                menuService.updateMenu(menu);
                result.put("result","修改菜单成功");
            }else {
                menuService.addMenu(menu);
                logger.info("添加成功");
                result.put("result","添加菜单成功");
            }
        }catch (Exception e){
            logger.error("操作菜单失败",e);
            result.put("result","操作菜单信息失败");
        }
        return result;
    }
    @RequestMapping("/delMenu")
    public @ResponseBody Map<String,Object> delMenu(Long menuId){
        Map<String,Object> result =new HashMap<>();
        if(menuService.getChildCount(menuId).intValue()>0){
            result.put("result", "此菜单下面还有子菜单,请确定删除所有的子菜单后再进行此操作");
            return result;
        }
        try{
        if(menuService.delMenu(menuId)){
            result.put("result","删除菜单成功");
        }else{
            result.put("result","删除菜单失败");
        }


        }catch (Exception e){
            logger.error("删除菜单失败",e);
            result.put("result","删除菜单失败");

        }
        return result;

    }






}

