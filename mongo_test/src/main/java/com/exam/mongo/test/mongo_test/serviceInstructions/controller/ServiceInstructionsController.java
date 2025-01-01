package com.exam.mongo.test.mongo_test.serviceInstructions.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.mongo.test.mongo_test.dto.ServiceInstructionDto;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.serviceInstructions.domain.ServiceInstruction;
import com.exam.mongo.test.mongo_test.serviceInstructions.service.ServiceInstructionsService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/cs/si")
public class ServiceInstructionsController {
  private ServiceInstructionsService serviceInstructionsService;

  private static final Logger logger = LoggerFactory.getLogger(ServiceInstructionsController.class);

  public ServiceInstructionsController(ServiceInstructionsService serviceInstructionsService) {
    this.serviceInstructionsService = serviceInstructionsService;
  }

  @GetMapping("/guide")
  public ResponseEntity<ResponseDto<?>> getServiceInstructionsRepositoryFindByType(
      @RequestParam("type") String type) {

    try {
      ResponseDto<List<ServiceInstruction>> res = serviceInstructionsService
          .getServiceInstructionsRepositoryFindByType(type);

      return ResponseEntity.ok().body(res);
    } catch (CustomException e) {
      return ResponseEntity.badRequest().body(new ResponseDto<>(e, e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(new ResponseDto<>(e, "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }
  }

  @PostMapping("/guide-add")
  public ResponseEntity<ResponseDto<String>> insertServiceInstructionOne(
      @RequestBody ServiceInstructionDto serviceInstructionsDto) {

    ResponseDto<String> res;

    try {
      res = serviceInstructionsService.insertServiceInstructionOne(serviceInstructionsDto);

      return ResponseEntity.ok().body(res);
    } catch (CustomException e) {
      res = new ResponseDto<>("", e.getMessage());

      return ResponseEntity.badRequest().body(res);
    } catch (Exception e) {
      res = new ResponseDto<>("", e.getMessage());
      return ResponseEntity.internalServerError().body(res);
    }
  }

  @PatchMapping("/guide-update")
  public ResponseEntity<ResponseDto<String>> updateServiceInstructionOne(@RequestBody ServiceInstructionDto dto) {
    try {
      ResponseDto<String> res = serviceInstructionsService.updateServiceInstructionOne(dto);
      return ResponseEntity.status(HttpStatus.OK).body(res);
    } catch (CustomException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto<>("", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ResponseDto<>("", "서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."));
    }
  }

}
