package com.project.core.domain.comment;

import com.project.core.domain.base.BaseTimeEntity;
import com.project.core.domain.member.Member;
import com.project.core.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", updatable = false)
    private Post post;

    public static Comment of(Member writer, Post post, String content) {
        return Comment.builder()
                .writer(writer)
                .post(post)
                .content(content)
                .build();
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
