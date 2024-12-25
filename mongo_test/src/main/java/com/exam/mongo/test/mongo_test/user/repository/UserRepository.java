package com.exam.mongo.test.mongo_test.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.exam.mongo.test.mongo_test.user.domain.User;

@Repository // 없어도 동작하지만 가독성과 Exception 처리를 위해 사용
// * MongoRepository<User, String>: 도메인 객체와 ID 필드의 타입만을 지정
public interface UserRepository extends MongoRepository<User, String> {

  /*
   * Optional<T>를 반환
   * 주어진 ID에 해당하는 객체가 존재할 경우 해당 객체를 감싸는 Optional을 반환하고,
   * 존재하지 않을 경우 Optional.empty()를 반환
   */
  Optional<User> findById(String id); // mongodb Id로 사용자 검색

  // token 필드를 기준으로 검색
  Optional<User> findByToken(String token);

  // 사용자 ID가 존재하는지 확인하는 메서드 -> boolean 반환
  boolean existsByUserId(String userId);

  Optional<User> findByUserId(String userId); // 사용자 id로 사용자 검색
}
