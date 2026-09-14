package com.liangxuan.company.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liangxuan.company.entity.CompanyVote;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CompanyVoteMapper extends BaseMapper<CompanyVote> {
    @Select("SELECT id, company_id, user_id, vote_type FROM lx_company_vote "
            + "WHERE company_id = #{companyId} AND user_id = #{userId}")
    CompanyVote findByCompanyAndUser(@Param("companyId") Long companyId, @Param("userId") Long userId);

    @Select("SELECT id, company_id, user_id, vote_type FROM lx_company_vote "
            + "WHERE user_id = #{userId}")
    List<CompanyVote> findAllByUserId(@Param("userId") Long userId);
}