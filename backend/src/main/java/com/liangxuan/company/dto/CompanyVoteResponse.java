package com.liangxuan.company.dto;

import com.liangxuan.company.entity.Company;

public record CompanyVoteResponse(Company company, VoteType myVote) {
}