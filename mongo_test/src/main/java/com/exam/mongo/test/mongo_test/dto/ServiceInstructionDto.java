package com.exam.mongo.test.mongo_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInstructionDto {
  private String id;
  private String type;
  private String title;
  private String content;
}