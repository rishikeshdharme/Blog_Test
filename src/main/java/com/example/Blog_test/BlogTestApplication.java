package com.example.Blog_test;

import com.example.Blog_test.config.AppConstant;
import com.example.Blog_test.entity.Role;
import com.example.Blog_test.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.List;

@SpringBootApplication
public class BlogTestApplication implements CommandLineRunner {

	@Autowired
	private  RoleRepository roleRepository;

	public static void main(String[] args) {

		SpringApplication.run(BlogTestApplication.class, args);


	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		// This method can be used to execute code after the application has started
		// For example, you can initialize some data or perform some checks
		try{

			Role role1 = new Role();
			role1.setId(AppConstant.ADMIN_USER);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstant.NORMAL_USER);
			role2.setName("ROLE_NORMAL");

			List<Role> saveroles = List.of(role1, role2);
			List<Role> roles = roleRepository.saveAll(saveroles);

			roles.forEach(role -> {
				System.out.println("Role saved: " + role);
			});

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
