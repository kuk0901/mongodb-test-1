package com.exam.mongo.test.mongo_test.res;

import java.util.List;

import com.exam.mongo.test.mongo_test.dto.FoodDto;
import com.exam.mongo.test.mongo_test.dto.FoodTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuResponseDto {
  private List<FoodDto> foodList;
    private List<FoodTypeDto> foodTypeList;
}
