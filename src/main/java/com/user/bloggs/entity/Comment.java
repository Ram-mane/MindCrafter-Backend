package com.user.bloggs.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private String content;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	 
	@JsonIgnore 
	@ManyToOne
	@JoinColumn(name = "post_id") 
	private Post post;
}
