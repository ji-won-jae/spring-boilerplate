package com.project.api.model.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostReqDto {
    @Schema(description = "제목", example = "제목명")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "글 내용", example = "내용")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
