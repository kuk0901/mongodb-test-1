package com.exam.mongo.test.mongo_test.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String userId; // 사용자 ID
  private String pwd; // 비밀번호
  private String userName;
  private List<String> roles; // 사용자 권한 목록
}