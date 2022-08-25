package com.finalproject.dontbeweak.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.dontbeweak.dto.LoginIdCheckDto;
import com.finalproject.dontbeweak.dto.SignupRequestDto;
import com.finalproject.dontbeweak.dto.SocialLoginInfoDto;
import com.finalproject.dontbeweak.model.NaverProfile;
import com.finalproject.dontbeweak.model.OAuthToken;
import com.finalproject.dontbeweak.security.UserDetailsImpl;
import com.finalproject.dontbeweak.service.KakaoService;
import com.finalproject.dontbeweak.service.NaverService;
import com.finalproject.dontbeweak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    private final NaverService naverService;

    //회원가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @RequestBody SignupRequestDto requestDto){
        String res = userService.registerUser(requestDto);
        if(res.equals("")){
            return "회원가입 성공";
        }else{
            return res;
        }
    }

    //카카오 소셜 로그인
    @GetMapping("/auth/kakao/callback")
    public @ResponseBody SocialLoginInfoDto kakaoCallback(String code, HttpServletResponse response) {      //ResponseBody -> Data를 리턴해주는 컨트롤러 함수
        return kakaoService.requestKakao(code, response);
    }

    //네이버 소셜 로그인
    @GetMapping("/auth/naver/callback")
    public @ResponseBody SocialLoginInfoDto naverCallback(String code, String state, HttpServletResponse response){
        return naverService.requestNaver(code, state, response);
    }

    //로그인 유저 정보
    @GetMapping("/user")
    public LoginIdCheckDto userDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.userInfo(userDetails);
    }
}