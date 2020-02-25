package com.ProjectService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Category;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.repository.AdvertisementRepository;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;
import com.ProjectService.utils.StringUtils;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	AdvertisementRepository advertisementRepository;

	@Override
	public void addComment(Comment comment, Advertisement advertisement) throws IllegalUsernameException {
		if (StringUtils.containsAKeyword(comment.getContent().toLowerCase(), StringUtils.forbiddenWords))
			throw new IllegalTextException();
		advertisement.addComment(comment);
		advertisementRepository.save(advertisement);

	}

	@Override
	public void addAdvertisement(Advertisement advertisement) throws IllegalUsernameException, IncorrectEmailException {
		if (StringUtils.containsAKeyword(advertisement.getContent().toLowerCase(), StringUtils.forbiddenWords))
			throw new IllegalTextException();
		advertisementRepository.save(advertisement);
	}

	@Override
	public List<Advertisement> getAdvertisementsByUser(Usero user) {
		Iterable<Advertisement> adverts = advertisementRepository.findAll();
		return (List<Advertisement>) adverts;
	}

	@Override
	public void removeAdvertisement(Advertisement advertisement) {
		advertisementRepository.delete(advertisement);
	}

	@Override
	public void changeTitle(Advertisement advertisement, String title) {
		advertisement.setTitle(title);
		advertisementRepository.save(advertisement);

	}

	@Override
	public void changeContent(Advertisement advertisement, String content) {
		advertisement.setContent(content);
		advertisementRepository.save(advertisement);

	}

	@Override
	public void changeCategory(Advertisement advertisement, Category category) {
		advertisement.setCategory(category);
		advertisementRepository.save(advertisement);

	}

	@Override
	public List<Advertisement> findAll() {
		List<Advertisement> list = (List<Advertisement>) advertisementRepository.findAll();
		List<Advertisement> list2 = new ArrayList<>();
		for (Advertisement l1 : list) {
			if (l1.isAccepted()) {
				list2.add(l1);
			}
		}
		return list2;
	}

	@Override
	public List<Comment> findAllCommentsByAdvertisement(Advertisement advertisement) {
		List<Comment> listWithComments = new ArrayList<>();
		for (int i = 0; i < advertisement.getComments().size(); i++) {
			listWithComments.add(advertisement.getComments().get(i));
		}

		return listWithComments;
	}

	@Override
	public List<Advertisement> findAllByUser(Usero user) {
		List<Advertisement> list = (List<Advertisement>) advertisementRepository.findAll();
		List<Advertisement> list2 = new ArrayList<>();
		for (Advertisement l1 : list) {
			if ((l1.getAuthor().getUsername().equals(user.getUsername())) && l1.isAccepted()) {
				list2.add(l1);
			}
		}
		return list2;
	}

	@Override
	public List<Advertisement> findAllByCategory(Category category) {
		List<Advertisement> list = (List<Advertisement>) advertisementRepository.findAll();
		List<Advertisement> list2 = new ArrayList<>();
		for (Advertisement l1 : list) {
			if ((l1.getCategory().getCategory().equals(category.getCategory())) && l1.isAccepted()) {
				list2.add(l1);
			}
		}
		return list2;
	}

	@Override
	public List<Advertisement> findAllNotAccepted() {
		List<Advertisement> list = (List<Advertisement>) advertisementRepository.findAll();
		List<Advertisement> list2 = new ArrayList<>();
		for (Advertisement l1 : list) {
			if (!l1.isAccepted()) {
				list2.add(l1);
			}
		}
		return list2;
	}

}
