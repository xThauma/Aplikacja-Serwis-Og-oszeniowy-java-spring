package com.ProjectService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ProjectService.model.Usero;

@Repository
public interface UserRepository extends CrudRepository<Usero, Long> {

}
