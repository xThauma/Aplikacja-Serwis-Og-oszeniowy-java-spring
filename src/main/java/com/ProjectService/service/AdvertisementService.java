package com.ProjectService.service;

import java.util.List;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Category;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.service.UserService.IllegalUsernameException;

public interface AdvertisementService {
	class IllegalTextException extends RuntimeException {
	}

	void addComment(Comment comment, Advertisement advertisement) throws IllegalTextException;

	void addAdvertisement(Advertisement advertisement) throws IllegalTextException;

	List<Advertisement> getAdvertisementsByUser(Usero user);

	List<Advertisement> findAll();

	List<Comment> findAllCommentsByAdvertisement(Advertisement advertisement);

	void removeAdvertisement(Advertisement advertisement) throws IllegalUsernameException;

	void changeTitle(Advertisement advertisement, String title);

	void changeContent(Advertisement advertisement, String content);

	void changeCategory(Advertisement advertisement, Category category);

	List<Advertisement> findAllByUser(Usero user);

	List<Advertisement> findAllByCategory(Category category);
	
	List<Advertisement> findAllNotAccepted();

}
