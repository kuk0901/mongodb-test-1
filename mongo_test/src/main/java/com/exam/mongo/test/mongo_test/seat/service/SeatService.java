package com.exam.mongo.test.mongo_test.seat.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.mongo.test.mongo_test.dto.SeatDto;
import com.exam.mongo.test.mongo_test.exception.CustomException;
import com.exam.mongo.test.mongo_test.res.ResponseDto;
import com.exam.mongo.test.mongo_test.seat.domain.Seat;
import com.exam.mongo.test.mongo_test.seat.repository.SeatRepository;

@Service
public class SeatService {
  private SeatRepository seatRepository;
  private final ModelMapper modelMapper;
  private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

  public SeatService(SeatRepository seatRepository, ModelMapper modelMapper) {
    this.seatRepository = seatRepository;
    this.modelMapper = modelMapper;
  }

  public ResponseDto<List<SeatDto>> getSeatAll() {
    List<Seat> seatList = seatRepository.findAll();

    List<SeatDto> seatDtoList = seatList.stream()
        .map(seat -> modelMapper.map(seat, SeatDto.class))
        .toList();

    return new ResponseDto<>(seatDtoList, "좌석 데이터 반환");
  }

  @Transactional
  public ResponseDto<String> saveSeatOne(SeatDto seatDto) throws CustomException {

    Optional<Seat> existingSeatOpt = seatRepository.findBySeatNumber(seatDto.getSeatNumber());

    if (existingSeatOpt.isPresent()) {
      throw new CustomException("이미 존재하는 좌석 번호입니다.", "409");
    }

    Seat seat = modelMapper.map(seatDto, Seat.class);
    Seat savedSeat = seatRepository.save(seat);

    if (savedSeat == null || savedSeat.getId() == null) {
      throw new CustomException("서버 오류로 인해 좌석이 추가되지 않았습니다. 잠시 후 다시 시도해 주세요.",
          "500");
    }

    return new ResponseDto<>("", savedSeat.getSeatNumber() + " 좌석번호가 추가되었습니다.");
  }

  @Transactional
  public ResponseDto<String> updateSeatOne(SeatDto seatDto) throws CustomException {
    if (!seatRepository.existsById(seatDto.getId())) {
      throw new CustomException("수정하려는 좌석을 찾을 수 없습니다.", "400");
    }

    Seat seat = modelMapper.map(seatDto, Seat.class);
    seatRepository.save(seat);

    return new ResponseDto<>("", "좌석에 대한 정보가 수정되었습니다.");
  }

}
