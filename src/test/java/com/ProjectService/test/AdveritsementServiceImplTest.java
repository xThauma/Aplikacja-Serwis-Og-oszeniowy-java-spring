package com.ProjectService.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Category;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Usero;
import com.ProjectService.service.AdvertisementService;

public class AdveritsementServiceImplTest {

//	@Autowired
//	private AdvertisementService advertisementService;
//
//	private static AbstractApplicationContext context;
//	private static AdveritsementServiceImplTest adveritsementServiceImplTest;
//
//	@BeforeClass
//	public static void init() {
//		context = new ClassPathXmlApplicationContext(new String[] { "beans-test.xml" });
//		context.registerShutdownHook();
//		adveritsementServiceImplTest = context.getBean(AdveritsementServiceImplTest.class);
//	}
//
//	@Test
//	public void test1() {
//		Usero user = new Usero("kamil", "qwe", "qwe@gmail.com");
//		Comment comment = new Comment("To jest komentarz", 5, user);
//		Category category = new Category("Komedia");
//		Advertisement advertisement = new Advertisement("Nowy temat", "Srodek", user, category);
//		advertisementService.addAdvertisement(advertisement);
//		assertEquals(1, advertisementService.findAll());
//	}
//
//	@Test
//	public void test2() {
//		Usero user = new Usero("kamil", "qwe", "qwe@gmail.com");
//		Comment comment = new Comment("To jest komentarz", 5, user);
//		Category category = new Category("Komedia");
//		Advertisement advertisement = new Advertisement("Nowy temat", "Srodek", user, category);
//		advertisementService.addAdvertisement(advertisement);
//		advertisementService.removeAdvertisement(advertisement);
//		assertEquals(0, advertisementService.findAll());
//	}
//
//	@Test
//	public void test3() {
//		Usero user = new Usero("kamil", "qwe", "qwe@gmail.com");
//		Comment comment = new Comment("To jest komentarz", 5, user);
//		Category category = new Category("Komedia");
//		Advertisement advertisement = new Advertisement("Nowy temat", "Srodek", user, category);
//		advertisementService.addComment(comment, advertisement);
//		assertEquals(1, advertisementService.findAllByCategory(category));
//	}
//
//	@Test
//	public void test4() {
//		Usero user = new Usero("kamil", "qwe", "qwe@gmail.com");
//		Comment comment = new Comment("To jest komentarz", 5, user);
//		Category category = new Category("Komedia");
//		Advertisement advertisement = new Advertisement("Nowy temat", "Srodek", user, category);
//		advertisementService.changeTitle(advertisement, "Nowosc");
//		assertEquals("Nowosc", advertisement.getTitle());
//	}

}
