package com.exam.mongo.test.mongo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
  @Getter
  @Setter
  private String id;
  @Getter
  @Setter
  private int seatNumber;
  @Getter
  @Setter
  private int cost;
}
