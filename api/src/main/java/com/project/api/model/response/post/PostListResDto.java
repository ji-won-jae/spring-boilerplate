package com.project.api.model.response.post;

import com.project.api.model.response.member.MemberProfileResDto;
import com.project.core.domain.member.QMember;
import com.project.core.domain.post.Post;
import com.project.core.domain.post.QPost;
import com.project.core.domain.post_like.QPostLike;
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
    @Schema(description = "게시글 PK", example = "1")
    private Long postId;
    @Schema(description = "제목", example = "제목명")
    private String title;
    @Schema(description = "내용", example = "내용")
    private String content;
    @Schema(description = "조회수", example = "1")
    private Integer viewCount;
    @Schema(description = "좋아요  수", example = "1")
    private Long likeCount;
    @Schema(description = "좋아요 여부", example = "false")
    private Boolean isLike;
    @Schema(description = "등록일", example = "2024")
    private LocalDateTime createDate;

    public static QBean<PostListResDto> qBean(QPost post, QMember member, QPostLike postLike, QPostLike myPostLike) {
        return Projections.bean(PostListResDto.class,
                MemberProfileResDto.qBean(member).as("memberProfile"),
                post.id.as("postId"),
                post.title.as("title"),
                post.content.as("content"),
                post.viewCount.as("viewCount"),
                postLike.count().as("likeCount"),
                myPostLike.isNotNull().as("isLike"),
                post.createDate.as("createDate")
        );
    }
}
