package gui;

/**
 * @author Yanjie Zhang
 * @content show the game type menu to guide user choose game type
 * 
 */
import java.util.ArrayList;

import Ozlympic.Athletes;
import Ozlympic.Driver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameTypeMenu implements EventHandler<ActionEvent> {

	Stage closeStage;
	Driver driver;
    ArrayList<Athletes> athletes;
	
    //constructor to get data from last stage
	GameTypeMenu(Stage closeStage, Driver driver, ArrayList<Athletes> athletes) {
		this.closeStage = closeStage;
		this.driver=driver;
		this.athletes=athletes;
	}

	public void handle(ActionEvent e) {
		closeStage.close(); //close basic menu
		Stage secondMenu = new Stage(); // Create a new stage
		secondMenu.setTitle("secondMenu"); // Set the stage title
		// Set a scene with a button in the stage
		VBox pane = new VBox();
		pane.setPadding(new Insets(50, 100, 50, 100));
		pane.setSpacing(10);
		pane.getChildren().add(new Text("Ozlympic Game"));
		pane.getChildren().add(new Text("==================================="));
		pane.getChildren().add(new Text("Select a sport from 1 to 3"));
		Button button1 = new Button("1. Swimming");
		Button button2 = new Button("2. Cycling");
		Button button3 = new Button("3. Running");

		pane.getChildren().add(button1);
		pane.getChildren().add(button2);
		pane.getChildren().add(button3);

		//event to finish selecting game type and then select athletes
		SelectAthletes sGame = new SelectAthletes(secondMenu, "Swimming", 1,driver, athletes,closeStage);
		SelectAthletes cGame = new SelectAthletes(secondMenu, "Cycling", 2,driver, athletes,closeStage);
		SelectAthletes rGame = new SelectAthletes(secondMenu, "Running", 3,driver, athletes,closeStage);
		button1.setOnAction(sGame);
		button2.setOnAction(cGame);
		button3.setOnAction(rGame);

		Scene scene = new Scene(pane);
		secondMenu.setScene(scene);
		secondMenu.show(); // Display the stage
	}
}