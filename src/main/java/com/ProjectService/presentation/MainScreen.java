package com.ProjectService.presentation;

import java.util.ArrayList;
import java.util.List;

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
import com.ProjectService.utils.EmailUtil;
import com.ProjectService.utils.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

@Component
public class MainScreen {

	public MainScreen() {
	}

	private TableView table = new TableView();
	private TableView tableComments = new TableView();
	private TableView tableNotAccepted = new TableView();
	private UserService userService;
	private CommentService commentService;
	private AdvertisementService advertisementService;
	private CategoryService categoryService;
	private ObservableList<Comment> dataComments;
	private ObservableList<Advertisement> data;
	private ObservableList<Advertisement> dataNotAccepted = FXCollections.observableArrayList();;
	private ObservableList<Category> categoryListToAdd;
	private ComboBox categoryCcomboBox;

	public void initMainScreen(Stage stage) {
		userService = new UserServiceImpl();
		commentService = new CommentServiceImpl();
		advertisementService = new AdvertisementServiceImpl();
		categoryService = new CategoryServiceImpl();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
		context.registerShutdownHook();
		context.getAutowireCapableBeanFactory().autowireBean(userService);
		context.getAutowireCapableBeanFactory().autowireBean(commentService);
		context.getAutowireCapableBeanFactory().autowireBean(advertisementService);
		context.getAutowireCapableBeanFactory().autowireBean(categoryService);
		stage.setTitle("Panel uwieżytelnienia");

		BorderPane vBox1 = new BorderPane();
		initVBox1(stage, vBox1);

		BorderPane bp1 = new BorderPane();
		initBorderPane1(stage, bp1);

		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Ogłoszenia", vBox1);
		Tab tab2 = new Tab("Tworzenie Ogłoszenia", bp1);

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);

		if (!(StringUtils.currentUser.getRole() == Role.UZYTKOWNIK)) {
			VBox vBox2 = new VBox();
			initVBox2(stage, vBox2);
			Tab tab5 = new Tab("Akceptacja Atrykułów", vBox2);
			tabPane.getTabs().add(tab5);
		}

		if (!(StringUtils.currentUser.getRole() == Role.UZYTKOWNIK)) {
			GridPane grid1 = new GridPane();
			initGridPane1(stage, grid1);
			Tab tab3 = new Tab("Tworzenie Kategorii", grid1);
			tabPane.getTabs().add(tab3);
		}

		if ((StringUtils.currentUser.getRole() == Role.ADMIN)) {
			GridPane grid2 = new GridPane();
			initGridPane2(stage, grid2);
			Tab tab4 = new Tab("Przypisanie ról", grid2);
			tabPane.getTabs().add(tab4);
		}

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		VBox vBox = new VBox(tabPane);

