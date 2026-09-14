package com.liangxuan.company.dto;

import com.liangxuan.company.entity.Company;
import java.util.List;

public record CompanyPageResponse(long pageNum, long pageSize, long total, List<Company> records) {
}