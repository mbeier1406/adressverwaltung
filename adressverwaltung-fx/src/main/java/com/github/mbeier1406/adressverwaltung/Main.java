package com.github.mbeier1406.adressverwaltung;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.github.mbeier1406.adressverwaltung.model.Person;
import com.github.mbeier1406.adressverwaltung.model.Person.Geschlecht;
import com.github.mbeier1406.adressverwaltung.model.PersonImpl;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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

	private final Dao<PersonImpl> personDAO = new DaoJPA<>(PersonImpl.class);
	private Person aktuellePerson;

	private TextField vornameTextField;
	private TextField nachnameTextField;
	private ImageView bildView;
	private DatePicker geburtsdatumDatePicker;
	private RadioButton maennlichRadioButton;
	private RadioButton weiblichRadioButton;
	private TextField kommentarTextField;
	final ListView<Person> listView = new ListView<>();

	@Override
	public void start(Stage primaryStage) {

		final var fileChooser = new FileChooser();

		final var vornameLabel = new Label("Vorname");
		vornameTextField = new TextField();
		final var nachnameLabel = new Label("Nachname");
		nachnameTextField = new TextField();
		final var bildLabel = new Label("Passbild");
		bildView = new ImageView();
		final var neuesBildButton = new Button("Passbild laden");
		final var geburtsdatumLabel = new Label("Geburtsdatum");
		geburtsdatumDatePicker = new DatePicker();
		final var geschlechtLabel = new Label("Geschlecht");
		final var geschlechtToggleGroup = new ToggleGroup();
		maennlichRadioButton = new RadioButton("Männlich");
		maennlichRadioButton.setToggleGroup(geschlechtToggleGroup);
		maennlichRadioButton.setSelected(true);
		maennlichRadioButton.setPadding(new Insets(0, 10, 0, 0));
		weiblichRadioButton = new RadioButton("Weiblich");
		weiblichRadioButton.setToggleGroup(geschlechtToggleGroup);
		final var geschlechtFlowPane = new FlowPane();
		geschlechtFlowPane.getChildren().addAll(maennlichRadioButton, weiblichRadioButton);
		final var kommentarLabel = new Label("Kommentar");
		kommentarTextField = new TextField();

		final var neuButton = new Button("Neue Person");
		final var speichernButton = new Button("Person speichern");
		final var loeschenButton = new Button("Person löschen");
		final var buttonFlowPane = new FlowPane(10, 5);
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

		final ObservableList<Person> observableList = FXCollections.observableArrayList(ladePersonen());
		listView.setItems(observableList);

		final var borderPane = new BorderPane();
		borderPane.setLeft(listView);
		borderPane.setCenter(gridPane);

		primaryStage.setTitle("Adressverwaltung");
		primaryStage.setScene(new Scene(borderPane, 750, 380));
		primaryStage.show();

		listView.setOnMouseClicked(event -> {
			final var person = listView.getSelectionModel().getSelectedItem();
			System.out.println("person="+person);
			personenAnzeigeAktualisieren(person);
		});

		neuesBildButton.setOnAction(event -> {
			final var file = fileChooser.showOpenDialog(primaryStage);
			System.out.println("file="+file);
			if ( file != null ) {
				// "file:src/main/resources/images/maxmustermann.png"
				try {
					bildView.setImage(new Image(new BufferedInputStream(new FileInputStream(file)), 0, 100, true, false));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		});

		neuButton.setOnAction(event -> {
			aktuellePerson = new PersonImpl();
			aktuaisierePerson();
			aktualisiereListe();
		});

		speichernButton.setOnAction(event -> {
			aktuaisierePerson();
			aktualisiereListe();
		});

		loeschenButton.setOnAction(event -> {
			if ( aktuellePerson == null ) return;
			loeschePerson();
			aktualisiereListe();
			anzeigeLeeren();
		});
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		personDAO.shutdown();
	}

	/**
	 * Löschte die Person in {@linkplain #aktuellePerson} aus der DB.
	 */
	private void loeschePerson() {
		personDAO.delete(((PersonImpl) aktuellePerson).getId());
	}

	/**
	 * Alle Personen aus der Datenbank laden un als FX-Collection zurückgeben
	 * @return die Liste
	 */
	private ObservableList<Person> ladePersonen() {
		return FXCollections.observableArrayList(personDAO.findAll());
	}

	/**
	 * Aktualisiert die Anzeige einer Person mit den Daten aus der
	 * in Liste aktivierten Person.
	 * @param person die Iin der Liste ausgewählte Person
	 */
	private void personenAnzeigeAktualisieren(Person person) {
		aktuellePerson = person;
		vornameTextField.setText(person.getVorname());
		nachnameTextField.setText(person.getNachname());
		bildView.setImage(person.getPassbild() != null ?
				new Image(new ByteArrayInputStream(person.getPassbild(), 0, person.getPassbild().length), 0, 100, true, false) :
				null);
		if ( person.getGeburtsdatum() != null ) {
			Calendar c = Calendar.getInstance();
			c.setTime(person.getGeburtsdatum());
			geburtsdatumDatePicker.setValue(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)));
		}
		else
			geburtsdatumDatePicker.setValue(null);
		if ( person.getGeschlecht() == null ) {
			maennlichRadioButton.setSelected(false);
			weiblichRadioButton.setSelected(false);			
		}
		else if ( person.getGeschlecht() == Geschlecht.MAENNLICH ) {
			maennlichRadioButton.setSelected(true);
			weiblichRadioButton.setSelected(false);
		}
		else {
			maennlichRadioButton.setSelected(false);
			weiblichRadioButton.setSelected(true);
			
		}		
		kommentarTextField.setText(person.getKommentar());
	}

	private void anzeigeLeeren() {
		vornameTextField.setText(null);
		nachnameTextField.setText(null);
		bildView.setImage(null);
		geburtsdatumDatePicker.setValue(null);
		maennlichRadioButton.setSelected(false);
		weiblichRadioButton.setSelected(false);
		kommentarTextField.setText(null);
	}

	/**
	 * Aktualisiert die ausgewählte Person in {@linkplain #aktuellePerson}
	 * mit den Werten der Anzeige und speichert sie in der Datenbank.
	 */
	private void aktuaisierePerson() {
		aktuellePerson.setVorname(vornameTextField.getText());
		aktuellePerson.setNachname(nachnameTextField.getText());
		aktuellePerson.setGeburtsdatum(geburtsdatumDatePicker.getValue() != null ?
				java.sql.Date.valueOf(geburtsdatumDatePicker.getValue()) :
				null);
		aktuellePerson.setGeschlecht(maennlichRadioButton.isSelected() ? Geschlecht.MAENNLICH : Geschlecht.WEIBLICH);
		if ( bildView.getImage() != null )
			try {
				final var baos = new ByteArrayOutputStream();
				ImageIO.write(SwingFXUtils.fromFXImage(bildView.getImage(), null), "png", baos);
				aktuellePerson.setPassbild(baos.toByteArray());
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		else
			aktuellePerson.setPassbild(null);
		aktuellePerson.setKommentar(kommentarTextField.getText());
		personDAO.persist((PersonImpl) aktuellePerson);
	}

	/**
	 * Liste der {@linkplain Person}en aus der DB aktualisieren
	 */
	private void aktualisiereListe() {
		listView.getItems().clear();
		listView.setItems(ladePersonen());
	}

	public static void main(String[] args) {
		launch();
	}

}
