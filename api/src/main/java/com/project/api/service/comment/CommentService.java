package com.project.api.service.comment;

import com.project.api.exception.BadRequestException;
import com.project.api.exception.NotFoundException;
import com.project.api.model.SortType;
import com.project.api.model.request.comment.CommentReqDto;
import com.project.api.model.response.comment.CommentListResDto;
import com.project.api.principal.Account;
import com.project.api.repository.comment.CommentRepository;
import com.project.api.repository.member.MemberRepository;
import com.project.api.repository.post.PostRepository;
import com.project.core.domain.comment.Comment;
import com.project.core.domain.member.Member;
import com.project.core.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Post findByPost(Long accountId) {
        return postRepository.findById(accountId).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Member findByWriter(Long accountId) {
        return memberRepository.findById(accountId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long createComment(Account account, CommentReqDto reqBody) {
        var writer = findByWriter(account.getId());
        var post = findByPost(reqBody.getPostId());
        var comment = commentRepository.save(
                Comment.of(
                        writer,
                        post,
                        reqBody.getContent())
        );
        return comment.getId();
    }

    @Transactional
    public void updateComment(Account account, Long commentId, CommentReqDto reqBody) {
        var comment = findById(commentId);
        if (!Objects.equals(account.getId(), comment.getWriter().getId())) {
            throw new BadRequestException("수정 권한이 없습니다.");
        }
        comment.updateComment(reqBody.getContent());
    }

    @Transactional
    public void deleteComment(Account account, Long commentId) {
        var comment = findById(commentId);
        if (!Objects.equals(account.getId(), comment.getWriter().getId())) {
            throw new BadRequestException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }

    public Page<CommentListResDto> getCommentList(Account account, Long postId, int offset, int size, SortType sortType) {
        var pageable = PageRequest.of(offset, size);
        var commentList = commentRepository.getCommentList(account, postId, pageable, sortType);
        return commentList;
    }
}
