package com.ProjectService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.repository.CommentRepository;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;
import com.ProjectService.utils.StringUtils;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRespository;


	@Override
	public void removeComment(Comment comment) throws IllegalUsernameException {
		commentRespository.delete(comment);

	}

	@Override
	public void changeRating(Comment comment, int rating) {
		comment.setRating(rating);
		commentRespository.save(comment);

	}

	@Override
	public void changeContent(Comment comment, String content) {
		comment.setContent(content);
		commentRespository.save(comment);

	}

}
