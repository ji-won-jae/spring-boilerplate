package com.project.api.repository.comment;

import com.project.api.model.SortType;
import com.project.api.model.response.comment.CommentListResDto;
import com.project.api.principal.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    Page<CommentListResDto> getCommentList(Account account, Long postId, Pageable pageable, SortType sortType);

}
