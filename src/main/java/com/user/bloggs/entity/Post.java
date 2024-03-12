package com.user.bloggs.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_id")
	private int postId;
	
	@Column(name = "post_title", nullable = false)
	private String title;
	
	@Column(name="content", length = 10000)
	private String content; 
	
	private String imageName;
	
	@Column(name="created_date")
	private Date addedDate;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn( name = "user_id")
	private User user;
	
	
//	@JsonBackReference
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comment = new HashSet<>();
	
	
	
	
	
}
