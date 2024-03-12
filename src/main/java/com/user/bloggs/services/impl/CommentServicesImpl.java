package com.user.bloggs.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.Comment;
import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;
import com.user.bloggs.exceptions.ResourceNotFoundException;
import com.user.bloggs.payloads.CommentDto;
import com.user.bloggs.repository.CommentRepo;
import com.user.bloggs.repository.PostRepo;
import com.user.bloggs.repository.UserRepo;
import com.user.bloggs.services.CommentServices;

@Service
public class CommentServicesImpl implements CommentServices {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public void deleteComment(Integer commentId) {
		
		this.commentRepo.deleteById(commentId);

	}


	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Comment comment =this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

}
