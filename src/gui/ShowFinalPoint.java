package gui;

import java.util.ArrayList;

import Ozlympic.Athletes;
import Ozlympic.Driver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class ShowFinalPoint implements EventHandler<ActionEvent> {

	ArrayList<Athletes> athletes;
	Driver driver;

	ShowFinalPoint(Driver driver, ArrayList<Athletes> athletes) {
		this.athletes = athletes;
		this.driver = driver;
	}

	@Override
	public void handle(ActionEvent event) {

		Stage finalPoint = new Stage();
		finalPoint.setTitle("Final Point");
		GridPane border = new GridPane();
		border.setPadding(new Insets(5));
		border.setHgap(20);
		border.setVgap(5);
		Scene scene = new Scene(border, 400, 550);
		final Text Name = new Text("Name");
		final Text Age = new Text("Age");
		final Text State = new Text("State");
		final Text AthleteType = new Text("Athlete Type");
		final Text Point = new Text("Point");
		border.add(Name, 0, 0);
		border.add(Age, 1, 0);
		border.add(State, 2, 0);
		border.add(AthleteType, 3, 0);
		border.add(Point, 4, 0);

		String[] athleteInf = new String[5];
		int countAthlete = 0;
		countAthlete = athletes.size();
		if (countAthlete == 0) {
			Stage errorWarning = new Stage();
			errorWarning.setTitle("Warning");
			Text warningText = new Text("Thers isn't any athlete's information!");
			BorderPane pane = new BorderPane();
			pane.setPadding(new Insets(10, 20, 10, 20));

			String imagePath = this.getClass().getResource("/").getFile();
			final ImageView warning = new ImageView(new Image(imagePath + "image/warning.png"));
			Button bt1 = new Button("Ok");
			Scene sceneWarning = new Scene(pane, 350, 100);
			pane.setCenter(warningText);
			pane.setLeft(warning);
			pane.setBottom(bt1);
			bt1.setOnAction((ActionEvent t) -> {
				errorWarning.close();
			});
			errorWarning.setScene(sceneWarning);
			errorWarning.show();
		} else {
			for (int i = 0; i < countAthlete; i++) {
				athleteInf = driver.getAthleteInf(athletes.get(i).getUserID());
				border.add(new Text(athleteInf[0]), 0, i + 1);
				border.add(new Text(athleteInf[1]), 1, i + 1);
				border.add(new Text(athleteInf[2]), 2, i + 1);
				border.add(new Text(athleteInf[3]), 3, i + 1);
				border.add(new Text(athleteInf[4]), 4, i + 1);
			}
		}
		finalPoint.setScene(scene);
		finalPoint.show(); // Display the stage
	}
}
