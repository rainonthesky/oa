package com.oa.sys.service;

import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.Dict;
import com.oa.sys.entity.User;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.util.PageParam;

import java.util.List;

/**
 * 字典相关业务层接口代码
 */
public interface IDictService {
    /**
     * 获取所有字典的类型
     * @return
     */
    public List<String> getAllDictType();
    /**
     * 根据条件查询字典列表
     * @param dict
     * @return
     */
    public List<Dict> getDictList(Dict dict);

    /**
     * 通过id获取字典明细
     * @param dictId
     * @return
     */
    public Dict getDictById(Long dictId);

    /**
     * 更新字典的信息
     * @param dict
     * @return
     */

    public boolean updateDict(Dict dict);

    /**
     * 增加字典中的信息
     * @param dict
     * @return
     */
    public boolean addDict(Dict dict);

    /**
     * 删除字典中的信息
     * @param id
     * @return
     */

    public boolean delDict(Long id);

    /**
     * 通过分页查询字典列表
     * @param dict
     * @param pageParam
     * @return
     */

    public PageInfo<Dict>getDictListPage(Dict dict,PageParam pageParam);
}
