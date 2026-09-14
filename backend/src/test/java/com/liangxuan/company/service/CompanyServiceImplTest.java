package com.liangxuan.company.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.liangxuan.company.dto.CompanyVoteResponse;
import com.liangxuan.company.dto.VoteType;
import com.liangxuan.company.entity.Company;
import com.liangxuan.company.entity.CompanyVote;
import com.liangxuan.company.mapper.CompanyMapper;
import com.liangxuan.company.mapper.CompanyVoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {
    private static final Long COMPANY_ID = 1L;
    private static final Long USER_ID = 2L;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private CompanyVoteMapper companyVoteMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void addsLikeForUserWithoutExistingVote() {
        Company company = company(3, 1);
        when(companyMapper.selectById(COMPANY_ID)).thenReturn(company);
        when(companyVoteMapper.findByCompanyAndUser(COMPANY_ID, USER_ID)).thenReturn(null);

        CompanyVoteResponse response = companyService.vote(COMPANY_ID, VoteType.LIKE, USER_ID);

        assertThat(response.company().getLikes()).isEqualTo(4);
        assertThat(response.myVote()).isEqualTo(VoteType.LIKE);
        verify(companyVoteMapper).insert(any(CompanyVote.class));
        verify(companyMapper).updateById(company);
    }

    @Test
    void removesExistingLikeWhenUserVotesLikeAgain() {
        Company company = company(3, 1);
        CompanyVote existingVote = vote(VoteType.LIKE);
        when(companyMapper.selectById(COMPANY_ID)).thenReturn(company);
        when(companyVoteMapper.findByCompanyAndUser(COMPANY_ID, USER_ID)).thenReturn(existingVote);

        CompanyVoteResponse response = companyService.vote(COMPANY_ID, VoteType.LIKE, USER_ID);

        assertThat(response.company().getLikes()).isEqualTo(2);
        assertThat(response.myVote()).isNull();
        verify(companyVoteMapper).deleteById(existingVote.getId());
        verify(companyMapper).updateById(company);
    }

    @Test
    void switchesDislikeToLikeAndUpdatesBothCounters() {
        Company company = company(3, 2);
        CompanyVote existingVote = vote(VoteType.DISLIKE);
        when(companyMapper.selectById(COMPANY_ID)).thenReturn(company);
        when(companyVoteMapper.findByCompanyAndUser(COMPANY_ID, USER_ID)).thenReturn(existingVote);

        CompanyVoteResponse response = companyService.vote(COMPANY_ID, VoteType.LIKE, USER_ID);

        assertThat(response.company().getLikes()).isEqualTo(4);
        assertThat(response.company().getDislikes()).isEqualTo(1);
        assertThat(response.myVote()).isEqualTo(VoteType.LIKE);
        verify(companyVoteMapper).updateById(existingVote);
        verify(companyMapper).updateById(company);
        assertThat(existingVote.getVoteType()).isEqualTo(VoteType.LIKE);
    }

    private Company company(int likes, int dislikes) {
        Company company = new Company();
        company.setId(COMPANY_ID);
        company.setLikes(likes);
        company.setDislikes(dislikes);
        return company;
    }

    private CompanyVote vote(VoteType voteType) {
        CompanyVote vote = new CompanyVote();
        vote.setId(10L);
        vote.setCompanyId(COMPANY_ID);
        vote.setUserId(USER_ID);
        vote.setVoteType(voteType);
        return vote;
    }
}