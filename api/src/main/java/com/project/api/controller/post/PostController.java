package com.project.api.controller.post;

import com.project.api.model.SortType;
import com.project.api.model.common.ResponseDto;
import com.project.api.model.request.post.PostReqDto;
import com.project.api.model.response.post.PostListResDto;
import com.project.api.model.response.post.PostResDto;
import com.project.api.principal.Account;
import com.project.api.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createPost(@AuthenticationPrincipal Account account, @Valid @RequestBody PostReqDto reqBody) {
        var response = postService.createPost(account, reqBody);

        return ResponseDto.created(response);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PutMapping(value = "/{postId}")
    public ResponseEntity<Void> updatePost(@AuthenticationPrincipal Account account, @Valid @RequestBody PostReqDto reqBody, @PathVariable Long postId) {
        postService.updatePost(account, postId, reqBody);

        return ResponseDto.noContent();
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal Account account, @PathVariable Long postId) {
        postService.deletePost(account, postId);

        return ResponseDto.noContent();
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ResponseDto<PostResDto>> getPost(@AuthenticationPrincipal Account account, @PathVariable Long postId) {
        var response = postService.getPost(account, postId);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<Slice<PostListResDto>>> getPostList(@AuthenticationPrincipal Account account
            , @RequestParam(required = false) Long offsetId
            , @RequestParam int limit
            , @RequestParam(required = false) String keyword
            , @RequestParam(required = false) SortType sortType) {
        var response = postService.getPostList(account, offsetId, limit, keyword, sortType);

        return ResponseDto.ok(response);
    }

    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요를 합니다.")
    @PostMapping(value = "/{postId}/like")
    public ResponseEntity<Void> likePost(@AuthenticationPrincipal Account account, @PathVariable Long postId) {
        postService.likePost(account, postId);

        return ResponseDto.noContent();
    }

    @Operation(summary = "게시글 좋아요 취소", description = "게시글 좋아요를 취소합니다.")
    @DeleteMapping(value = "/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@AuthenticationPrincipal Account account, @PathVariable Long postId) {
        postService.unlikePost(account, postId);

        return ResponseDto.noContent();
    }

}
