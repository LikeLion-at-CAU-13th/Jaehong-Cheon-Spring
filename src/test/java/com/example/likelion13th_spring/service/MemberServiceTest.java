package com.example.likelion13th_spring.service;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.Role;
import com.example.likelion13th_spring.repository.MemberRepository;
import com.example.likelion13th_spring.domain.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .age(i)
                    .build();



            memberRepository.save(member);
        });
    }

    @Test
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }
    @Test
    void testGetMemberByAge(){
        Page<Member> result = memberService.getMembersByAge(0,10);

        assertThat(result.getContent()).hasSize(10); // 첫 페이지 10명
        assertThat(result.getTotalElements()).isEqualTo(10); // 총 나이 >20인 사람 수 (21~30 → 10명)
        assertThat(result.getContent().get(0).getAge()).isGreaterThan(20);
        assertThat(result.getContent()).isSortedAccordingTo(Comparator.comparing(Member::getName));
    }

    @Test
    void testGetMemberByName(){
        Page<Member> result = memberService.getMembersByName(0,10);

        assertThat(result.getContent()).hasSize(10); // 첫 페이지 10명
        assertThat(result.getTotalElements()).isEqualTo(30); // us로 시작하는 사람 수 30명
    }

}