package com.project.api.service.post;

import com.project.api.exception.BadRequestException;
import com.project.api.exception.ConflictException;
import com.project.api.exception.NotFoundException;
import com.project.api.model.SortType;
import com.project.api.model.request.post.PostReqDto;
import com.project.api.model.response.post.PostListResDto;
import com.project.api.model.response.post.PostResDto;
import com.project.api.principal.Account;
import com.project.api.repository.member.MemberRepository;
import com.project.api.repository.post.PostRepository;
import com.project.api.repository.post_like.PostLikeRepository;
import com.project.core.domain.post.Post;
import com.project.core.domain.post_like.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createPost(Account account, PostReqDto reqBody) {
        var writer = memberRepository.findById(account.getId()).orElseThrow(NotFoundException::new);
        var post = postRepository.save(
                Post.of(
                        writer
                        , reqBody.getTitle()
                        , reqBody.getContent())
        );
        return post.getId();
    }

    @Transactional
    public void updatePost(Account account, Long postId, PostReqDto reqBody) {
        var post = findById(postId);
        if (!Objects.equals(post.getWriter().getId(), account.getId())) {
            throw new BadRequestException("해당 글을 작성한 사용자만 수정이 가능합니다.");
        }
        post.updatePost(reqBody.getTitle(), reqBody.getContent());
    }

    @Transactional
    public void deletePost(Account account, Long postId) {
        var post = findById(postId);
        if (!Objects.equals(post.getWriter().getId(), account.getId())) {
            throw new BadRequestException("해당 글을 작성한 사용자만 삭제가 가능합니다.");
        }
        postRepository.delete(post);
    }

    @Transactional
    public PostResDto getPost(Account account, Long postId) {
        var post = postRepository.getPost(account.getId(), postId);
        postRepository.updateViewCount(postId);

        return post;
    }

    @Transactional(readOnly = true)
    public Slice<PostListResDto> getPostList(Account account, Long offsetId, int limit, String keyword, SortType sortType) {
        var pageable = PageRequest.of(0, limit);
        var posts = postRepository.getPostList(account.getId(), offsetId, pageable, keyword, sortType);

        return posts;
    }

    @Transactional
    public void likePost(Account account, Long postId) {
        var member = memberRepository.findById(account.getId()).orElseThrow(NotFoundException::new);
        var post = findById(postId);
        if (postLikeRepository.existsByMemberIdAndPostId(member.getId(), post.getId())) {
            throw new ConflictException("이미 처리된 요청입니다.");
        }
        postLikeRepository.save(
                PostLike.of(
                        member
                        , post)
        );
    }

    @Transactional
    public void unlikePost(Account account, Long postId) {
        postLikeRepository.findByMemberIdAndPostId(account.getId(), postId)
                .ifPresent(postLikeRepository::delete);
    }
}
