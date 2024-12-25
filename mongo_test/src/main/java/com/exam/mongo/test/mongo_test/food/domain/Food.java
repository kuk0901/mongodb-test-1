package com.exam.mongo.test.mongo_test.food.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: 날짜 관련 어노테이션 사용할 MongoConfig class 생성
// * 실제 날짜 데이터 사용을 위해선 특정 인스턴스 변수에 어노테이션 사용해야 함
// * 날짜 데이터를 사용하지 않을 컬렉션은 해당 필드 제외해 domain 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "food")
public class Food {
  @Id
  private String id;
  private String foodName;
  private int price;
  private String type;
}
