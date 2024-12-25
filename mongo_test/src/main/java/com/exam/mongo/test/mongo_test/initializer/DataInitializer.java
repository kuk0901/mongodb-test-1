package com.exam.mongo.test.mongo_test.initializer;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.exam.mongo.test.mongo_test.food.domain.Food;
import com.exam.mongo.test.mongo_test.food.repository.FoodRepository;
import com.exam.mongo.test.mongo_test.token.TokenService;
import com.exam.mongo.test.mongo_test.user.domain.User;
import com.exam.mongo.test.mongo_test.user.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;
  private final FoodRepository foodRepository;

  public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService, FoodRepository foodRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
    this.foodRepository = foodRepository;
  }

  @Override
  public void run(String... args) {
    // users에 아무런 데이터가 없을 때 슈퍼 관리자 계정을 생성해 줌
    if (userRepository.count() == 0) {
      User admin = new User();
      admin.setUserId("admin");
      admin.setUserName("admin");
      admin.setPwd(passwordEncoder.encode("admin1234"));
      admin.setRoles(Arrays.asList("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_USER"));

      admin.setToken(tokenService.generateToken(admin.getUserId())); // JWT 생성

      // users 테이블이 최초에 만들어지면서, 데이터가 insert됨
      userRepository.save(admin);
    }

    // 음식이 하나도 없다면? 음식 한 세 개 정도는 넣어줌 ㅇㅇ
    // 내가 최초에 해당 테이블에 데이터를 넣을 때 생성됨
    if (foodRepository.count() == 0) {
      Food food1 = new Food();
      food1.setFoodName("떡볶이");
      food1.setPrice(3000);

      Food food2 = new Food();
      food2.setFoodName("라면");
      food2.setPrice(1500);

      // food 테이블이 최초에 만들어지면서, 데이터가 insert됨
      foodRepository.save(food1);
    }
  }
}
