package com.oa.sys.controller;
import com.oa.sys.entity.Dept;
import com.oa.sys.service.IDeptService;
import com.oa.sys.service.impl.DeptService;
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
@RequestMapping("/sys/dept")
public class DeptController {
    private static Logger logger=Logger.getLogger(DeptController.class);
    @Autowired
    private IDeptService deptService;
    //进入菜单查询列表功能
    @RequestMapping("/gotoDeptList")
    public String gotoDeptList(Model model){
        //进入功能初始化
        List<Dept>deptList=deptService.getDeptListByUserId(UserUtils.getCurrentUserId());
//        List<Dept> deptList=deptService.getAllDeptList();
        List<Dept> sortDeptList=new ArrayList<Dept>();
        TreeUtils.sortTreeList(sortDeptList,deptList,0l);
        model.addAttribute("deptList",sortDeptList);
        return "sysmanage/dept/deptList";

    }
    //进入字典编辑功能
    //进入编辑功能的时候，要根据id获取编辑对象的明细记录
    @RequestMapping("/gotoDeptEdit")
    public String gotoDeptEdit(@ModelAttribute("editFlag") int editFlag,Long deptId,Long parentId,Model model){
        logger.info("进入修改");
        //如果editFlag ==2则进入修改页面，我们 需要查询带修改的明细
        if(editFlag==2){
           Dept dept =deptService.getDeptById(deptId);
           model.addAttribute("dept",dept);
        }
        if(editFlag ==1){//进入增加界面
            if(parentId!=null){
                Dept parentDept=deptService.getDeptById(parentId);
                Dept dept =new Dept();
                dept.setParentId(parentDept.getId());
                dept.setParentName(parentDept.getName());
                model.addAttribute("dept",dept);
            }

        }
        return "sysmanage/dept/deptEdit";

   }
   //获取所有树形结构 部门节点信息
   @RequestMapping("/getParentDeptTreeData")
    public @ResponseBody List<TreeDto> getParentDeptTreeData(Long deptId){
        List<TreeDto> treeDtoList =new ArrayList<>();
        List<Dept> deptList =deptService.getAllDeptList();
            for (Dept dept : deptList){
                TreeDto treeDto=new TreeDto();
                treeDto.setId(dept.getId());
                treeDto.setName(dept.getName());
                treeDto.setParentId(dept.getParentId());
                treeDtoList.add(treeDto);
            }

        if(deptId!=null){
            Map<Long,TreeDto>childrenMap=new HashMap<Long,TreeDto>() ;
            //1:把他的儿子，孙子全部找出来
            TreeUtils.getAllChildrenMap(childrenMap,treeDtoList,deptId);
            Iterator<TreeDto> treeDtoIterator=treeDtoList.iterator();
            while(treeDtoIterator.hasNext()){
                TreeDto treeDto =treeDtoIterator.next();
                //如果子节点列表中存在这个对象，则删除
                if(childrenMap.get(treeDto.getId())!=null){
                    treeDtoIterator.remove();
                }
                //删除本身
                if(treeDto.getId().equals(deptId)){
                    treeDtoIterator.remove();
                }
            }

        }
        return treeDtoList;
    }
    @RequestMapping("/saveDept")
    public  @ResponseBody Map<String,Object>saveDept(@RequestBody Dept dept){
        Map<String,Object> result =new HashMap<>();
        try{
            if(dept!=null&&dept.getId()!=null){
                deptService.updateDept(dept);
                result.put("result","修改部门成功");
            }else {
                deptService.addDept(dept);
                logger.info("添加成功");
                result.put("result","添加部门成功");
            }
        }catch (Exception e){
            logger.error("操作部门失败",e);
            result.put("result","操作部门信息失败");
        }
        return result;
    }
    @RequestMapping("/delDept")
    public @ResponseBody Map<String,Object> delDept(Long deptId){
        Map<String,Object> result =new HashMap<>();
        if(deptService.getChildCount(deptId).intValue()>0){
            result.put("result", "此菜单下面还有子部门,请确定删除所有的部门单后再进行此操作");
            return result;
        }
        try{
        if(deptService.delDept(deptId)){
            result.put("result","删除部门成功");
        }else{
            result.put("result","删除部门失败");
        }
        }catch (Exception e){
            logger.error("删除部门失败",e);
            result.put("result","删除部门失败");

        }
        return result;

    }

    //获取所有部门树
    @RequestMapping("/getAllDeptList")
    public @ResponseBody List<TreeDto>getAllDeptList(){
        List<TreeDto>treeDtoList=new ArrayList<>();
        List<Dept>deptList=deptService.getAllDeptList();
        TreeDto tree;
        for(Dept dept:deptList){
            tree=new TreeDto();
            tree.setId(dept.getId());tree.setName(dept.getName());tree.setParentId(dept.getParentId());
            treeDtoList.add(tree);
        }

    return treeDtoList;

    }






}

