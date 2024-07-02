package com.project.core.domain.post;

import com.project.core.domain.base.BaseTimeEntity;
import com.project.core.domain.comment.Comment;
import com.project.core.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id",updatable = false)
    private Member writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    @Column
    private Integer viewCount;

    public static Post of(Member writer, String title, String content) {
        return Post.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();
    }

    //조회수 초기 값 생성
    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
