package com.oa.sys.service;

import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.User;
import com.oa.sys.entity.UserToRole;
import com.oa.sys.entity.vo.UserDto;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.util.PageParam;

import java.util.List;

/**
 * 用户业务处理的接口
 */
public interface IUserService {
    /**
     *
     * 登录验证
     * @param loginName
     * @param password
     * @return
     */
    public User loginUser(String loginName, String password);

    /**
     * 获取用户列表
     * @param userId
     * @return
     */
    public User getUserById(Long userId);

    /**
     * 按条件分页查询用户列表
     * @param user
     * @param pageParam
     * @return
     */
    public PageInfo<UserVo> getUserDtoList(User user, PageParam pageParam);



    /**
     * 修改用户的密码
     * @param userId
     * @param password
     * @return
     */
    public boolean updateUserPsd(Long userId,String password);


    /**
     * 根据id获取用户明细 包含部门名称以及角色列表
     * @return
     */
    public UserVo getUserInfoById(Long userId);

    /**
     * 更新用户个人信息
     * @param user
     * @return
     */
    public boolean updateUser(User user);

    /**
     * 查询该用户拥有的角色
     * @param userId
     * @return
     */
    public List<UserToRole>getUserRoleByUserId(Long userId);

    /**
     * 增加用户信息
     * @param userDto
     * @return
     */
    public boolean addUser(UserDto userDto);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    public boolean delUser(Long userId);

    /**
     *修改用户以及角色对应关系
     * @return
     */
    public boolean updateUserDto(UserDto userDto);






}
