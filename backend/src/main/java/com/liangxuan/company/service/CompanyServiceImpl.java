package com.liangxuan.company.service;

import com.liangxuan.company.dto.CompanyCreateRequest;
import com.liangxuan.company.dto.CompanyPageResponse;
import com.liangxuan.company.dto.CompanyVoteResponse;
import com.liangxuan.company.dto.UserVoteResponse;
import com.liangxuan.company.dto.VoteType;
import com.liangxuan.company.entity.Company;
import com.liangxuan.company.entity.CompanyVote;
import com.liangxuan.company.mapper.CompanyMapper;
import com.liangxuan.company.mapper.CompanyVoteMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liangxuan.common.BizException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyVoteMapper companyVoteMapper;

    public CompanyServiceImpl(CompanyMapper companyMapper, CompanyVoteMapper companyVoteMapper) {
        this.companyMapper = companyMapper;
        this.companyVoteMapper = companyVoteMapper;
    }

    @Override
    @Transactional
    public Company create(CompanyCreateRequest request, Long userId) {
        if (Integer.parseInt(request.endTime().substring(0, 2)) < Integer.parseInt(request.startTime().substring(0, 2))) {
            throw new IllegalArgumentException("下班时间不能早于上班时间");
        }
        Company company = new Company();
        company.setName(request.name().trim());
        company.setWebsite(request.website() == null ? "https://example.com" : request.website().trim());
        company.setProduct(request.product() == null ? "暂未填写产品介绍。" : request.product().trim());
        company.setStartTime(request.startTime());
        company.setEndTime(request.endTime());
        company.setWorkDays(request.workDays());
        company.setLikes(1);
        company.setDislikes(0);
        company.setSubmittedBy(userId);
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());
        companyMapper.insert(company);
        CompanyVote initialVote = new CompanyVote();
        initialVote.setCompanyId(company.getId());
        initialVote.setUserId(userId);
        initialVote.setVoteType(VoteType.LIKE);
        companyVoteMapper.insert(initialVote);
        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyPageResponse page(String keyword, long pageNum, long pageSize) {
        Page<Company> page = companyMapper.selectPageByKeyword(new Page<>(pageNum, pageSize), keyword);
        return new CompanyPageResponse(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyVoteResponse detail(Long companyId, Long userId) {
        Company company = getCompany(companyId);
        return new CompanyVoteResponse(company, findVoteType(companyId, userId));
    }

    @Override
    @Transactional
    public CompanyVoteResponse vote(Long companyId, VoteType voteType, Long userId) {
        Company company = getCompany(companyId);
        CompanyVote existingVote = companyVoteMapper.findByCompanyAndUser(companyId, userId);
        VoteType previousVote = existingVote == null ? null : existingVote.getVoteType();
        if (existingVote == null) {
            increment(company, voteType);
            CompanyVote vote = new CompanyVote();
            vote.setCompanyId(companyId);
            vote.setUserId(userId);
            vote.setVoteType(voteType);
            companyVoteMapper.insert(vote);
        } else if (existingVote.getVoteType() == voteType) {
            decrement(company, voteType);
            companyVoteMapper.deleteById(existingVote.getId());
        } else {
            decrement(company, existingVote.getVoteType());
            increment(company, voteType);
            existingVote.setVoteType(voteType);
            companyVoteMapper.updateById(existingVote);
        }
        company.setUpdatedAt(LocalDateTime.now());
        companyMapper.updateById(company);
        VoteType currentVote = voteType == previousVote ? null : voteType;
        return new CompanyVoteResponse(company, currentVote);
    }

    @Override
    @Transactional
    public CompanyVoteResponse cancelVote(Long companyId, Long userId) {
        Company company = getCompany(companyId);
        CompanyVote existingVote = companyVoteMapper.findByCompanyAndUser(companyId, userId);
        if (existingVote != null) {
            decrement(company, existingVote.getVoteType());
            companyVoteMapper.deleteById(existingVote.getId());
            company.setUpdatedAt(LocalDateTime.now());
            companyMapper.updateById(company);
        }
        return new CompanyVoteResponse(company, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserVoteResponse> listUserVotes(Long userId) {
        return companyVoteMapper.findAllByUserId(userId).stream()
                .map(vote -> new UserVoteResponse(vote.getCompanyId(), vote.getVoteType()))
                .toList();
    }

    private Company getCompany(Long companyId) {
        Company company = companyMapper.selectById(companyId);
        if (company == null) {
            throw new BizException(404, "公司不存在");
        }
        return company;
    }

    private VoteType findVoteType(Long companyId, Long userId) {
        if (userId == null) return null;
        CompanyVote vote = companyVoteMapper.findByCompanyAndUser(companyId, userId);
        return vote == null ? null : vote.getVoteType();
    }

    private void increment(Company company, VoteType voteType) {
        if (voteType == VoteType.LIKE) company.setLikes(company.getLikes() + 1);
        else company.setDislikes(company.getDislikes() + 1);
    }

    private void decrement(Company company, VoteType voteType) {
        if (voteType == VoteType.LIKE) company.setLikes(Math.max(0, company.getLikes() - 1));
        else company.setDislikes(Math.max(0, company.getDislikes() - 1));
    }
}
