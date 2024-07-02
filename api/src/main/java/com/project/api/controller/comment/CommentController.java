package com.project.api.controller.comment;

import com.project.api.model.SortType;
import com.project.api.model.common.PageResponseDto;
import com.project.api.model.common.ResponseDto;
import com.project.api.model.request.comment.CommentReqDto;
import com.project.api.model.request.post.PostReqDto;
import com.project.api.model.response.comment.CommentListResDto;
import com.project.api.principal.Account;
import com.project.api.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 관련 API")
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createComment(@AuthenticationPrincipal Account account, @Valid @RequestBody CommentReqDto reqBody) {
        var response = commentService.createComment(account, reqBody);

        return ResponseDto.created(response);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<Void> updateComment(@AuthenticationPrincipal Account account, @Valid @RequestBody CommentReqDto reqBody, @PathVariable Long commentId) {
        commentService.updateComment(account, commentId, reqBody);

        return ResponseDto.noContent();
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Void> deleteComment(@AuthenticationPrincipal Account account, @PathVariable Long commentId) {
        commentService.deleteComment(account, commentId);

        return ResponseDto.noContent();
    }

    @Operation(summary = "댓글 목록 조회", description = "댓글을 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<PageResponseDto<CommentListResDto>>> getCommentList(@AuthenticationPrincipal Account account

            , @RequestParam(required = false) Long postId
            , @RequestParam int page
            , @RequestParam int size
            , @RequestParam(required = false) SortType sortType) {
        var response = commentService.getCommentList(account, postId, page, size, sortType);

        return PageResponseDto.ok(response);
    }
}
