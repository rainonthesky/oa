package com.oa.sys.controller;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.oa.sys.entity.Role;
import com.oa.sys.entity.User;
import com.oa.sys.entity.UserToRole;
import com.oa.sys.entity.vo.UserDto;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.service.IRoleService;
import com.oa.sys.service.IUserService;
import com.oa.sys.util.PageParam;
import com.oa.sys.util.PageUtils;
import com.oa.sys.util.UserUtils;
import com.oa.sys.vaildate.VaildatePassword;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关的控制器转发
 */
@Controller
@RequestMapping("/sys/user")
public class UserController {
    private static Logger logger=Logger.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @RequestMapping("/gotoUserInfo")
    public String gotoUserInfo(){
        return "sysmanage/user/userInfo";
    }
    @RequestMapping("/gotoChangePwd")
    public String gotoChangePwd(){
        return "sysmanage/user/changePwd";
    }

    @RequestMapping("/gotoUserList")
    public String gotoUserList(){
        return "sysmanage/user/userList";
    }

    @RequestMapping("/gotoUserEdit")
    public String gotoUserEdit(@ModelAttribute("editFlag")int editFlag, Long userId, Model model){
        //不管进入修改页面还是增加页面，都要将所有的角色列表查询出来
        List<Role>  roleList=roleService.getAllRoleList();
        model.addAttribute("roleList",roleList);
        //如果是进入修改页面，我们必须将用户的角色信息查询出来返回前端页面
        if(editFlag==2){
            UserVo userVo=userService.getUserInfoById(userId);
            model.addAttribute("userDto",userVo);
            List<UserToRole>userToRoleList =userService.getUserRoleByUserId(userId);
            if(userToRoleList!=null){
                Map<Long,Long>roleCheckMap=new HashMap<>();
                for(UserToRole userToRole:userToRoleList){
                    roleCheckMap.put(userToRole.getRoleId(),userToRole.getRoleId());
                }
                model.addAttribute("roleCheckMap",roleCheckMap);
            }
        }
        return"sysmanage/user/userEdit";
    }
    //分页查询
    @RequestMapping("/getUserList")
    public @ResponseBody Map<String,Object>getUserList(Long deptId,String userName,int pageNo,int pageSize){
        Map<String,Object>resultMap =new HashMap<>();
        //获取查询条件
        User user =new User();
        if(deptId!=null)user.setDeptId(deptId);
        if(userName!=null)user.setUserName(userName);
        //获取分页条件
        PageParam pageParam=new PageParam();
        pageParam.setPageNo(pageNo);
        pageParam.setPageSize(pageSize);
        //获取分页数据
        List<UserVo>userVoList=new ArrayList<>();
        PageInfo<UserVo>pageInfo=userService.getUserDtoList(user,pageParam);
        userVoList =pageInfo.getList();
        resultMap.put("userList",userVoList);
        //获取分页条
        String pageStr= PageUtils.pageStr(pageInfo,"userMgr.getUserListPage");
        resultMap.put("pageStr",pageStr);
        return resultMap;
    }
    //保存用户信息
    @RequestMapping("/saveUser")
    public @ResponseBody Map<String,Object>saveUser(@RequestBody UserDto userDto){
        Map<String,Object> resultMap=new HashMap<>();
        try{
            if(userDto!=null&&userDto.getUser()!=null&&userDto.getUser().getUserId()!=null){
              userService.updateUserDto(userDto);
                resultMap.put("result","修改用户成功");
            }else {
                //增加方法
                userService.addUser(userDto);
                resultMap.put("result","增加用户成功");
            }
        }catch(Exception e){
            resultMap.put("result","操作用户失败");
            logger.info("操作用户失败",e);
        }
        return resultMap;
    }

    //删除用户信息
    @RequestMapping("/delUser")
    public @ResponseBody Map<String,Object>delUser(Long userId){
        Map<String,Object>resultMap=new HashMap<>();
        try{
          if(userService.delUser(userId)){
              resultMap.put("result","删除用户成功");
            }else {
              resultMap.put("result","删除用户失败");
          }
        }catch (Exception e){
            resultMap.put("result","操作用户失败");
            logger.info("操作用户失败",e);

        }
        return resultMap;

    }

    @RequestMapping("/getUserInfoById")
    @ResponseBody
    public UserVo getUserInfoById(){
        Long userId=UserUtils.getCurrentUserId();
        List<UserToRole> userToRoleList=userService.getUserRoleByUserId(userId);
        UserVo userVo=userService.getUserInfoById(userId);
        for(UserToRole userToRole:userToRoleList){
            Role  role=roleService.getRoleById(userToRole.getRoleId());
            userVo.setRoleName(role.getName());
        }
        return userVo;
    }

    @RequestMapping("/saveSelfUserInfo")
    public @ResponseBody Map<String,Object> saveSelfUserInfo(@RequestBody User user){

        Map<String,Object> result=new HashMap<String, Object>();
            try{
                if(userService.updateUser(user)){
                    result.put("result","修改用户信息成功");
                }else {
                    result.put("result","修改用户信息失败");
                }

            }catch(Exception e){
                result.put("result","修改用户信息失败");
                logger.info(user.getLoginName());
                logger.info(user.getUserId());
                logger.error("修改用户信息失败",e);
            }
            return result;

    }

    @RequestMapping("/saveChangePwd")
    @ResponseBody
    public Map<String,Object> saveChangePwd(String oldPassword,String newPassword){
        logger.info("进入成功");
        Map<String,Object> resultMap =new HashMap<>();
        VaildatePassword vaildatePassword=new VaildatePassword();
        Long userId =1l;
        User user = userService.getUserById(userId);
        //校验密码是否有效
        boolean vaildateOldPsd= vaildatePassword.vaildatePsd(oldPassword,user.getPassword());
        if(!vaildateOldPsd){
            resultMap.put("result","修改密码失败，请输入正确的密码");

        }else{
            if(StringUtil.isNotEmpty(newPassword)){
                try{
                    if(userService.updateUserPsd(userId,newPassword)){
                        resultMap.put("result","修改密码成功");
                    }else {
                        resultMap.put("result","修改密码失败");
                    }
                }catch (Exception e){

                    resultMap.put("result","修改密码失败");
                    logger.error("修改密码失败",e);
                }

            }else {
                resultMap.put("result","新密码不能空");

            }

        }
                return resultMap;
    }

}

