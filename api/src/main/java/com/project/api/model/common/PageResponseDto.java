package com.project.api.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> content;
    private int page;
    private int size;
    private int totalPage;
    private long totalCount;

    public static <T> ResponseEntity<ResponseDto<PageResponseDto<T>>> ok(Page<T> data) {
        var pageData = new PageResponseDto<>(
                data.getContent(),
                data.getPageable().getPageNumber(),
                data.getSize(),
                data.getTotalPages(),
                data.getTotalElements()
        );

        return ResponseDto.ok(pageData);
    }
}
