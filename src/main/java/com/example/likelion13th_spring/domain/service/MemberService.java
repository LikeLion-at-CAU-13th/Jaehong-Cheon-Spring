package com.example.likelion13th_spring.domain.service;


import com.example.likelion13th_spring.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.likelion13th_spring.repository.MemberRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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


    
}
