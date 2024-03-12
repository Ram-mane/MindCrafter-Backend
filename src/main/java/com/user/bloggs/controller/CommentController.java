package com.user.bloggs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bloggs.payloads.ApiResponse;
import com.user.bloggs.payloads.CommentDto;
import com.user.bloggs.services.CommentServices;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentServices commentServices;
	
	@PostMapping("/user/{userId}/posts/{postId}/comment")
	
	ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto ,@PathVariable Integer postId, @PathVariable Integer userId){
		
		CommentDto comments =this.commentServices.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(comments, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		
		this.commentServices.deleteComment(commentId);
		
		return new ResponseEntity<>(new ApiResponse("comment deleted succesfully !", true), HttpStatus.OK);
	}
}
