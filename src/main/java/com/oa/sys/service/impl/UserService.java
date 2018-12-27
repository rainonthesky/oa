package com.oa.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.User;
import com.oa.sys.entity.UserToRole;
import com.oa.sys.entity.vo.UserDto;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.mapper.UserMapper;
import com.oa.sys.service.IUserService;
import com.oa.sys.util.PageParam;
import com.oa.sys.util.UserUtils;
import com.oa.sys.vaildate.VaildatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User loginUser(String loginName, String password) {
       User user =new User();user.setLoginName(loginName);
        List<User> userList =userMapper.getUserList(user);
        if(userList.isEmpty()){
            return null;
        }else {
            VaildatePassword vaildatePassword=new VaildatePassword();
            // 进行密码的比较
           String encryptPsd= userList.get(0).getPassword();
            if(vaildatePassword.vaildatePsd(password,encryptPsd)){
                return userList.get(0);
            }else {
                return null;
            }

        }

    }

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public PageInfo<UserVo> getUserDtoList(User user, PageParam pageParam) {
        PageHelper.startPage(pageParam.getPageNo(),pageParam.getPageSize());
        List<UserVo>userVoList=userMapper.getUserVoList(user);
        PageInfo<UserVo> pageInfo=new PageInfo<>(userVoList);
        return pageInfo;
    }

    @Override
    public boolean updateUserPsd(Long userId, String password) {
        VaildatePassword vaildatePassword=new VaildatePassword();
        String painPassword=vaildatePassword.encyptPassword(password);
        return userMapper.updateUserPsd(userId,painPassword);

    }

    @Override
    public UserVo getUserInfoById(Long userId) {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public List<UserToRole> getUserRoleByUserId(Long userId) {
        return userMapper.getUserRoleByUserId(userId);
    }

    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public boolean addUser(UserDto userDto) {
        VaildatePassword vaildatePassword=new VaildatePassword();
        boolean flag =false;
        User user=userDto.getUser();
        user.setUpdateBy(UserUtils.getCurrentUserId().toString());
        user.setUpdateDate(new Date());
        //每次新增用户，密码默认为1234
        String password="1234";
        String encryptPassword=vaildatePassword.encyptPassword(password);
        user.setPassword(encryptPassword);
        flag =this.userMapper.addUser(user);
        return flag;
    }

    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public boolean delUser(Long userId) {
        boolean flag =false;
        flag=userMapper.delUserRoleByUserId(userId);
        flag=userMapper.delUser(userId);
        return flag;
    }

    @Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public boolean updateUserDto(UserDto userDto) {
        boolean flag=false;
        User user=userDto.getUser();
        user.setUpdateDate(new Date());
        user.setUpdateBy(UserUtils.getCurrentUserId().toString());

        Long userId=user.getUserId();
        flag = userMapper.delUserRoleByUserId(userId);
        //插入用户角色信息
        List<UserToRole> userToRoleList=new ArrayList<>();
        UserToRole userToRole;
        for(Long roleId: userDto.getRoleIds().values()){
            userToRole =new UserToRole();
            userToRole.setRoleId(roleId);
            userToRole.setUserId(userId);
            userToRoleList.add(userToRole);
        }
        flag=userMapper.addUserRoleBatch(userToRoleList);
        flag =this.updateUser(user);
        return flag;
    }

}
