package com.ProjectService.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ProjectService.model.Role;
import com.ProjectService.model.Usero;
import com.ProjectService.service.UserService;
import com.ProjectService.service.UserServiceImpl;
import com.ProjectService.service.UserService.IllegalUsernameException;
import com.ProjectService.service.UserService.IncorrectEmailException;
import com.ProjectService.utils.StringUtils;
import javafx.css.Styleable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Controller
public class Login {

	public Login() {
	}

	public void initLoginWindow(Stage stage) {
		UserService userService = new UserServiceImpl();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		context.registerShutdownHook();
		context.getAutowireCapableBeanFactory().autowireBean(userService);
		stage.setTitle("Panel uwieżytelnienia");
		GridPane grid = new GridPane();
		grid.setId("root");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Logowanie");
		scenetitle.setId("title");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("Użytkownik");

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Hasło:");

		PasswordField pwBox = new PasswordField();

		Button registerBtn = new Button("Zarejestruj");
		registerBtn.setOnAction(e -> {
			Register r = new Register();
			r.initRegisterWindow(stage);
		});
		Button loginBtn = new Button("Zaloguj");
		loginBtn.setOnAction(e -> {
			try {
				Usero user = userService.verifyUser(pwBox.getText().toString(), userTextField.getText().toString());
				if (user != null) {
					StringUtils.currentUser = user;
					MainScreen us = new MainScreen();
					us.initMainScreen(stage);

				} else {
					StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Niewłaściwe dane");
				}
			} catch (IllegalUsernameException iue) {
				StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Niewłaściwy email");
			} catch (IncorrectEmailException ie) {
				StringUtils.createAlertBox(AlertType.ERROR, "Błąd", null, "Niewłaściwa nazwa użytkownika");
			}
		});

		HBox hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_RIGHT);
		hBox.getChildren().addAll(registerBtn, loginBtn);

		grid.add(userName, 0, 1);
		grid.add(pw, 0, 2);
		grid.add(pwBox, 1, 2);
		grid.add(hBox, 1, 4);

		Scene scene = new Scene(grid, 300, 275);
		scene.getStylesheets().add("static/stylesheet.css");
		stage.setScene(scene);

	}

}
