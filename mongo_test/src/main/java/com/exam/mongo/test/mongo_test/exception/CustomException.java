package com.exam.mongo.test.mongo_test.exception;

public class CustomException extends RuntimeException{
  private final String errorCode; // 에러 코드를 추가할 수 있습니다.

  public CustomException(String message) {
    super(message); // 부모 클래스의 생성자 호출
    this.errorCode = "DEFAULT_ERROR"; // 기본 에러 코드 설정
  }

  public CustomException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
     return errorCode;
  }
}
