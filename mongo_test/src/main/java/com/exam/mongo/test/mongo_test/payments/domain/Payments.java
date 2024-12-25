package com.exam.mongo.test.mongo_test.payments.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.exam.mongo.test.mongo_test.food.domain.Food;
import com.exam.mongo.test.mongo_test.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payments {
  @Id
  private String id;

  @DBRef
  private User user;
  private int amount; // 결제 금액
  private Date paymentTime; // 결제 시간
  @DBRef
  private List<Food> food; // 주문 내역
  private int validityPeriod; // 사용 가능 시간
}
