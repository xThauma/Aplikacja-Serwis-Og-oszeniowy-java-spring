package com.ProjectService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ProjectService.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
