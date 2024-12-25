package com.exam.mongo.test.mongo_test.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
  private String token;
  private T data; // 실제 데이터
  private String msg; // 알림 메시지

  public ResponseDto(String token) {
    this.token = token;
  }

  public ResponseDto(T data, String msg) {
    this.data = data;
    this.msg = msg;
  }
}
