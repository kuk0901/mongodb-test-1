package com.exam.mongo.test.mongo_test.seat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.exam.mongo.test.mongo_test.seat.domain.Seat;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {
  List<Seat> findAll();

  Optional<Seat> findBySeatNumber(int number);

  Optional<Seat> findById(String id);

  boolean existsById(String id);

  void deleteById(String id);
}
