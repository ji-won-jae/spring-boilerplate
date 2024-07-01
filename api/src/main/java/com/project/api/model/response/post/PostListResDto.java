package com.project.api.model.response.post;

import com.project.api.model.response.member.MemberProfileResDto;
import com.project.core.domain.member.QMember;
import com.project.core.domain.post.Post;
import com.project.core.domain.post.QPost;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListResDto {
    @Schema(description = "작성자 정보")
    private MemberProfileResDto memberProfile;
    @Schema(description = "제목", example = "제목명")
    private String title;
    @Schema(description = "내용", example = "내용")
    private String content;
    @Schema(description = "조회수", example = "1")
    private Integer viewCount;
    @Schema(description = "등록일", example = "2024")
    private LocalDateTime createDate;

    public static QBean<PostListResDto> qBean(QPost post, QMember member) {
        return Projections.bean(PostListResDto.class,
                MemberProfileResDto.qBean(member).as("memberProfile"),
                post.title.as("title"),
                post.content.as("content"),
                post.viewCount.as("viewCount"),
                post.createDate.as("createDate")
        );
    }
}
