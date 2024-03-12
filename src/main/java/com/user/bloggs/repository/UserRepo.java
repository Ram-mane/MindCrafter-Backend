package com.user.bloggs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.user.bloggs.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

	
	Optional<User> findUserByUsername(String email);
	boolean existsByEmail(String email);
}
