package com.ProjectService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ProjectService.model.Advertisement;

@Repository
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

}
