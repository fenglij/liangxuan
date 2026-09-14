package com.liangxuan.company.service;

import com.liangxuan.company.dto.CompanyCreateRequest;
import com.liangxuan.company.dto.CompanyPageResponse;
import com.liangxuan.company.dto.CompanyVoteResponse;
import com.liangxuan.company.dto.UserVoteResponse;
import com.liangxuan.company.entity.Company;
import com.liangxuan.company.dto.VoteType;
import java.util.List;
public interface CompanyService {
    Company create(CompanyCreateRequest request, Long userId);
    CompanyPageResponse page(String keyword, long pageNum, long pageSize);
    CompanyVoteResponse detail(Long companyId, Long userId);
    CompanyVoteResponse vote(Long companyId, VoteType voteType, Long userId);
    CompanyVoteResponse cancelVote(Long companyId, Long userId);
    List<UserVoteResponse> listUserVotes(Long userId);
}
