package com.tawfeek.quizApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class ClassRoomApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassRoomApplication.class, args);
	}

}
