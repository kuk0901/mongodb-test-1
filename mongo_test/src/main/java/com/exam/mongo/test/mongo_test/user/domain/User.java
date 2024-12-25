package com.exam.mongo.test.mongo_test.user.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Document(collection = "users") // MongoDB의 "users" 컬렉션과 매핑
public class User {
  @Id
  private String id; // MongoDB에서 자동 생성되는 ID
  private String userId;
  private String userName;
  private String pwd;
  private String token;
  private List<String> roles;

  private String computerId;

  public void addRole(String role) {
    if (!roles.contains(role)) {
      roles.add(role);
    }
  }

  public void removeRole(String role) {
    roles.remove(role);
  }
}
