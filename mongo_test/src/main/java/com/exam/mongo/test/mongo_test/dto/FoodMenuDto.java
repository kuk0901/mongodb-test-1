package com.exam.mongo.test.mongo_test.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuDto {
  private List<FoodDto> foodList;
  private List<FoodTypeDto> foodTypeList;
  private long totalItems;
  private int totalPages;
}
