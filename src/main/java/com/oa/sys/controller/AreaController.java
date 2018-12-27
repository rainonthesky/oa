package com.oa.sys.controller;


import com.oa.sys.entity.Area;
import com.oa.sys.service.IAreaService;
import com.oa.sys.service.impl.AreaService;
import com.oa.sys.util.TreeDto;
import com.oa.sys.util.TreeUtils;
import com.oa.sys.util.UserUtils;
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
@RequestMapping("/sys/area")
public class AreaController {
    private static Logger logger=Logger.getLogger(AreaController.class);
    @Autowired
    private IAreaService areaService;
    //进入菜单查询列表功能
    @RequestMapping("/gotoAreaList")
    public String gotoAreaList(Model model){
        //进入功能初始化
        List<Area> areaList=areaService.getAreaListById(UserUtils.getCurrentUserId());
      //  List<Area> areaList=areaService.getAllAreaList();
        List<Area> sortAreaList=new ArrayList<Area>();
        TreeUtils.sortTreeList(sortAreaList,areaList,0l);
        model.addAttribute("areaList",sortAreaList);
        return "sysmanage/area/areaList";

    }

    //进入字典编辑功能
    //进入编辑功能的时候，要根据id获取编辑对象的明细记录
    @RequestMapping("/gotoAreaEdit")
    public String gotoAreaEdit(@ModelAttribute("editFlag") int editFlag,Long areaId,Long parentId,Model model){
        logger.info("进入修改");
        //如果editFlag ==2则进入修改页面，我们 需要查询带修改的明细
        if(editFlag==2){
           Area area =areaService.getAreaById(areaId);
           model.addAttribute("area",area);
        }
        if(editFlag ==1){//进入增加界面
            if(parentId!=null){
                Area parentArea=areaService.getAreaById(parentId);
                Area area =new Area();
                area.setParentId(parentArea.getId());
                area.setParentName(parentArea.getName());
                model.addAttribute("area",area);

            }

        }
        return "sysmanage/area/areaEdit";

   }
   //获取所有树形结构 区域节点信息
   @RequestMapping("/getParentAreaTreeData")
    public @ResponseBody List<TreeDto> getParentAreaTreeData(Long areaId){
        List<TreeDto> treeDtoList =new ArrayList<>();
        List<Area> areaList =areaService.getAllAreaList();
            for (Area area : areaList){
                TreeDto treeDto=new TreeDto();
                treeDto.setId(area.getId());
                treeDto.setName(area.getName());
                treeDto.setParentId(area.getParentId());
                treeDtoList.add(treeDto);
            }

        if(areaId!=null){
            Map<Long,TreeDto>childrenMap=new HashMap<Long,TreeDto>() ;
            //1:把他的儿子，孙子全部找出来
            TreeUtils.getAllChildrenMap(childrenMap,treeDtoList,areaId);
            Iterator<TreeDto> treeDtoIterator=treeDtoList.iterator();
            while(treeDtoIterator.hasNext()){
                TreeDto treeDto =treeDtoIterator.next();
                //如果子节点列表中存在这个对象，则删除
                if(childrenMap.get(treeDto.getId())!=null){
                    treeDtoIterator.remove();
                }
                if(treeDto.getId().equals(areaId)){
                    treeDtoIterator.remove();
                }
            }

        }
        return treeDtoList;
    }
    @RequestMapping("/saveArea")
    public  @ResponseBody Map<String,Object>saveArea(@RequestBody Area area){
        Map<String,Object> result =new HashMap<>();
        try{
            if(area!=null&&area.getId()!=null){
                areaService.updateArea(area);
                result.put("result","修改区域成功");
            }else {
                areaService.addArea(area);
                logger.info("添加成功");
                result.put("result","添加区域成功");
            }
        }catch (Exception e){
            logger.error("操作区域失败",e);
            result.put("result","操作区域信息失败");
        }
        return result;
    }
    @RequestMapping("/delArea")
    public @ResponseBody Map<String,Object> delArea(Long areaId){
        Map<String,Object> result =new HashMap<>();
        if(areaService.getChildCount(areaId).intValue()>0){

            result.put("result", "此菜单下面还有子区域,请确定删除所有的区域单后再进行此操作");
            return result;
        }else{
        try{
        if(areaService.delArea(areaId)){
            result.put("result","删除区域成功");
        }else{
            result.put("result","删除区域失败");
        }


        }catch (Exception e){
            logger.error("删除区域失败",e);
            result.put("result","删除区域失败");

        }
        }
        return result;

    }






}

