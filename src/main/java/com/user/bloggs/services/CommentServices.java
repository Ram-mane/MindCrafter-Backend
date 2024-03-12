package com.user.bloggs.services;

import org.springframework.stereotype.Service;

import com.user.bloggs.payloads.CommentDto;

@Service
public interface CommentServices {

	CommentDto createComment(CommentDto commentDto , Integer postId , Integer userId);
	
//	void deleteCommentOfPost (Integer postId);
//	
//	void deleteCommentOfUser(Integer userId);
	
	void deleteComment(Integer commentId);
	
	
	
}
