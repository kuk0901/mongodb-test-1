package com.exam.mongo.test.mongo_test.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.mongo.test.mongo_test.dto.UserDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.user.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  // 생성자 주입
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<UserDto>> getUserInfo(@PathVariable String id) {
    try {
      ResponseDto<UserDto> response = userService.getUserInfo(id); // getUserInfo 메서드 호출
      return ResponseEntity.ok(response); // 성공적으로 사용자 정보 반환
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>(null, e.getMessage())); // 에러 메시지 반환
    }
  }

}
