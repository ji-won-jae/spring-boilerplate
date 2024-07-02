package com.project.api.model.request.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentReqDto {
    @Schema(description = "게시글 PK", example = "1")
    @NotNull(message = "게시글 번호를 입력해주세요.")
    private Long postId;

    @Schema(description = "내용", example = "댓글 내용")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
