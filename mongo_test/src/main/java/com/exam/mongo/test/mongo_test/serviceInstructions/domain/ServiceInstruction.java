package com.exam.mongo.test.mongo_test.serviceInstructions.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ServiceInstruction")
public class ServiceInstruction {
  @Id
  private String id;
  private String type;
  private String title;
  private String content;
}
