package com.project.api.repository.comment;

import com.project.api.model.SortType;
import com.project.api.model.response.comment.CommentListResDto;
import com.project.api.principal.Account;
import com.project.core.domain.comment.QComment;
import com.project.core.domain.member.QMember;
import com.project.core.domain.post.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.project.core.domain.comment.QComment.*;
import static com.project.core.domain.member.QMember.*;
import static com.project.core.domain.post.QPost.*;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentListResDto> getCommentList(Account account, Long postId, Pageable pageable, SortType sortType) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(comment.post.id.eq(postId));

        OrderSpecifier orderSpecifier = null;
        switch (sortType == null ? SortType.LATEST : sortType) {
            case LATEST:
                orderSpecifier = new OrderSpecifier(Order.DESC, post.createDate);
                break;
            case OLDEST:
                orderSpecifier = new OrderSpecifier(Order.ASC, post.createDate);
                break;
        }

        var list = jpaQueryFactory.select(CommentListResDto.qBean(comment, post, member))
                .from(comment)
                .innerJoin(post).on(post.id.eq(comment.post.id))
                .innerJoin(member).on(member.id.eq(comment.writer.id))
                .where(where)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var totalCount = jpaQueryFactory.select(comment.count())
                .from(comment)
                .innerJoin(post).on(post.id.eq(comment.post.id))
                .innerJoin(member).on(member.id.eq(comment.writer.id))
                .where(where)
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }
}
