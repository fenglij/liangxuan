package com.liangxuan.company.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyCreateRequest(
        @NotBlank(message = "公司名称不能为空") @Size(max = 120, message = "公司名称不能超过 120 个字符") String name,
        @Size(max = 255, message = "公司网址不能超过 255 个字符") String website,
        @Size(max = 500, message = "产品介绍不能超过 500 个字符") String product,
        @NotBlank(message = "上班时间不能为空") @Pattern(regexp = "(?:[01]\\d|2[0-3]):00", message = "上班时间格式不正确") String startTime,
        @NotBlank(message = "下班时间不能为空") @Pattern(regexp = "(?:[01]\\d|2[0-3]):00", message = "下班时间格式不正确") String endTime,
        @Min(value = 1, message = "每周至少工作 1 天") @Max(value = 7, message = "每周最多工作 7 天") Integer workDays) {
}
