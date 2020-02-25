package com.ProjectService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ProjectService.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

}
