package com.user.bloggs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.user.bloggs.entity.Category;
import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;
import com.user.bloggs.payloads.ApiResponse;
import com.user.bloggs.payloads.PostDto;
import com.user.bloggs.payloads.PostResponse;
import com.user.bloggs.services.FileServices;
import com.user.bloggs.services.PostServices;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")

public class PostController {

	@Autowired
	private PostServices postServices;
	
	@Autowired
	private FileServices fileServices;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts/")
	public ResponseEntity<?> createPost(@RequestBody PostDto postDto,@PathVariable("userId") Integer userId,@PathVariable("categoryId") Integer categoryId) {
		PostDto createdPost = this.postServices.createPost(postDto, userId, categoryId);
		ApiResponse apiResponse = new ApiResponse("Post created Succesfully !", true);
		Map<String, Object> map =new HashMap<>();
		map.put("Your post", createdPost);
		map.put("Response", apiResponse);
		
				
		return new ResponseEntity<>(map,HttpStatus.CREATED);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId){
		PostDto updatedPost = this.postServices.updatePost(postDto, postId);
		ApiResponse apiResponse = new ApiResponse("Post updated Succesfully !", true);
		Map<String, Object> map =new HashMap<>();
		map.put("Your post", updatedPost);
		map.put("Response", apiResponse);
		
				
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	@GetMapping("/posts/Search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
		
		List<PostDto> posts = this.postServices.searchPostByTitle(keyword);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto getPost = this.postServices.getPostById(postId);
		return new ResponseEntity<PostDto>(getPost,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId){
		this.postServices.deletePost(postId);
		ApiResponse response = new ApiResponse("Posts deleted succesfully !", true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable User userId,@RequestParam(value = "pageNumber", defaultValue="0",required =false) Integer pageNumber,
			 @RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize){
		PostResponse getPostByUser = this.postServices.getPostsByUser(userId,pageNumber,pageSize);
		return  ResponseEntity.ok(getPostByUser);
	}
	
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Category categoryId,@RequestParam(value = "pageNumber", defaultValue="0",required =false) Integer pageNumber,
			 @RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize){
		PostResponse getPostByCategory = this.postServices.getPostsByCategory(categoryId,pageNumber,pageSize);
		return  ResponseEntity.ok(getPostByCategory);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue="0",required =false) Integer pageNumber,
													 @RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
													 @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
													 @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
		
		return  ResponseEntity.ok(this.postServices.getAllPosts(pageNumber, pageSize,sortBy,sortDir));
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image" ) MultipartFile image, @PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postServices.getPostById(postId);
		
		String fileName = this.fileServices.uploadeImage(path, image);
		
		postDto.setImageName(fileName);
		
		PostDto updatedPostDto= this.postServices.updatePost(postDto, postId);
		 return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
		
	}
	
	@GetMapping("/posts/image/download/{imageName}")
	public void downloadImages(@PathVariable("imageName") String imageName,
								HttpServletResponse response) throws IOException {
		
		InputStream resource= this.fileServices.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
}
