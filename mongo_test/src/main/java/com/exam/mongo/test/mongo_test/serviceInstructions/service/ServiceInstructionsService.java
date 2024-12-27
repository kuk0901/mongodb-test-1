package com.exam.mongo.test.mongo_test.serviceInstructions.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.serviceInstructions.domain.ServiceInstruction;
import com.exam.mongo.test.mongo_test.serviceInstructions.repository.ServiceInstructionsRepository;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.dto.ServiceInstructionDto;

@Service
public class ServiceInstructionsService {
  private ServiceInstructionsRepository serviceInstructionsRepository;
  private final ModelMapper modelMapper;

  public ServiceInstructionsService(ServiceInstructionsRepository serviceInstructionsRepository,
      ModelMapper modelMapper) {
    this.serviceInstructionsRepository = serviceInstructionsRepository;
    this.modelMapper = modelMapper;
  }

  public ResponseDto<List<ServiceInstruction>> getServiceInstructionsRepositoryFindByType(String type)
      throws CustomException {
    ResponseDto<List<ServiceInstruction>> responseDto;

    List<ServiceInstruction> serviceInstructionList = serviceInstructionsRepository.findByType(type);

    if (serviceInstructionList.isEmpty()) {
      throw new CustomException("해당 안내사항을 찾을 수 없습니다.", "400");
    }

    responseDto = new ResponseDto<>(serviceInstructionList, "이용시 안내사항을 참고해 주세요.");

    return responseDto;
  }

  @Transactional
  public ResponseDto<String> insertServiceInstructionOne(ServiceInstructionDto serviceInstructionsDto)
      throws CustomException {

    serviceInstructionsRepository
        .findByTitle(serviceInstructionsDto.getTitle())
        .ifPresent(val -> {
          throw new CustomException("해당 제목의 안내사항이 존재합니다. 내용을 수정해 주세요.", "409");
        });

    serviceInstructionsRepository
        .findByContent(serviceInstructionsDto.getContent())
        .ifPresent(val -> {
          throw new CustomException("해당 내용의 안내사항이 존재합니다. 제목을 수정해 주세요.", "409");
        });

    ServiceInstruction serviceInstruction = modelMapper.map(serviceInstructionsDto, ServiceInstruction.class);

    ServiceInstruction savedInstruction = serviceInstructionsRepository.save(serviceInstruction);

    if (savedInstruction == null || savedInstruction.getId() == null) {
      throw new CustomException("서버 오류로 인해 안내사항이 추가되지 않았습니다. 잠시 후 다시 시도해 주세요.", "500");
    }

    return new ResponseDto<>("", "안내사항이 추가되었습니다.");
  }

  @Transactional
  public ResponseDto<String> updateServiceInstructionOne(ServiceInstructionDto serviceInstructionsDto)
      throws CustomException {
    ServiceInstruction prevSiDto = serviceInstructionsRepository.findById(serviceInstructionsDto.getId())
        .orElseThrow(() -> new CustomException("수정하려는 안내사항을 찾을 수 없습니다.", "400"));

        /*
          * orElseThrow 
          * Optional 객체가 값을 포함하고 있을 때는 그 값을 반환하고, 값이 없을 경우(null일 경우) 지정된 예외를 발생
        */
        
    prevSiDto.setContent(serviceInstructionsDto.getContent());
    serviceInstructionsRepository.save(prevSiDto);

    return new ResponseDto<>("", "안내사항이 수정되었습니다.");
  }

  @Transactional
  public ResponseDto<String> deleteServiceInstructionOne(String id) throws CustomException {
    if (!serviceInstructionsRepository.existsById(id)) {
      throw new CustomException("삭제하려는 안내사항을 찾을 수 없습니다.", "400");
    }

    serviceInstructionsRepository.deleteById(id);
    return new ResponseDto<>("", "안내사항이 삭제되었습니다.");
  }

}
