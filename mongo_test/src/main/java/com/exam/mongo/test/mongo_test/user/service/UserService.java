package com.exam.mongo.test.mongo_test.user.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.mongo.test.mongo_test.dto.UserDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.token.TokenService;
import com.exam.mongo.test.mongo_test.user.domain.User;
import com.exam.mongo.test.mongo_test.user.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  // 회원 정보 조회
  public ResponseDto<UserDto> getUserInfo(String id) throws CustomException {
    Optional<User> userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      UserDto userDto = new UserDto(user.getUserId(), null, user.getUserName(), user.getRoles()); // 비밀번호는 노출하지 않음
      return new ResponseDto<>(userDto, "상세 정보를 확인해 보세요.");
    } else {
      throw new CustomException("해당 사용자를 찾을 수 없습니다.");
    }
  }

  // 회원가입
  public void registerUser(UserDto userDto) {
    // 비밀번호 인코딩
    String encodedPassword = passwordEncoder.encode(userDto.getPwd());

    // User 도메인 객체 생성
    User user = new User();
    user.setUserId(userDto.getUserId());
    user.setPwd(encodedPassword); // 인코딩된 비밀번호 저장
    user.setUserName(userDto.getUserName());
    user.setRoles(List.of("ROLE_USER")); // 권한 설정

    // 중복 사용자 ID 체크
    if (userRepository.existsByUserId(user.getUserId())) {
      throw new CustomException("이미 사용 중인 사용자 ID입니다."); // 사용자 정의 예외 발생
    }

    // 사용자 정보를 데이터베이스에 저장
    try {
      userRepository.save(user);
    } catch (Exception e) {
      throw new CustomException("사용자 등록 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
    }
  }

  public ResponseDto<UserDto> signin(String userId, String pwd, HttpServletResponse response) throws CustomException {
    Optional<User> userOptional = userRepository.findByUserId(userId);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (passwordEncoder.matches(pwd, user.getPwd())) {
        String accessToken = tokenService.generateToken(user.getUserId()); // 액세스 토큰 생성
        String refreshToken = tokenService.generateRefreshToken(user.getUserId()); // 리프레시 토큰 생성

        // 쿠키에 리프레시 토큰 저장
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true); // JavaScript에서 접근 불가
        cookie.setSecure(false); // 개발 단계에서는 false로 설정 (HTTPS 사용 시 true로 변경)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 100); // 100일 유효

        response.addCookie(cookie); // 응답에 쿠키 추가

        // 사용자 정보를 DTO로 변환
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setRoles(user.getRoles());

        // ResponseDto 생성
        return new ResponseDto<>(accessToken, userDto, "로그인 성공");
      } else {
        throw new CustomException("비밀번호가 일치하지 않습니다.");
      }
    } else {
      throw new CustomException("해당 사용자를 찾을 수 없습니다.");
    }
  }

  // 리프레시 토큰을 이용해 액세스 토큰 생성
  public String refreshAccessToken(String refreshToken) throws CustomException {
    if (!tokenService.validateRefreshToken(refreshToken)) {
      throw new CustomException("리프레시 토큰이 유효하지 않습니다.");
    }

    String userId = tokenService.extractUsername(refreshToken); // 사용자 ID 추출
    return tokenService.generateToken(userId); // 새로운 액세스 토큰 생성
  }

  public UserDto getUserInfoByToken(String token) throws CustomException {
    // JWT 검증
    if (!tokenService.validateToken(token, tokenService.extractUsername(token))) {
      throw new CustomException("유효하지 않은 토큰입니다.", "401");
    }

    // 사용자 ID 추출
    String userId = tokenService.extractUsername(token);

    // 데이터베이스에서 사용자 정보 조회
    Optional<User> userOptional = userRepository.findByUserId(userId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      UserDto userDto = new UserDto();
      userDto.setUserId(user.getUserId());
      userDto.setUserName(user.getUserName());
      userDto.setRoles(user.getRoles());

      return userDto; // 사용자 정보 반환
    } else {
      throw new CustomException("사용자를 찾을 수 없습니다.");
    }
  }
}