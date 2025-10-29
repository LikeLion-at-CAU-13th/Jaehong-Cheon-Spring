package com.example.likelion13th_spring.domain.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 토큰 확인
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("Access Token: " + accessToken);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId(); // "google"인지 "naver"인지

        String email;
        String username;

        if ("naver".equals(provider)) {
            Map<String, Object> resp = oAuth2User.getAttribute("response");
            email = (String) resp.get("email");
            username = (String) resp.get("name");
        } else { // google
            email = oAuth2User.getAttribute("email");
            username = oAuth2User.getAttribute("name");
        }


        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .email(email)
                                .name(username)
                                .password("")
                                .build()
                ));

        // 새로운 사용자는 DB에 저장
        if ("naver".equals(provider)) {
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    oAuth2User.getAttributes(),
                    "response" // 네이버는 최상위 키가 "response"
            );
        } else {
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    oAuth2User.getAttributes(),
                    "email" // 구글 등은 최상위에 "email"
            );
        }
    }
}