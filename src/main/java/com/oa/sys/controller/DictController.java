package com.oa.sys.controller;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.oa.sys.entity.Dict;
import com.oa.sys.service.IDictService;
import com.oa.sys.util.PageParam;
import com.oa.sys.util.PageUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关的控制器转发
 */
@Controller
@RequestMapping("/sys/dict")
public class DictController {
    private static Logger logger=Logger.getLogger(DictController.class);
    @Autowired
    private IDictService dictService;

    //进入字典的查询功能
    @RequestMapping("/gotoDictList")
    public String gotoDictList(Model model){
        List<String> dictTypeList =dictService.getAllDictType();
        model.addAttribute("dictTypeList",dictTypeList);
        return "sysmanage/dict/dictList";

    }
    // 查询字典列表
    @RequestMapping("/getDictList")
    @ResponseBody
    public List<Dict>getDictList(String type,String description){
        Dict dict=new Dict();
        if(StringUtil.isNotEmpty(type))dict.setType(type);
        if(StringUtil.isNotEmpty(description))dict.setDescription(description);
        List<Dict>dictList=dictService.getDictList(dict);
        return dictList;
    }


    // 分页查询
    @RequestMapping("/getDictListPages")
    @ResponseBody
    public Map<String,Object>getDictListPages(String type,String description,int pageNo,int pageSize) {
        Map<String,Object> result=new HashMap<>();
        //获取查询条件
        Dict dict=new Dict();
        if(StringUtil.isNotEmpty(type))dict.setType(type);
        if(StringUtil.isNotEmpty(description))dict.setDescription(description);
        // 构造分页对象
        PageParam pageParam=new PageParam();
        pageParam.setPageNo(pageNo);
        pageParam.setPageSize(pageSize);
        //获取返回的分页数据
        PageInfo<Dict> pageInfo=dictService.getDictListPage(dict,pageParam);
        List<Dict>dictList=pageInfo.getList();
        result.put("dictList",dictList);
        //获取返回的分页条
        String pageStr = PageUtils.pageStr(pageInfo,"dictMgr.getDictListPage");
        result.put("pageStr",pageStr);
            return  result;
    }
    //进入字典编辑功能

    @RequestMapping("/gotoDictEdit")
    public String gotoDictEidt(@ModelAttribute("editFlag") int editFlag,Long dictId,Model model){

        if (editFlag==2){
            Dict dict=dictService.getDictById(dictId);
            model.addAttribute("dict",dict);
        }
        return "sysmanage/dict/dictEdit";
    }
    // 修改字典和增加字典
    @RequestMapping("/saveDict")
    public @ResponseBody Map<String,Object>saveDict(@RequestBody Dict dict){
        Map<String,Object>resultMap=new HashMap<>();
        try{
            if(dict!=null&&dict.getId()!=null){
                //修改字典
                dictService.updateDict(dict);
                resultMap.put("result","修改字典成功");
            }else {
                //增加字典
                logger.info("添加");
                dictService.addDict(dict);
                resultMap.put("result","增加字典成功");
            }
        }catch (Exception e){
            resultMap.put("result","修改字典失败");
            logger.error("修改字典失败",e);
        }
        return resultMap;

    }
    // 字典删除功能
    @RequestMapping("/delDict")
    @ResponseBody
    public Map<String,Object>delDict(Long dictId){
        Map<String,Object>result=new HashMap<>();
        try{
            if(dictService.delDict(dictId)){
                result.put("result","删除字典成功");
            }
        }catch (Exception e){
            result.put("result","删除字典失败");
            logger.error("删除字典失败",e);
        }
        return result;
    }




}

