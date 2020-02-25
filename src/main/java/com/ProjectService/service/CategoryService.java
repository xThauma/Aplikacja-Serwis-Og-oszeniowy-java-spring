package com.ProjectService.service;

import java.util.List;

import com.ProjectService.model.Category;

public interface CategoryService {
	void addCategory(Category category);

	List<Category> findAll();

}
