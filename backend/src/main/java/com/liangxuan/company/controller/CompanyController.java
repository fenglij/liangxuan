package com.liangxuan.company.controller;

import com.liangxuan.common.Result;
import com.liangxuan.company.dto.CompanyCreateRequest;
import com.liangxuan.company.dto.CompanyPageResponse;
import com.liangxuan.company.dto.CompanyVoteResponse;
import com.liangxuan.company.dto.UserVoteResponse;
import com.liangxuan.company.dto.VoteType;
import com.liangxuan.company.entity.Company;
import com.liangxuan.company.service.CompanyService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public Result<Company> create(@Valid @RequestBody CompanyCreateRequest request,
                                  @RequestAttribute("userId") Long userId) {
        return Result.success(companyService.create(request, userId));
    }

    @GetMapping
    public Result<CompanyPageResponse> page(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "20") long pageSize) {
        CompanyPageResponse page = companyService.page(keyword, pageNum, pageSize);
        return Result.success(page);
    }

    @GetMapping("/{companyId}")
    public Result<CompanyVoteResponse> detail(@PathVariable Long companyId,
                                              @RequestAttribute(value = "userId", required = false) Long userId) {
        return Result.success(companyService.detail(companyId, userId));
    }

    @PutMapping("/{companyId}/vote")
    public Result<CompanyVoteResponse> vote(@PathVariable Long companyId,
                                             @RequestParam VoteType voteType,
                                             @RequestAttribute("userId") Long userId) {
        return Result.success(companyService.vote(companyId, voteType, userId));
    }

    @DeleteMapping("/{companyId}/vote")
    public Result<CompanyVoteResponse> cancelVote(@PathVariable Long companyId,
                                                  @RequestAttribute("userId") Long userId) {
        return Result.success(companyService.cancelVote(companyId, userId));
    }

    @GetMapping("/user-votes")
    public Result<List<UserVoteResponse>> listUserVotes(@RequestAttribute("userId") Long userId) {
        return Result.success(companyService.listUserVotes(userId));
    }
}
