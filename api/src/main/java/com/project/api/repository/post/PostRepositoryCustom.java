package com.project.api.repository.post;

import com.project.api.model.SortType;
import com.project.api.model.response.post.PostListResDto;
import com.project.api.model.response.post.PostResDto;
import com.project.api.principal.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {

    Slice<PostListResDto> getPostList(Long id, Long offsetId, Pageable pageable, String keyword, SortType sortType);

    PostResDto getPost(Account account, Long postId);
}
