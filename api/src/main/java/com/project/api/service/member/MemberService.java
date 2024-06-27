package com.project.api.service.member;

import com.project.api.exception.NotFoundException;
import com.project.api.model.request.member.UpdateMyProfileResDto;
import com.project.api.model.response.member.MeResDto;
import com.project.api.model.response.member.MemberProfileResDto;
import com.project.api.principal.Account;
import com.project.api.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MeResDto getMyProfile(Account account) {
        return memberRepository.findById(account.getId())
                .map(MeResDto::from)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void updateMyProfile(Account account, UpdateMyProfileResDto resDto) {
        var member = memberRepository.findById(account.getId()).orElseThrow(NotFoundException::new);
        member.updateMyProfile(resDto.getContent(), resDto.getNickname());
    }

    @Transactional(readOnly = true)
    public MemberProfileResDto getMemberProfile(Account account, Long memberId) {
        var member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
        return MemberProfileResDto.of(
                member.getId()
                , member.getNickname()
                , member.getEmail()
        );
    }
}
