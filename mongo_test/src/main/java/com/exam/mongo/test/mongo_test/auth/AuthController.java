package com.exam.mongo.test.mongo_test.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.mongo.test.mongo_test.dto.UserDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.token.TokenService;
import com.exam.mongo.test.mongo_test.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;
  private final TokenService tokenService;

  // 생성자 주입
  public AuthController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<String>> signUp(@RequestBody UserDto userDto) {
    try {
      userService.registerUser(userDto); // 회원가입 처리
      return ResponseEntity.ok(new ResponseDto<>(null, "회원가입 되었습니다."));
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>(null, e.getMessage()));
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<ResponseDto<UserDto>> signIn(@RequestBody UserDto userDto, HttpServletResponse response) {
    try {
      // 로그인 처리 및 사용자 정보와 액세스 토큰을 포함한 ResponseDto 생성
      ResponseDto<UserDto> responseDto = userService.signin(userDto.getUserId(), userDto.getPwd(), response);

      return ResponseEntity.ok(responseDto);
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>(null, null, e.getMessage()));
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<ResponseDto<String>> refresh(@CookieValue("refreshToken") String refreshToken) {
    try {
      // 리프레시 토큰 유효성 검사
      if (!tokenService.validateRefreshToken(refreshToken)) {
        throw new CustomException("리프레시 토큰이 유효하지 않습니다."); // 유효하지 않은 경우 예외 발생
      }

      String newAccessToken = userService.refreshAccessToken(refreshToken); // 리프레시 토큰으로 액세스 토큰 갱신

      return ResponseEntity.ok(new ResponseDto<>(newAccessToken, "액세스 토큰이 갱신되었습니다."));
    } catch (CustomException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto<>(null, e.getMessage()));
    }
  }

  @PostMapping("/signout")
  public ResponseEntity<Void> signout(HttpServletResponse response) {
    try {
      // 쿠키에서 리프레시 토큰 삭제
      Cookie cookie = new Cookie("refreshToken", null);
      cookie.setHttpOnly(true);
      cookie.setSecure(false); // HTTPS 사용 시 true로 변경
      cookie.setPath("/");
      cookie.setMaxAge(0); // 쿠키 만료 설정

      response.addCookie(cookie); // 쿠키 삭제

      return ResponseEntity.ok().build(); // 응답을 비움
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(null); // 실패 시 응답
    }
  }

  @GetMapping("/me")
  public ResponseEntity<ResponseDto<?>> getUserInfo(@RequestHeader("Authorization") String authorization) {
    try {
      // Authorization 헤더에서 Bearer 토큰 추출
      String token = authorization.substring(7); // "Bearer " 이후의 부분 추출
      UserDto userDto = userService.getUserInfoByToken(token); // 토큰을 사용하여 사용자 정보 가져오기

      return ResponseEntity.ok(new ResponseDto<>(userDto, "사용자 정보 조회 성공"));
    } catch (CustomException e) {
      ResponseDto<String> responseDto = new ResponseDto<>(e.getErrorCode(), e.getMessage());

      if ("401".equals(e.getErrorCode())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
      }
    }
  }
}