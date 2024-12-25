package com.exam.mongo.test.mongo_test.serviceInstructions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.exam.mongo.test.mongo_test.serviceInstructions.domain.ServiceInstruction;

@Repository
public interface ServiceInstructionsRepository extends MongoRepository<ServiceInstruction, String> {
  List<ServiceInstruction> findAll();

  List<ServiceInstruction> findByType(String type);

  Optional<ServiceInstruction> findByTitle(String title);

  Optional<ServiceInstruction> findByContent(String content);

  Optional<ServiceInstruction> findById(String id);

  void deleteById(String id);

  boolean existsById(String id);

}
