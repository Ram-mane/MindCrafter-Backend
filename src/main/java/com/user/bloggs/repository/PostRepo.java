package com.user.bloggs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.user.bloggs.entity.Category;
import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	
	 Page<Post> findByUser(User user, Pageable pageable);
	 Page<Post> findByCategory(Category category, Pageable pageable);
	 
	 Page<Post> findAll(Pageable pageable);
	 
	 
	 List<Post> findByTitleContaining(String keyword);
}
