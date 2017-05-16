package gui;

import java.util.ArrayList;

import Ozlympic.Athletes;
import Ozlympic.Driver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectAthletes implements EventHandler<ActionEvent>{

	Stage closeStage;
	String gameTypeName;
	int gameType;
	Driver driver;
	ArrayList<Athletes> athletes;
	
	SelectAthletes(Stage closeStage, String gameTypeName, int gameType, Driver driver,ArrayList<Athletes> athletes)
	{
		this.closeStage=closeStage;
		this.driver=driver;
		this.gameType=gameType;
		this.gameTypeName=gameTypeName;
		this.athletes=athletes;
	}
	@Override
	public void handle(ActionEvent event) {
		MultiWindow.gameTypeName = gameTypeName;
		driver.setGameType(gameType);
		closeStage.close();
		
		Stage selectAthlete = new Stage();
		selectAthlete.setTitle("Final Point");
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
		border.add(Name, 1, 0);
		border.add(Age, 2, 0);
		border.add(State, 3, 0);
		border.add(AthleteType, 4, 0);
		border.add(Point, 5, 0);

		String[] athleteInf = new String[5];
		int countAthlete = 0;
		countAthlete = athletes.size();
		ArrayList<CheckBox> cb=new ArrayList<CheckBox>();
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
				border.add(new Text(athleteInf[0]), 1, i + 1);
				border.add(new Text(athleteInf[1]), 2, i + 1);
				border.add(new Text(athleteInf[2]), 3, i + 1);
				border.add(new Text(athleteInf[3]), 4, i + 1);
				border.add(new Text(athleteInf[4]), 5, i + 1);
				cb.add(new CheckBox());
				border.add(cb.get(i), 0, i+1);
			}
		}
		selectAthlete.setScene(scene);
		selectAthlete.show(); // Display the stage
	}

}
