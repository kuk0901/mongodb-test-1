package com.exam.mongo.test.mongo_test.food.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.mongo.test.mongo_test.dto.FoodDto;
import com.exam.mongo.test.mongo_test.dto.FoodMenuDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.food.service.FoodService;
import com.exam.mongo.test.mongo_test.res.FoodMenuResponseDto;
import com.exam.mongo.test.mongo_test.res.ResponseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/menus")
public class FoodController {

  private FoodService foodService;
  private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

  public FoodController(FoodService foodService) {
    this.foodService = foodService;
  }

  @GetMapping("/")
  public ResponseEntity<ResponseDto<FoodMenuDto>> getFoods(
      @RequestParam(name = "search", required = false) String search,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    FoodMenuDto foodMenuResponse;

    if (search == null || search.isEmpty()) {
      foodMenuResponse = foodService.getAllFoodAndTypes(page, size);
    } else {
      foodMenuResponse = foodService.getFoodsByType(search, page, size);
    }

    ResponseDto<FoodMenuDto> responseDto = new ResponseDto<>(foodMenuResponse,
        search == null || search.isEmpty() ? "전체 메뉴 조회" : search + " 타입 메뉴 조회");

    return ResponseEntity.ok().body(responseDto);
  }

  @PostMapping("/food-add")
  public ResponseEntity<ResponseDto<String>> insertFoodOne(@RequestBody FoodDto foodDto) {
    ResponseDto<String> responseDto;

    try {
      responseDto = foodService.saveFoodOne(foodDto);

      return ResponseEntity.ok().body(responseDto);
    } catch (CustomException e) {
      responseDto = new ResponseDto<>(e.getMessage(), "");

      return ResponseEntity.badRequest().body(responseDto);
    } catch (Exception e) {
      logger.error("Unexpected error occurred: ", e);
      responseDto = new ResponseDto<>("서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요", "");
      return ResponseEntity.internalServerError().body(responseDto);
    }
  }

  @DeleteMapping("/{foodId}")
  public ResponseEntity<ResponseDto<String>> deleteFoodOne(@PathVariable("foodId") String foodId) {
    ResponseDto<String> responseDto;
    try {
      responseDto = foodService.deleteFoodOne(foodId);

      return ResponseEntity.ok().body(responseDto);
    } catch (CustomException e) {
      responseDto = new ResponseDto<>(e.getMessage(), "");

      return ResponseEntity.badRequest().body(responseDto);
    } catch (Exception e) {
      responseDto = new ResponseDto<>(e.getMessage(), "");
      return ResponseEntity.internalServerError().body(responseDto);
    }

  }

}
