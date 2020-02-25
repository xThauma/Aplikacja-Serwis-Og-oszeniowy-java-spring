package com.ProjectService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Category;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.repository.CategoryRepository;
import com.ProjectService.repository.CommentRepository;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;
import com.ProjectService.utils.StringUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void addCategory(Category category) {
		categoryRepository.save(category);
		
	}

	@Override
	public List<Category> findAll() {
		List<Category> list = (List<Category>) categoryRepository.findAll();
		return list;
	}
	
	



}
