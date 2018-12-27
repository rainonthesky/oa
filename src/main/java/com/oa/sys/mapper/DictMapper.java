package com.oa.sys.mapper;

import com.oa.sys.entity.Dict;
import com.oa.sys.entity.User;
import com.oa.sys.entity.vo.UserVo;

import java.util.List;

/**
 * 用户增删改查以及登录验证mapper代理接口
 * @author chenyaping
 */

public interface DictMapper {
        /**
         * 获取所有的字典类型
         * @return
         */
       public  List<String> getAllDictType();

        /**
         * 根据条件查询字典列表
         * @param dict
         * @return
         */
       public List<Dict> getDictList(Dict dict);

        /**
         *
         * 修改字典
         * @param dict
         * @return
         */
       public boolean updateDict(Dict dict);

        /**
         * 增加字典
         * @param dict
         * @return
         */

       public boolean addDict(Dict dict);
     /**
     *删除字典中的用户
     * @param dictId
     * @return
     */

        public boolean delDict(Long dictId);

     /**
      * 通过id获取字典明细
     * @param dictId
      * @return
     */
     public Dict getDictById(Long dictId);

}

