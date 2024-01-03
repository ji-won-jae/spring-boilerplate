package com.project.api.service.account;

import com.project.api.model.response.account.SignUpReqBody;
import com.project.api.repository.member.MemberRepository;
import com.project.core.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void signUp(SignUpReqBody reqBody) {
        Member member = Member.of(reqBody.getNickname());
        memberRepository.save(member);
    }
}
