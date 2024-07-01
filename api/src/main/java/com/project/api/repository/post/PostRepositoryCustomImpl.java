package com.project.api.repository.post;

import com.project.api.model.SortType;
import com.project.api.model.response.post.PostListResDto;
import com.project.api.model.response.post.PostResDto;
import com.project.api.principal.Account;
import com.project.core.domain.member.QMember;
import com.project.core.domain.post.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.core.domain.member.QMember.*;
import static com.project.core.domain.post.QPost.*;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PostResDto getPost(Account account, Long postId) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(post.id.eq(postId));

        return jpaQueryFactory.select(PostResDto.qBean(post, member))
                .from(post)
                .innerJoin(member).on(member.id.eq(post.writer.id))
                .where(where)
                .fetchOne();
    }

    @Override
    public Slice<PostListResDto> getPostList(Long id, Long offsetId, Pageable pageable, String keyword, SortType sortType) {
        BooleanBuilder where = new BooleanBuilder();

        if (offsetId != null) {
            where.and(post.id.lt(offsetId));
        }
        if (keyword != null) {
            where.and(post.title.like(keyword));
        }

        OrderSpecifier orderSpecifier = null;
        switch (sortType == null ? SortType.LATEST : sortType) {
            case LATEST:
                orderSpecifier = new OrderSpecifier(Order.DESC, post.createDate);
                break;
            case OLDEST:
                orderSpecifier = new OrderSpecifier(Order.ASC, post.createDate);
                break;
        }

        var list = jpaQueryFactory.select(PostListResDto.qBean(post, member))
                .from(post)
                .innerJoin(member).on(member.id.eq(post.writer.id))
                .where(where)
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNextPage = hasNextPage(list, pageable.getPageSize());

        return new SliceImpl<>(list, pageable, hasNextPage);
    }

    //무한 스크롤 다음 페이지 여부를 알기 위함
    private boolean hasNextPage(List<PostListResDto> list, int pageSize) {
        if (list.size() > pageSize) {
            list.remove(pageSize);
            return true;
        }
        return false;
    }

}
