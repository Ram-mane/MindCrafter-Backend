package com.user.bloggs.services.impl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.Category;
import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;
import com.user.bloggs.exceptions.ResourceNotFoundException;
import com.user.bloggs.payloads.PostDto;
import com.user.bloggs.payloads.PostResponse;
import com.user.bloggs.repository.CategoryRepo;
import com.user.bloggs.repository.PostRepo;
import com.user.bloggs.repository.UserRepo;
import com.user.bloggs.services.PostServices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

@Service

public class PostServicesImpl implements PostServices {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.modelmapper.map(postDto, Post.class);
		 
		post.setImageName("Default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelmapper.map(newPost, PostDto.class);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException("Post","postId",postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
//		post.setAddedDate(new Date());
		post.setImageName(postDto.getImageName());
//		post.setCategory(postDto.getCategory());
		
		Post updatedPost = this.postRepo.save(post);
		return modelmapper.map(updatedPost,PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) {

		Post post =this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelmapper.map(post,PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		
		Sort  sort= null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}else {
			 	
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		
//		List<Post> posts = this.postRepo.findAll();
//		List<PostDto> postDtos = posts.stream().map(post -> this.modelmapper.map(posts,PostDto.class)).collect(Collectors.toList());
		List<PostDto> postDto = mapPostListToDto(pagePost.getContent());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post =this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getPostsByUser(User userId, Integer pageNumber, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> postPage = this.postRepo.findByUser(userId, pageable);
			
		List<PostDto> post = mapPostListToDto(postPage.getContent());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(post);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElement(postPage.getTotalElements());
		postResponse.setLastPage(postPage.isLast());
		
		return postResponse ;
	}
	
	@Override
	public List<PostDto> searchPostByTitle(String keyword) {
		List<Post> post = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = mapPostListToDto(post);
		return postDto;
	}

	@Override
	public PostResponse getPostsByCategory(Category categoryId, Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> postPage = this.postRepo.findByCategory(categoryId, pageable);
//				postPage.getContent();
		List<PostDto> post = mapPostListToDto(postPage.getContent());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(post);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElement(postPage.getTotalElements());
		postResponse.setLastPage(postPage.isLast());
		return postResponse;
	}
	
	 private List<PostDto> mapPostListToDto(List<Post> posts) {
		 
	        return posts.stream().map(post -> modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
	                
	                
	    }

	

	

}
