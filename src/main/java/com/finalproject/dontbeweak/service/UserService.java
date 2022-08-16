package com.finalproject.dontbeweak.service;

import com.finalproject.dontbeweak.dto.SignupRequestDto;
import com.finalproject.dontbeweak.exception.CustomException;
import com.finalproject.dontbeweak.exception.ErrorCode;
import com.finalproject.dontbeweak.model.Cat;
import com.finalproject.dontbeweak.model.CatImage;
import com.finalproject.dontbeweak.model.User;
import com.finalproject.dontbeweak.repository.UserRepository;
import com.finalproject.dontbeweak.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final CatService catService;

    public String registerUser(SignupRequestDto requestDto, CatImage catImage){
        String error = "";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String nickname = requestDto.getNickname();
//        String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        //회원 username 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            throw new CustomException(ErrorCode.USERNAME_DUPLICATION_CODE);
        }

        //회원가입 조건
//        if(!Pattern.matches(pattern, username)){
//            throw new CustomException(ErrorCode.USERNAME_FORM_CODE);
//        }
        if (!password.equals(passwordCheck)){
            throw new CustomException(ErrorCode.PASSWORD_CHECK_CODE);
        } else if (password.length() < 4) {
            throw new CustomException(ErrorCode.PASSWORD_LENGTH_CODE);
        }

        //패스워드 인코딩
        password = passwordEncoder.encode(password);
        requestDto.setPassword(password);

        //유저 정보 저장
        User user = new User(username, password, nickname);
        userRepository.save(user);

        // 회원가입 후 사용자의 새 고양이 자동 생성
        catService.createNewCat(user, catImage);

        return error;
    }

    public User userInfo(UserDetailsImpl userDetails) {
        Long id = userDetails.getUser().getId();
        String username = userDetails.getUser().getUsername();
        String nickname = userDetails.getUser().getNickname();
        User userInfo = new User(id, username, nickname);
        return userInfo;
    }
}
