package com.oa.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oa.sys.entity.Dict;
import com.oa.sys.entity.User;
import com.oa.sys.entity.vo.UserVo;
import com.oa.sys.mapper.DictMapper;
import com.oa.sys.mapper.UserMapper;
import com.oa.sys.service.IDictService;
import com.oa.sys.service.IUserService;
import com.oa.sys.util.PageParam;
import com.oa.sys.util.UserUtils;
import com.oa.sys.vaildate.VaildatePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DictService implements IDictService {
    @Autowired
    private DictMapper dictMapper;


    @Override
    public List<String> getAllDictType() {
        return dictMapper.getAllDictType();
    }

    @Override
    public List<Dict> getDictList(Dict dict) {
        return dictMapper.getDictList(dict);
    }

    @Override
    public Dict getDictById(Long dictId) {
        return dictMapper.getDictById(dictId);
    }
    @Override
    public boolean updateDict(Dict dict) {
        dict.setUpdateDate(new Date());
        if(UserUtils.getCurrentUserId()!=null){
            dict.setUpdateBy(UserUtils.getCurrentUserId().toString());
        }
        return dictMapper.updateDict(dict);
    }

    @Override
    public boolean addDict(Dict dict) {
        dict.setUpdateDate(new Date());
        if(UserUtils.getCurrentUserId()!=null){
            dict.setUpdateBy(UserUtils.getCurrentUserId().toString());
        }
        return dictMapper.addDict(dict);
    }

    @Override
    public boolean delDict(Long dictId) {
        return dictMapper.delDict(dictId);
    }

    @Override
    public PageInfo<Dict> getDictListPage(Dict dict,PageParam pageParam) {
        //开启分页插件
        PageHelper.startPage(pageParam.getPageNo(),pageParam.getPageSize());
        //查询出所有的
        List<Dict> dictList =dictMapper.getDictList(dict);
        PageInfo<Dict>pageInfo=new PageInfo<>(dictList);
        return pageInfo;
    }
}
