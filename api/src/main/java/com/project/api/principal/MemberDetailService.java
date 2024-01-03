package com.project.api.principal;

import com.project.api.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            return (UserDetails) memberRepository.findById(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException(id));
        }catch (NullPointerException e){
            throw new UsernameNotFoundException(null);
        }
    }
}
