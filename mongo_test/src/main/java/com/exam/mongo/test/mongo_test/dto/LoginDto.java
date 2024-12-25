package com.exam.mongo.test.mongo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
  private String userId; // 사용자 ID
  private String pwd; // 비밀번호
}