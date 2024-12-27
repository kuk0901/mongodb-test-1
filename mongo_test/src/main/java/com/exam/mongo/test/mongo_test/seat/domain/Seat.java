package com.exam.mongo.test.mongo_test.seat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "seat")
public class Seat {
  @Id
  private String id;
  private int seatNumber;
  private int cost;
}
