package com.checkpoint.StudentsCourses;

import com.checkpoint.StudentsCourses.model.Admin;
import com.checkpoint.StudentsCourses.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class StudentsCoursesApplication {
	private static final Logger logger = LoggerFactory.getLogger(StudentsCoursesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudentsCoursesApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		logger.info("Init Admin credentials in application startup");

		return args -> {
			String email = "admin@gmail.com";
			String password = "admin_password";

			if (adminRepository.findByEmail(email).isEmpty()) {
				Admin admin = new Admin();
				admin.setEmail(email);
				admin.setPassword(passwordEncoder.encode(password));
				adminRepository.save(admin);
				System.out.println("Default admin created - email: " + email + ", password: " + password);
			}
			else {
				System.out.println("Admin already exists in DB");
			}
		};
	}
}
