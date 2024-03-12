package com.user.bloggs.payloads;

import com.user.bloggs.entity.Post;
import com.user.bloggs.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

	private int id;
	private String content;
	
}
