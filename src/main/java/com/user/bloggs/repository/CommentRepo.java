package com.user.bloggs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.bloggs.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
