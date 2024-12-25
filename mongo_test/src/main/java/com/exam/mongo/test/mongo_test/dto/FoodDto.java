package com.exam.mongo.test.mongo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {
  private String id;
  private String foodName;
  private int price;
  private String type;
}
