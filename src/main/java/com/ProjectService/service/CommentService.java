package com.ProjectService.service;

import java.util.List;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;

public interface CommentService {
	class IllegalTextException extends RuntimeException {
	}

	void removeComment(Comment comment);

	void changeRating(Comment comment, int rating);

	void changeContent(Comment comment, String content);

}
