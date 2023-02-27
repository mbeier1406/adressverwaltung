package com.github.mbeier1406.adressverwaltung;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Startet die Anwendung zur Adressverwaltung.
 * @author mbeier
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		final var fileChooser = new FileChooser();

		final var vornameLabel = new Label("Vorname");
		final var vornameTextField = new TextField();
		final var nachnameLabel = new Label("Nachname");
		final var nachnameTextField = new TextField();
		final var bildLabel = new Label("Passbild");
		final var bildView = new ImageView(new Image("file:src/main/resources/images/maxmustermann.png", 0, 100, true, false));
		final var neuesBildButton = new Button("Passbild laden");
		final var geburtsdatumLabel = new Label("Geburtsdatum");
		final var geburtsdatumDatePicker = new DatePicker();
		final var geschlechtLabel = new Label("Geschlecht");
		final var geschlechtToggleGroup = new ToggleGroup();
		final var maennlichRadioButton = new RadioButton("Männlich");
		maennlichRadioButton.setToggleGroup(geschlechtToggleGroup);
		maennlichRadioButton.setSelected(true);
		maennlichRadioButton.setPadding(new Insets(0, 10, 0, 0));
		final var weiblichRadioButton = new RadioButton("Weiblich");
		weiblichRadioButton.setToggleGroup(geschlechtToggleGroup);
		final var geschlechtFlowPane = new FlowPane();
		geschlechtFlowPane.getChildren().addAll(maennlichRadioButton, weiblichRadioButton);
		final var kommentarLabel = new Label("Kommentar");
		final var kommentarTextField = new TextField();

		final var neuButton = new Button("Neue Person");
		final var speichernButton = new Button("Person speichern");
		final var loeschenButton = new Button("Person löschen");
		final var buttonFlowPane = new FlowPane();
		buttonFlowPane.getChildren().addAll(neuButton, speichernButton, loeschenButton);

		final var gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.add(vornameLabel, 0, 0);
		gridPane.add(vornameTextField, 1, 0);
		gridPane.add(nachnameLabel, 0, 1);
		gridPane.add(nachnameTextField, 1, 1);
		gridPane.add(bildLabel, 0, 2);
		gridPane.add(bildView, 1, 2);
		gridPane.add(neuesBildButton, 1, 3);
		gridPane.add(geburtsdatumLabel, 0, 4);
		gridPane.add(geburtsdatumDatePicker, 1, 4);
		gridPane.add(geschlechtLabel, 0, 5);
		gridPane.add(geschlechtFlowPane, 1, 5);
		gridPane.add(kommentarLabel, 0, 6);
		gridPane.add(kommentarTextField, 1, 6);
		gridPane.add(buttonFlowPane, 0, 7, 2, 1);

		final ListView<String> listView = new ListView<>();
		final ObservableList<String> observableList = FXCollections.observableArrayList("eins", "zwei", "drei");
		listView.setItems(observableList);

		final var borderPane = new BorderPane();
		borderPane.setLeft(listView);
		borderPane.setCenter(gridPane);
		//borderPane.se

		primaryStage.setTitle("Adressverwaltung");
		primaryStage.setScene(new Scene(borderPane, 750, 380));
		primaryStage.show();

		listView.setOnMouseClicked(event -> {});
		neuButton.setOnAction(event -> {});
		speichernButton.setOnAction(event -> {});
		loeschenButton.setOnAction(event -> {});
	}

	public static void main(String[] args) {
		launch();
	}

}
