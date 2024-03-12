package com.user.bloggs.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.Category;
import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;
import com.user.bloggs.payloads.PostDto;
import com.user.bloggs.payloads.PostResponse;

@Service
public interface PostServices {

	
	// create post
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	PostDto getPostById(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
	
	void deletePost(Integer postId);
	
	PostResponse getPostsByUser(User userId, Integer pageNumber, Integer psgeSize);
	
	PostResponse getPostsByCategory(Category categoryId, Integer pageNumber, Integer psgeSize);
	
	List<PostDto> searchPostByTitle(String keyword);
	

	
	
	
}
