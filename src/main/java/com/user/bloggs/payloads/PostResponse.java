package com.user.bloggs.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElement;
	
	private boolean lastPage;
}
