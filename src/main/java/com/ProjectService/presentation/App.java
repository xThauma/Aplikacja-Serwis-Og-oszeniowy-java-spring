package com.ProjectService.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.ProjectService.model.Advertisement;
import com.ProjectService.model.Category;
import com.ProjectService.model.Comment;
import com.ProjectService.model.Role;
import com.ProjectService.model.Usero;
import com.ProjectService.service.AdvertisementService;
import com.ProjectService.service.AdvertisementServiceImpl;
import com.ProjectService.service.CategoryService;
import com.ProjectService.service.CategoryServiceImpl;
import com.ProjectService.service.CommentService;
import com.ProjectService.service.CommentServiceImpl;
import com.ProjectService.service.UserService;
import com.ProjectService.service.UserServiceImpl;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

@Component
public class App extends Application {

	private AdvertisementService advertisementService;
	private Login login;
	private UserService userService;
	private CategoryService categoryService;
	private CommentService commentService;
	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		launch(App.class, args);
	}


	@Override
	public void start(Stage stage) throws Exception {
		login = new Login();
		userService = new UserServiceImpl();
		advertisementService = new AdvertisementServiceImpl();
		categoryService = new CategoryServiceImpl();
		commentService = new CommentServiceImpl();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		context.registerShutdownHook();
		App app = context.getBean(App.class);
		context.getAutowireCapableBeanFactory().autowireBean(login);
		context.getAutowireCapableBeanFactory().autowireBean(userService);
		context.getAutowireCapableBeanFactory().autowireBean(advertisementService);
		context.getAutowireCapableBeanFactory().autowireBean(categoryService);
		context.getAutowireCapableBeanFactory().autowireBean(commentService);
		initBasicData();
		login.initLoginWindow(stage);

		stage.show();
	}

	private void initBasicData() {
		Usero user1 = new Usero("kamil", "qwe", "kamil4800@gmail.com");
		Usero user2 = new Usero("patryk", "qwe", "kamil4800@gmail.com");
		Usero user3 = new Usero("krzys", "qwe", "kamil4800@gmail.com");
		userService.addUser(user1);
		userService.addUser(user2);
		userService.addUser(user3);
		userService.changeRoleUser(user2, Role.MODERATOR);
		userService.changeRoleUser(user3, Role.ADMIN);

		Category c1 = new Category("Dramat");
		Category c2 = new Category("Komedia");
		Category c3 = new Category("Horror");

		categoryService.addCategory(c1);
		categoryService.addCategory(c2);
		categoryService.addCategory(c3);

		Advertisement advertisement1 = new Advertisement("To jest temat artykulu nr 1",
				"These were the two halves of one single enigmatical entity, and even though either of them was perfectly capable of acting on his own just as if he had been fully independent, independent they were not. They were bound together by an eternal bond that could not be broken, and their destiny was one.",
				user1, c2);
		advertisement1.setAccepted(true);
		Advertisement advertisement2 = new Advertisement("To jest temat artykulu nr 2",
				"for he was driven by the need to create and give life. He was overflowing with creative power and impatient to release it, so he stepped into existence and began to unleash his powers. However, none of his attempts to create were successful. All of his creations were swallowed by the void before they were completed, and none survived.",
				user2, c1);
		advertisement2.setAccepted(true);
		Advertisement advertisement3 = new Advertisement("To jest temat artykulu nr 3", "test3", user3, c3);
		advertisement3.setAccepted(true);
		Advertisement advertisement4 = new Advertisement("To jest temat artykulu nr 4", "test4", user3, c3);
		Advertisement advertisement5 = new Advertisement("To jest temat artykulu nr 5", "test4", user3, c3);
		advertisement5.setAccepted(true);
		advertisementService.addAdvertisement(advertisement1);
		advertisementService.addAdvertisement(advertisement2);
		advertisementService.addAdvertisement(advertisement3);
		advertisementService.addAdvertisement(advertisement4);
		advertisementService.addAdvertisement(advertisement5);

		Comment comment1 = new Comment("komentarz 1", 1, user1);
		Comment comment2 = new Comment("komentarz 2", 4, user1);
		Comment comment3 = new Comment("komentarz 3", 8, user1);
		Comment comment4 = new Comment("komentarz 4", 1, user2);
		Comment comment5 = new Comment("komentarz 5", 1, user3);
		Comment comment6 = new Comment("komentarz 6", 1, user3);

		advertisementService.addComment(comment1, advertisement1);
		advertisementService.addComment(comment2, advertisement1);
		advertisementService.addComment(comment3, advertisement1);
		advertisementService.addComment(comment6, advertisement1);
		advertisementService.addComment(comment4, advertisement2);
		advertisementService.addComment(comment5, advertisement3);

		final ObservableList<Comment> dataComments = FXCollections.observableArrayList(advertisementService.findAllCommentsByAdvertisement(advertisement1));
		dataComments.forEach(System.out::println);
		logger.error("Hello from Log4j 2");

	}

}
