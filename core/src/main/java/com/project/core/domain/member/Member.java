package com.project.core.domain.member;

import com.project.core.domain.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(length = 2000)
    private String content;

    @Column(nullable = false)
    private String password;

    @Column
    private String refreshToken;

    public static Member of(String nickname, String email, String password) {
        Member member = new Member();
        member.setNickname(nickname);
        member.setEmail(email);
        member.setPassword(password);
        return member;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateMyProfile(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
