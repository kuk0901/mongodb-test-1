package com.exam.mongo.test.mongo_test.food.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import com.exam.mongo.test.mongo_test.dto.*;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.food.domain.Food;
import com.exam.mongo.test.mongo_test.food.repository.FoodRepository;
import com.exam.mongo.test.mongo_test.res.ResponseDto;

@Service
public class FoodService {
  private FoodRepository foodRepository;
  private final ModelMapper modelMapper;
  private final MongoTemplate mongoTemplate;

  private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

  public FoodService(FoodRepository foodRepository, ModelMapper modelMapper, MongoTemplate mongoTemplate) {
    this.foodRepository = foodRepository;
    this.modelMapper = modelMapper;
    this.mongoTemplate = mongoTemplate;
  }

  // typeList
  public List<FoodTypeDto> getDistinctFoodTypes() {
    List<String> distinctTypes = mongoTemplate.getCollection("food")
        .distinct("type", String.class)
        .into(new ArrayList<>());

    return distinctTypes.stream()
        .map(FoodTypeDto::new)
        .toList();
  }

  // 전체
  public FoodMenuDto getAllFoodAndTypes(int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Food> foodPage = foodRepository.findAll(pageable);
    List<FoodDto> foodList = foodPage.getContent().stream()
        .map(food -> modelMapper.map(food, FoodDto.class))
        .toList();
    List<FoodTypeDto> foodTypeList = getDistinctFoodTypes();
    return new FoodMenuDto(foodList, foodTypeList, foodPage.getTotalElements(), foodPage.getTotalPages());
  }

  // type에 대응하는 메뉴
  public FoodMenuDto getFoodsByType(String type, int page, int size) {
    Pageable pageable = PageRequest.of(page - 1, size);
    Page<Food> foodPage = foodRepository.findByType(type, pageable);
    List<FoodDto> foodList = foodPage.getContent().stream()
        .map(food -> modelMapper.map(food, FoodDto.class))
        .toList();
    List<FoodTypeDto> foodTypeList = getDistinctFoodTypes();
    return new FoodMenuDto(foodList, foodTypeList, foodPage.getTotalElements(), foodPage.getTotalPages());
  }

  @Transactional
  public ResponseDto<String> saveFoodOne(FoodDto foodDto) throws CustomException {
    Optional<Food> existingFoodOpt = Optional.ofNullable(foodRepository.findByFoodName(foodDto.getFoodName()));

    if (existingFoodOpt.isPresent()) {
      throw new CustomException("이미 존재하는 메뉴입니다.", "409");
    }

    Food food = modelMapper.map(foodDto, Food.class);
    Food savedFood = foodRepository.save(food);

    if (savedFood == null || savedFood.getId() == null) {
      throw new CustomException("서버 오류로 인해 메뉴가 추가되지 않았습니다. 잠시 후 다시 시도해 주세요.", "500");
    }

    return new ResponseDto<>(savedFood.getFoodName() + " 메뉴가 추가되었습니다.", "");
  }

  @Transactional
  public ResponseDto<String> deleteFoodOne(String id) throws CustomException {
    Optional<Food> findFood = foodRepository.findById(id);

    if (findFood.isEmpty()) {
      throw new CustomException("해당 메뉴를 찾을 수 없습니다.", "400");
    }

    foodRepository.deleteById(id);

    if (foodRepository.existsById(id)) {
      throw new CustomException("서버 오류로 인해 메뉴를 삭제하지 못했습니다. 잠시 후 다시 시도해 주세요.", "500");
    }

    return new ResponseDto<>("", findFood.get().getFoodName() + " 메뉴를 삭제했습니다.");
  }

}
