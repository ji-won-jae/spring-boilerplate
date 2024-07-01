package com.project.api.repository.post;

import com.project.core.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Modifying(clearAutomatically = true) // 연산 이후 영속성 컨텍스트를 clear 하도록 설정
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    void updateViewCount(Long postId);
}
