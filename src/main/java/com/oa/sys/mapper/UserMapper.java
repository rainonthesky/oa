package com.oa.sys.mapper;

import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.User;
import com.oa.sys.entity.UserToRole;
import com.oa.sys.entity.vo.UserDto;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.util.PageParam;

import java.util.List;
/**
 * 用户增删改查以及登录验证mapper代理接口
 * @author chenyaping
 */

public interface UserMapper {
        /**
         * 根据条件查询用户
         * @param user
         * @return
         */
        public List<User> getUserList(User user);

        /**
         * 根据用户ID获取用户的信息
         * @param userId
         * @return
         */
        public User getUserById(Long userId);

        /**
         * 根据id获取用户明细 包含部门名称以及角色列表
         * @return
         */
        public UserVo getUserInfo(Long userId);

        /**
         * 查询该用户拥有的角色
         * @param userId
         * @return
         */
        public List<UserToRole>getUserRoleByUserId(Long userId);

        /**
         * 按条件分页查询用户列表
         * @param user
         * @return
         */
        public List<UserVo> getUserVoList(User user);
        /**
         * 批量添加用户角色表
         * @param userToRole
         * @return
         */
        public boolean addUserRoleBatch(List<UserToRole> userToRole);


        /**
         * 增加用户信息
         * @param user
         * @return
         */
        public boolean addUser(User user);

        /**
         * 修改用户的密码
         * @param userId
         * @param password
         * @return
         */
        public boolean updateUserPsd(Long userId,String password);

        /**
         * 更新用户个人信息
         * @param user
         * @return
         */
        public boolean updateUser(User user);

        /**
         * 删除用户信息
         * @param userId
         * @return
         */
        public boolean delUser(Long userId);


        /**
         * 删除用户和角色关联表的数据
         * @param userId
         * @return
         */
        public boolean delUserRoleByUserId(Long userId);




}

