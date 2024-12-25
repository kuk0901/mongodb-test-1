package com.exam.mongo.test.mongo_test.food.repository;

import org.springframework.stereotype.Repository;

import com.exam.mongo.test.mongo_test.food.domain.Food;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
  Page<Food> findAll(Pageable pageable);
  Page<Food> findByType(String type, Pageable pageable);
  Optional<Food> findById(String id);

  @Query(value = "{}", fields = "{ 'type' : 1 }")
  List<String> findDistinctType();

  Food findByFoodName(String foodName);

  void deleteById(String id);
  boolean existsById(String id);

}
