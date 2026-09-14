package com.liangxuan.company.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangxuan.company.entity.Company;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
	Page<Company> selectPageByKeyword(Page<Company> page, @Param("keyword") String keyword);
}