		Scene scene = new Scene(vBox, 1200, 800);
		stage.setX(100);
		stage.setY(100);
		scene.getStylesheets().add("static/stylesheet.css");
		stage.setScene(scene);
	}

	private void initVBox2(Stage stage, VBox vBox) {
		vBox.setPadding(new Insets(25, 25, 25, 25));

		TableColumn title = new TableColumn("Tytuł");
		title.setMinWidth(250);
		title.setStyle("-fx-alignment: CENTER;");
		TableColumn content = new TableColumn("Treść");
		content.setMinWidth(700);
		content.setMaxWidth(700);
		content.setStyle("-fx-alignment: CENTER;");
		TableColumn category = new TableColumn("Kategoria");
		category.setMinWidth(100);
		category.setStyle("-fx-alignment: CENTER;");
		TableColumn author = new TableColumn("Autor");
		author.setMinWidth(100);
		author.setStyle("-fx-alignment: CENTER;");
		TableColumn accept = new TableColumn("Akceptacja");
		accept.setMinWidth(100);
		accept.setStyle("-fx-alignment: CENTER;");

		TableColumn delete = new TableColumn("Usuń");
		delete.setMinWidth(100);
		delete.setStyle("-fx-alignment: CENTER;");

		title.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("title"));
		content.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("content"));
		category.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("category"));
		author.setCellValueFactory(new PropertyValueFactory<Advertisement, Usero>("author"));

		accept.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("DUMMY"));
		Callback<TableColumn<Advertisement, String>, TableCell<Advertisement, String>> cellFactory = //
				new Callback<TableColumn<Advertisement, String>, TableCell<Advertisement, String>>() {
					@Override
					public TableCell call(final TableColumn<Advertisement, String> param) {
						final TableCell<Advertisement, String> cell = new TableCell<Advertisement, String>() {

							final Button btn = new Button("Zaakceptuj");

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										Advertisement newAdvertisement = getTableView().getItems().get(getIndex());
										newAdvertisement.setAccepted(true);
										advertisementService.addAdvertisement(newAdvertisement);
										dataNotAccepted.removeAll(dataNotAccepted);
										dataNotAccepted = FXCollections.observableArrayList(advertisementService.findAllNotAccepted());
										tableNotAccepted.setItems(dataNotAccepted);
										data.removeAll(data);
										data = FXCollections.observableArrayList(advertisementService.findAll());
										table.setItems(data);
										StringUtils.createAlertBox(AlertType.INFORMATION, "Artykuł", "", "Artykuł zaakceptowany");
									});
									setGraphic(btn);
									setText(null);
								}
							}
						};
						return cell;
					}
				};

		accept.setCellFactory(cellFactory);

		delete.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("DUMMY"));
		Callback<TableColumn<Advertisement, String>, TableCell<Advertisement, String>> cellFactory2 = //
				new Callback<TableColumn<Advertisement, String>, TableCell<Advertisement, String>>() {
					@Override
					public TableCell call(final TableColumn<Advertisement, String> param) {
						final TableCell<Advertisement, String> cell = new TableCell<Advertisement, String>() {

							final Button btn = new Button("Usuń");

							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										Advertisement newAdvertisement = getTableView().getItems().get(getIndex());
										advertisementService.removeAdvertisement(newAdvertisement);
										dataNotAccepted.removeAll(dataNotAccepted);
										dataNotAccepted = FXCollections.observableArrayList(advertisementService.findAllNotAccepted());
										tableNotAccepted.setItems(dataNotAccepted);
									});
									setGraphic(btn);
									setText(null);
								}
							}
						};
						return cell;
					}
				};

		delete.setCellFactory(cellFactory2);

		dataNotAccepted = FXCollections.observableArrayList(advertisementService.findAllNotAccepted());
		tableNotAccepted.setItems(dataNotAccepted);

		tableNotAccepted.getColumns().addAll(title, category, author, accept, delete, content);
		vBox.getChildren().addAll(tableNotAccepted);

	}

	private void initGridPane2(Stage stage, GridPane grid) {
		grid.setId("root");
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Label categoryLabel = new Label("Rola");

		List<Role> listRoles = new ArrayList<>();
		for (Role r : Role.values()) {
			if (r != Role.ADMIN) {
				listRoles.add(r);
			}
		}

		ObservableList<Role> roleList = FXCollections.observableArrayList(listRoles);
		final ComboBox rolesComboBox = new ComboBox(roleList);
		rolesComboBox.getSelectionModel().selectFirst();

		ObservableList<Usero> usersList = FXCollections.observableArrayList(userService.findAll());
		final ComboBox usersComboBox = new ComboBox(usersList);
		usersComboBox.getSelectionModel().selectFirst();

		Button saveRole = new Button("Przypisz role");
		saveRole.setOnAction(e -> {
			Role newRole = (Role) rolesComboBox.getSelectionModel().getSelectedItem();
			Usero user = (Usero) usersComboBox.getSelectionModel().getSelectedItem();
			userService.changeRoleUser(user, newRole);
			StringUtils.createAlertBox(AlertType.INFORMATION, "Rola", "", "Rola została zmieniona");
		});

		grid.add(categoryLabel, 0, 0);
		grid.add(usersComboBox, 1, 0);
		grid.add(rolesComboBox, 2, 0);
		grid.add(saveRole, 3, 0);

	}

	private void initGridPane1(Stage stage, GridPane grid) {
		grid.setId("root");
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Label categoryLabel = new Label("Kategoria");

		TextField categoryTextField = new TextField();
		categoryTextField.setPromptText("Kategoria");

		Button categoryButton = new Button("Stwórz kategorię");
		categoryButton.setOnAction(e -> {
			String categoryWord = categoryTextField.getText().toString();
			for (String str : StringUtils.forbiddenWords) {
				if (str.toLowerCase().contains(categoryWord.toLowerCase())) {
					StringUtils.createAlertBox(AlertType.INFORMATION, "Kategoria", "", "Nowa kategoria zawiera zakazane słowo");
					return;
				}
			}
			Category c = new Category(categoryWord);
			categoryService.addCategory(c);
			categoryListToAdd.removeAll(categoryListToAdd);
			categoryListToAdd = FXCollections.observableArrayList(categoryService.findAll());
			categoryCcomboBox.setItems(categoryListToAdd);
			categoryCcomboBox.getSelectionModel().selectFirst();
			StringUtils.createAlertBox(AlertType.INFORMATION, "Kategoria", "", "Nowa kategoria została dodana");
		});

		grid.add(categoryLabel, 0, 0);
		grid.add(categoryTextField, 1, 0);
		grid.add(categoryButton, 2, 0);

	}

	private void initBorderPane1(Stage stage, BorderPane bp) {
		GridPane grid = new GridPane();
		grid.setId("root");
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		bp.setTop(grid);

		Label titleLabel = new Label("Tytuł");
		Label categoryLabel = new Label("Kategoria");

		TextField titleTextField = new TextField();
		titleTextField.setPromptText("Tytuł");

		categoryListToAdd = FXCollections.observableArrayList(categoryService.findAll());
		categoryCcomboBox = new ComboBox(categoryListToAdd);
		categoryCcomboBox.getSelectionModel().selectFirst();

		grid.add(titleLabel, 0, 0);
		grid.add(titleTextField, 1, 0);
		grid.add(categoryLabel, 0, 1);
		grid.add(categoryCcomboBox, 1, 1);

		VBox cenerVBox = new VBox();

		cenerVBox.setPadding(new Insets(25, 25, 25, 25));
		cenerVBox.setSpacing(10);

		TextArea contentLabel = new TextArea();
		contentLabel.setFont(new Font("Arial", 12));
		contentLabel.setWrapText(true);
		contentLabel.setMinHeight(150);

		Button createButton = new Button("Stwórz artykuł");
		createButton.setOnAction(e -> {
			Advertisement adv = new Advertisement(titleTextField.getText().toString(), contentLabel.getText().toString(), StringUtils.currentUser,
					(Category) categoryCcomboBox.getSelectionModel().getSelectedItem());
			advertisementService.addAdvertisement(adv);
			data.removeAll(data);
			data = FXCollections.observableArrayList(advertisementService.findAll());
			table.setItems(data);
			if (!dataNotAccepted.isEmpty())
				dataNotAccepted.removeAll(dataNotAccepted);
			dataNotAccepted = FXCollections.observableArrayList(advertisementService.findAllNotAccepted());
			tableNotAccepted.setItems(dataNotAccepted);
			StringUtils.createAlertBox(AlertType.INFORMATION, "Artykuł", "", "Nowy Artykuł zostanie dodany wkrótce. Poczekaj na akceptacje moderatora.");

			Thread thread = new Thread() {
				public void run() {
					EmailUtil.setMailServerProperties(StringUtils.currentUser.getEmail(), "Artykuł", "Nowy Artykuł został dodany");
				}
			};

			thread.start();
		});

		cenerVBox.getChildren().addAll(contentLabel, createButton);

		bp.setCenter(cenerVBox);

	}

	private void initVBox1(Stage stage, BorderPane bop) {
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(25, 25, 25, 25));
		TableColumn title = new TableColumn("Tytuł");
		title.setMinWidth(250);
		title.setStyle("-fx-alignment: CENTER;");
		TableColumn content = new TableColumn("Treść");
		content.setMinWidth(700);
		content.setMaxWidth(700);
		content.setStyle("-fx-alignment: CENTER;");
		TableColumn category = new TableColumn("Kategoria");
		category.setMinWidth(100);
		category.setStyle("-fx-alignment: CENTER;");
		TableColumn author = new TableColumn("Autor");
		author.setMinWidth(100);
		author.setStyle("-fx-alignment: CENTER;");

		title.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("title"));
		content.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("content"));
		category.setCellValueFactory(new PropertyValueFactory<Advertisement, String>("category"));
		author.setCellValueFactory(new PropertyValueFactory<Advertisement, Usero>("author"));

		data = FXCollections.observableArrayList(advertisementService.findAll());
		table.setItems(data);

		table.setOnMouseClicked(event -> {
			Stage advertisementStage = new Stage();
			int index = table.getSelectionModel().getSelectedIndex();
			Advertisement currAdverisement = (Advertisement) table.getItems().get(index);
			advertisementStage.setTitle(currAdverisement.getTitle());
			VBox vBoxInside = new VBox();
			vBoxInside.setAlignment(Pos.CENTER);
			vBoxInside.setPadding(new Insets(10, 50, 50, 50));
			vBoxInside.setSpacing(10);
			if (tableComments.getColumns().size() > 1) {
				tableComments.getColumns().removeAll(tableComments.getColumns());
			}
			if (StringUtils.currentUser.getUsername().equals(currAdverisement.getAuthor().getUsername()) || (StringUtils.currentUser.getRole() == Role.MODERATOR)
					|| (StringUtils.currentUser.getRole() == Role.ADMIN)) {
				TextField titleLabel = new TextField(currAdverisement.getTitle());

				ObservableList<Usero> usersList = FXCollections.observableArrayList(userService.findAll());
				final ComboBox usersComboBox = new ComboBox(usersList);
				usersComboBox.getSelectionModel().select(StringUtils.currentUser);

				ObservableList<Category> categoryList = FXCollections.observableArrayList(categoryService.findAll());
				final ComboBox categoryCcomboBox = new ComboBox(categoryList);
				categoryCcomboBox.getSelectionModel().select(currAdverisement.getCategory());

				TextArea contentLabel = new TextArea(currAdverisement.getContent());
				contentLabel.setFont(new Font("Arial", 12));
				contentLabel.setWrapText(true);
				contentLabel.setMinHeight(150);
				Button updateInfoButton = new Button("Zaktualizuj dane");
				updateInfoButton.setOnAction(ecv -> {
					Advertisement newAdvertisement = currAdverisement;
					newAdvertisement.setTitle(titleLabel.getText().toString());
					newAdvertisement.setContent(contentLabel.getText().toString());
					Usero userToUpdate = userService.findUsero((Usero) usersComboBox.getSelectionModel().getSelectedItem());
					// userService.updateUsero(userToUpdate);
					newAdvertisement.setAuthor(userToUpdate);
					newAdvertisement.setCategory((Category) categoryCcomboBox.getSelectionModel().getSelectedItem());
					advertisementService.addAdvertisement(newAdvertisement);
					data.removeAll(data);
					data = FXCollections.observableArrayList(advertisementService.findAll());
					table.setItems(data);
					advertisementStage.close();
					StringUtils.createAlertBox(AlertType.INFORMATION, "Dane zaktualizowane", "", "Dane zaktualizowane");
					Thread thread = new Thread() {
						public void run() {
							EmailUtil.setMailServerProperties(StringUtils.currentUser.getEmail(), "Artykuł", "Dane artykułu " + newAdvertisement.getTitle() + " zostały zaktualizowane");
						}
					};

					thread.start();

				});

				Button removeButton = new Button("Usuń artykuł");
				removeButton.setOnAction(asd -> {
					advertisementService.removeAdvertisement(currAdverisement);
					data.removeAll(data);
					data = FXCollections.observableArrayList(advertisementService.findAll());
					table.setItems(data);
					advertisementStage.close();
					Thread thread = new Thread() {
						public void run() {
							EmailUtil.setMailServerProperties(StringUtils.currentUser.getEmail(), "Artykuł", "Artykuł został usunięty");
						}
					};

					thread.start();

				});
				vBoxInside.getChildren().addAll(titleLabel, categoryCcomboBox);
				if (!(StringUtils.currentUser.getRole() == Role.UZYTKOWNIK))
					vBoxInside.getChildren().addAll(usersComboBox);
				vBoxInside.getChildren().addAll(contentLabel, updateInfoButton, removeButton);

			} else {
				Label titleLabel = new Label(currAdverisement.getTitle());
				titleLabel.setStyle("-fx-font-weight: bold");
				titleLabel.setFont(new Font("Arial", 30));
				Label categoryLabel = new Label("Kategoria: " + currAdverisement.getCategory() + ", Autor: " + currAdverisement.getAuthor().getUsername());
				categoryLabel.setFont(new Font("Arial", 25));
				Label contentLabel = new Label(currAdverisement.getContent());
				contentLabel.setFont(new Font("Arial", 20));
				contentLabel.setWrapText(true);
				contentLabel.setMinHeight(150);

				vBoxInside.getChildren().addAll(titleLabel, categoryLabel, contentLabel);

				Button removeButton = new Button("Usuń artykuł");
				removeButton.setOnAction(asd -> {
					advertisementService.removeAdvertisement(currAdverisement);
					data.removeAll(data);
					data = FXCollections.observableArrayList(advertisementService.findAll());
					table.setItems(data);
					advertisementStage.close();
					StringUtils.createAlertBox(AlertType.INFORMATION, "Usunięto artykuł", "", "Usunięto artykuł");
				});
				if (!(StringUtils.currentUser.getRole() == Role.UZYTKOWNIK))
					vBoxInside.getChildren().addAll(removeButton);
			}
			TableColumn commentRating = new TableColumn("Ocena");
			commentRating.setMinWidth(50);
			commentRating.setStyle("-fx-alignment: CENTER;");
			TableColumn commentUser = new TableColumn("Autor");
			commentUser.setMinWidth(100);
			commentUser.setStyle("-fx-alignment: CENTER;");
			TableColumn commentContent = new TableColumn("Treść");
			commentContent.setMinWidth(500);
			commentContent.setMaxWidth(500);
			commentContent.setStyle("-fx-alignment: CENTER;");

			commentRating.setCellValueFactory(new PropertyValueFactory<Comment, String>("rating"));
			commentUser.setCellValueFactory(new PropertyValueFactory<Comment, String>("author"));
			commentContent.setCellValueFactory(new PropertyValueFactory<Comment, String>("content"));

			dataComments = FXCollections.observableArrayList(advertisementService.findAllCommentsByAdvertisement(currAdverisement));
			dataComments.forEach(System.out::println);
			tableComments.setItems(dataComments);
			tableComments.getColumns().addAll(commentRating, commentUser, commentContent);
			vBoxInside.getChildren().addAll(tableComments);

			if (!StringUtils.currentUser.getUsername().equals(currAdverisement.getAuthor().getUsername()) || (StringUtils.currentUser.getRole() == Role.MODERATOR)
					|| (StringUtils.currentUser.getRole() == Role.ADMIN)) {
				Button addCommentButton = new Button("Dodaj komentarz");
				vBoxInside.getChildren().addAll(addCommentButton);
				addCommentButton.setOnAction(e -> {
					Stage addButtonStage = new Stage();
					BorderPane borderPane = new BorderPane();
					GridPane addCommentGrid = new GridPane();
					addCommentGrid.setAlignment(Pos.CENTER);
					addCommentGrid.setHgap(10);
					addCommentGrid.setVgap(10);
					addCommentGrid.setPadding(new Insets(25, 25, 25, 25));

					Label ratingLabel = new Label("Ocena");
					TextField ratingTextView = new TextField();
					ratingTextView.setPromptText("Ocena");
					addCommentGrid.add(ratingLabel, 0, 0);
					addCommentGrid.add(ratingTextView, 1, 0);

					borderPane.setTop(addCommentGrid);

					TextArea textArea = new TextArea();
					textArea.setMinWidth(400);
					textArea.setMinHeight(400);

					borderPane.setCenter(textArea);

					Button addButton = new Button("Dodaj komentarz");
					addButton.setOnAction(ev -> {
						if (!ratingTextView.getText().matches("-?\\d+") || ratingTextView.getText().length() > 1 || textArea.getText().length() < 10) {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setContentText("Wpisz poprawną ilość! (0-9) oraz treść komentarza");
							alert.showAndWait();
						} else {
							int rating = Integer.parseInt(ratingTextView.getText().toString());
							Comment comment = new Comment(textArea.getText().toString(), rating, StringUtils.currentUser);
							advertisementService.addComment(comment, currAdverisement);
							dataComments.removeAll(dataComments);
							dataComments = FXCollections.observableArrayList(advertisementService.findAllCommentsByAdvertisement(currAdverisement));
							tableComments.setItems(dataComments);
							addButtonStage.close();
							StringUtils.createAlertBox(AlertType.INFORMATION, "Stworzono nowy komentarz", "", "Stworzono nowy komentarz");
						}
					});

					borderPane.setBottom(addButton);

					Scene scene = new Scene(borderPane, 400, 550);
					addButtonStage.setX(100);
					addButtonStage.setY(100);
					scene.getStylesheets().add("static/stylesheet.css");
					addButtonStage.setScene(scene);
					addButtonStage.show();
				});

			}
			Scene scene = new Scene(vBoxInside, 600, 600);
			advertisementStage.setX(100);
			advertisementStage.setY(100);
			scene.getStylesheets().add("static/stylesheet.css");
			advertisementStage.setScene(scene);
			advertisementStage.show();
		});

		table.getColumns().addAll(title, category, author, content);
		vBox.getChildren().addAll(table);

		Button logoutButton = new Button("Wyloguj");
		logoutButton.setOnAction(e -> {
			Login l = new Login();
			l.initLoginWindow(stage);
			StringUtils.currentUser = null;
		});

		Button showAllButton = new Button("Pokaż wszystko");
		showAllButton.setOnAction(e -> {
			data.removeAll(data);
			data = FXCollections.observableArrayList(advertisementService.findAll());
			table.setItems(data);
		});

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(logoutButton, 0, 0);
		grid.add(showAllButton, 1, 0);

		Label userSearchLabel = new Label("Wyszukiwanie po autorze");
		Label categorySearchLabel = new Label("Wyszukiwanie po kategorii");

		ObservableList<Usero> usersList2 = FXCollections.observableArrayList(userService.findAll());
		final ComboBox usersComboBox2 = new ComboBox(usersList2);
		usersComboBox2.getSelectionModel().selectFirst();

		ObservableList<Category> categoryList2 = FXCollections.observableArrayList(categoryService.findAll());
		final ComboBox categoryCcomboBox2 = new ComboBox(categoryList2);
		categoryCcomboBox2.getSelectionModel().selectFirst();

		Button searchByUserButton = new Button("Szukaj");
		Button searchByCategoryButton = new Button("Szukaj");

		searchByUserButton.setOnAction(e -> {
			data.removeAll(data);
			data = FXCollections.observableArrayList(advertisementService.findAllByUser((Usero) usersComboBox2.getSelectionModel().getSelectedItem()));
			table.setItems(data);
		});

		searchByCategoryButton.setOnAction(e -> {
			data.removeAll(data);
			data = FXCollections.observableArrayList(advertisementService.findAllByCategory((Category) categoryCcomboBox2.getSelectionModel().getSelectedItem()));
			table.setItems(data);
		});

		grid.add(userSearchLabel, 0, 1);
		grid.add(categorySearchLabel, 0, 2);
		grid.add(usersComboBox2, 1, 1);
		grid.add(categoryCcomboBox2, 1, 2);
		grid.add(searchByUserButton, 2, 1);
		grid.add(searchByCategoryButton, 2, 2);

		bop.setTop(grid);
		bop.setCenter(vBox);

	}

}
