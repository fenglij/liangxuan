package com.liangxuan.company.dto;

/**
 * 用户点赞/点踩记录响应
 *
 * @param companyId 公司ID
 * @param voteType  投票类型（LIKE 或 DISLIKE）
 */
public record UserVoteResponse(Long companyId, VoteType voteType) {
}
