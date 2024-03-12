package com.user.bloggs.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.user.bloggs.entity.Category;
import com.user.bloggs.entity.Comment;
import com.user.bloggs.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private int postId;
	private String title;
	
	private String content;
	
	private String imageName;
	
	private String addedDate;
	
	private Category category;
	
	private User user;
	
	private List<Comment> comment = new ArrayList<>();
	
}
