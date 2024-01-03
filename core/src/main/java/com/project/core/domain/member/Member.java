package com.project.core.domain.member;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String nickname;

    public static Member of(String nickname) {
        Member member = new Member();
        member.setNickname(nickname);
        return member;
    }
}
