package com.example.likelion13th_spring.domain.service;


import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.likelion13th_spring.repository.MemberRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findAll(pageable);
    }

    public Page<Member> getMembersByAge(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("name").ascending());
        return memberRepository.findByAgeGreaterThan(20, pageable);
    }

    public Page<Member> getMembersByName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("name").ascending());
        return memberRepository.findByNameStartingWith("us", pageable);
    }


    public void join(JoinRequestDto joinRequestDto) {
        // 해당 이름이 이미 존재하는 경우
        if (memberRepository.existsByName(joinRequestDto.getName())){
            return;
        }

        // 유저 객체 생성
        Member member = joinRequestDto.toEntity(bCryptPasswordEncoder);

        // 유저 정보 저장
        memberRepository.save(member);
    }

}
