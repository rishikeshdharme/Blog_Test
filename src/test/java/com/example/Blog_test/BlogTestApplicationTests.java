package com.example.Blog_test;

import com.example.Blog_test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogTestApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest(){
		String name = this.userRepository.getClass().getName();
		System.out.println(name);
		System.out.println(this.userRepository.getClass().getPackageName());
	}

}
