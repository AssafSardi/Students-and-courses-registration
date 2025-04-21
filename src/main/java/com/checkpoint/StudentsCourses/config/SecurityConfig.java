//package com.checkpoint.StudentsCourses.config;
//
//import com.checkpoint.StudentsCourses.model.Admin;
//import com.checkpoint.StudentsCourses.model.Student;
//import com.checkpoint.StudentsCourses.repository.AdminRepository;
//import com.checkpoint.StudentsCourses.repository.StudentRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.List;
//import java.util.Optional;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/student/**").hasRole("STUDENT")
//                                .requestMatchers("/", "/logout").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(AdminRepository adminRepository, StudentRepository studentRepository) {
//        return email -> {
//            // check if it is an admin
//            Optional<Admin> adminOpt = adminRepository.findByEmail(email);
//            if (adminOpt.isPresent()) {
//                Admin admin = adminOpt.get();
//                return new User(admin.getEmail(), admin.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
//            }
//
//            // check if it is a student
//            Optional<Student> studentOpt = studentRepository.findByEmail(email);
//            if (studentOpt.isPresent()) {
//                Student student = studentOpt.get();
//                return new User(student.getEmail(), student.getSpecialKey(), List.of(new SimpleGrantedAuthority("ROLE_STUDENT")));
//            }
//
//            throw new UsernameNotFoundException("Not found user with email: " + email);
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
