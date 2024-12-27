package com.exam.mongo.test.mongo_test.seat.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.mongo.test.mongo_test.dto.SeatDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.seat.service.SeatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/seats") // * -> 여기만 복수형태로 작성함
public class SeatController {
  private final SeatService seatService;
  private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @GetMapping("/")
  public ResponseEntity<ResponseDto<?>> getSeatAll() {
    try {
      ResponseDto<List<SeatDto>> res = seatService.getSeatAll();

      logger.info("ResponseDto<List<SeatDto>>: {}", res);

      return ResponseEntity.ok().body(res);
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(new ResponseDto<>("", "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }
  }

  @PostMapping("/seat-add")
  public ResponseEntity<ResponseDto<String>> saveSeatOne(@RequestBody SeatDto seatDto) {
    try {
      return ResponseEntity.ok().body(seatService.saveSeatOne(seatDto));
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>(
          "", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ResponseDto<>("", e.getMessage()));
    }
  }

  @PutMapping("/seat-update")
  public ResponseEntity<ResponseDto<String>> putMethodName(@RequestBody SeatDto seatDto) {
    try {
      return ResponseEntity.ok().body(seatService.updateSeatOne(seatDto));
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>("", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ResponseDto<>("", "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }
  }
}
