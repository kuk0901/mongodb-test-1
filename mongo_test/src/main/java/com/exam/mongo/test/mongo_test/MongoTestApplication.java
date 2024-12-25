package com.exam.mongo.test.mongo_test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoTestApplication {

	private static final Logger logger = LoggerFactory.getLogger(MongoTestApplication.class);
 
	public static void main(String[] args) {
		SpringApplication.run(MongoTestApplication.class, args);
		logger.info("잘 돌아가는지 ㄹㅇ로 테스트 ㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂㅈㅂ");
	}
}
