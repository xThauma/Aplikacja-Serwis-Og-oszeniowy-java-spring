package com.ProjectService.presentation;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.ProjectService.model.Usero;
import com.ProjectService.service.UserService;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;
import com.ProjectService.service.UserServiceImpl;
import com.ProjectService.utils.EmailUtil;
import com.ProjectService.utils.StringUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Controller
public class Register {

	Session mailSession;

	public Register() {
	}

	public void initRegisterWindow(Stage stage) {
		UserService userService = new UserServiceImpl();
//        //Create the application context
//        ApplicationContext context2 = new FileSystemXmlApplicationContext
//        		  ("applicationContext.xml");
//          
//        //Get the mailer instance
//        EmailService mailer = (EmailService) context2.getBean("emailService");

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		context.registerShutdownHook();
		context.getAutowireCapableBeanFactory().autowireBean(userService);

		stage.setTitle("Panel rejestracji");
		GridPane grid = new GridPane();
		grid.setId("root");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Rejestracja");
		scenetitle.setId("title");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("Użytkownik:");

		TextField userTextField = new TextField();

		Label pw = new Label("Hasło:");

		PasswordField pwBox = new PasswordField();

		Label emailLabel = new Label("Email:");

		TextField emailTextField = new TextField();

		Button registerBtn = new Button("Zarejestruj");
		registerBtn.setOnAction(e -> {
			String username = userTextField.getText().toString();
			String password = pwBox.getText().toString();
			String email = emailTextField.getText().toString();
			System.out.println(username + " " + password + " " + email);
			Usero usero = new Usero(username, password, email);
			try {
				Thread thread = new Thread() {
					public void run() {
						EmailUtil.setMailServerProperties(email, "Aktywacja konta", "Nowe konto zostało utworzone");
					}
				};

				thread.start();
				userService.addUser(usero);
				Login l = new Login();
				l.initLoginWindow(stage);
				StringUtils.createAlertBox(AlertType.INFORMATION, "Stworzono nowego użytkownika", "", "Stworzono nowego użytkownika");
			} catch (IllegalUsernameException eo) {
				StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Niewłaściwy email");
			} catch (IncorrectEmailException ie) {
				StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Niewłaściwa nazwa użytkownika");
			} catch (Exception npe) {
				StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Jakiś inny błąd");
				npe.printStackTrace();
			}
			List<Usero> listo = userService.findAll();
			listo.forEach(System.out::println);
		});
		Button backBtn = new Button("Cofnij");
		backBtn.setOnAction(e -> {
			Login l = new Login();
			l.initLoginWindow(stage);
		});
		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_RIGHT);
		hBox.getChildren().addAll(backBtn, registerBtn);

		grid.add(userName, 0, 1);
		grid.add(userTextField, 1, 1);
		grid.add(pw, 0, 2);
		grid.add(pwBox, 1, 2);
		grid.add(emailLabel, 0, 3);
		grid.add(emailTextField, 1, 3);

		grid.add(hBox, 1, 4);

		Scene scene = new Scene(grid, 300, 275);
		scene.getStylesheets().add("static/stylesheet.css");
		stage.setScene(scene);
	}

}
