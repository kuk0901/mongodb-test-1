package com.exam.mongo.test.mongo_test.computer.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Computer {
  @Id
  private String id;
  private String comName;
  private boolean isInUse;
}
